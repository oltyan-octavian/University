USE [Showroom]
go


--functia prin care creeaza un test (doar insereaza in tabel numele, inca nu poate fi rulat)
GO
CREATE OR ALTER PROCEDURE CreateTest @name VARCHAR(50) AS
BEGIN
	IF EXISTS(SELECT * FROM Tests T WHERE Name=@name)
		BEGIN
			PRINT 'TEST ALREADY EXISTS'
			RETURN
		END

	INSERT INTO Tests(Name) VALUES (@name)
END


--functia prin care adauga in "Tables" numele tabelelor care pot face parte din testare
GO
CREATE OR ALTER PROCEDURE AddTableToTestTable @tableName VARCHAR(50) AS
BEGIN 
	IF NOT EXISTS(SELECT * FROM sys.tables WHERE name=@tableName)
	BEGIN
		PRINT 'TABLE '+@tableName+' doesnt exist'
		RETURN 
	END
	IF EXISTS(SELECT * FROM Tables T WHERE T.Name=@tableName)
	BEGIN
		PRINT 'TABLE '+@tableName+' already exists'
		RETURN
	END
	INSERT INTO Tables(Name) VALUES(@tableName)
END


--functia care leaga Tables si Tests(many to many)
GO
CREATE OR ALTER PROCEDURE RelateTestTables @tableName VARCHAR(50),@testName VARCHAR(50),@noRows INT, @position INT AS
BEGIN 
	IF @position<0
		BEGIN
			PRINT 'Position has to be >0'
			RETURN
		END
	IF @noRows<0
		BEGIN
			PRINT 'NoOfRows has to be >0'
			RETURN
		END

	DECLARE @testID INT, @tableID INT
	SET @testID=(SELECT T.TestID FROM Tests T WHERE T.Name=@testName)
	SET @tableID=(SELECT T.TableID FROM Tables T WHERE T.Name=@tableName)

	INSERT INTO TestTables(TestID,TableID,NoOfRows,Position) VALUES  ---
						(@testID,@tableID,@noRows,@position)
END


GO
CREATE OR ALTER PROCEDURE AddViewToTestTable @viewName VARCHAR(50) AS
BEGIN
	IF NOT EXISTS(SELECT * FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME=@viewName)
	BEGIN
		PRINT 'View '+@viewName+' doesnt exist'
		RETURN
	END
	IF EXISTS(SELECT * FROM Views WHERE Name=@viewName)
	BEGIN
		PRINT 'View already added'
		RETURN
	END
	INSERT INTO Views(Name) VALUES (@viewName)
END


--functia care leaga Views si Tests(many to many)
GO 
CREATE OR ALTER PROCEDURE RelateTestsAndViews @viewName VARCHAR(50),@testName VARCHAR(50) AS
BEGIN
	DECLARE @testID INT,@viewID INT
	SET @testID=(SELECT TestID FROM Tests T WHERE T.Name=@testName)
	SET @viewID=(SELECT ViewID FROM Views V WHERE V.Name=@viewName)
	INSERT INTO TestViews(TestID,ViewID) VALUES (@testID,@viewID)
END


--functia mare pe care o vom folosi sa rulam testele
GO
CREATE OR ALTER PROCEDURE RunTest @name VARCHAR(50) AS 
BEGIN

	--in test vom avea id-ul testului pe care il rulam din tabelul de teste pe care l am creat mai devreme
	DECLARE @test INT
	SET @test=(SELECT T.TestID FROM Tests T WHERE T.Name=@name)


	--dam declare la diferite variabile de care vom avea nevoie
	DECLARE @tableName VARCHAR(50),
			@noRows INT,
			@tableID INT,
			@viewName VARCHAR(50),
			@viewID INT,
			@testRunID INT,
			@command VARCHAR(256),
			@curentTestStart DATETIME2,
			@curentTestEnd DATETIME2,
			@allTestsStart DATETIME2,
			@allTestEnd DATETIME2


	--adaugam in tabelul TestRuns ca si descriere numele testului pe care il facem
	INSERT INTO TestRuns(Description) Values (@name)


	--face cu identity id-ul, iar in variabila @testRunID pune acel id
	--last_value = ultima valoare generata de catre identity
	--sys.identity_columns este un view creat de catre system care ne arata toate identity-urile create (oarecum)
	--name='TestRunID' verifica sa fie identity-urile doar din tabelul TestRuns
	SET @testRunID=CONVERT(INT,(SELECT last_value FROM sys.identity_columns WHERE name='TestRunId'))


	--cursor scroll e principial identic cu un iterator
	--aici creem un "iterator" pentru Tables, ordonat dupa position
	--vrem ca acest iterator sa umble doar pe tabelele care se afla in acest test
	DECLARE TablesCursor CURSOR SCROLL FOR
	SELECT T2.TableID,T2.Name,TT.NoOfRows
	FROM TestTables TT
	INNER JOIN Tables T2 ON T2.TableID=TT.TableID
	WHERE TT.TestID=@test
	ORDER BY TT.Position


	--aici creem un "iterator" pentru Views
	--vrem ca acest iterator sa umble doar pe view-urile care se afla in acest test
	DECLARE ViewsCursor CURSOR FOR
	SELECT V.ViewID, V.Name
	FROM Views V
	INNER JOIN TestViews TV ON TV.ViewID=V.ViewID
	WHERE TV.TestID=@test


	--setam timpul la care au inceput toate testele
	SET @allTestsStart=SYSDATETIME();

	--incepem sa folosim "iteratorul"
	OPEN TablesCursor

	--luam prima variabila care ne apare si ii punem valorile in tableID, tableName si noRows
	FETCH FIRST FROM TablesCursor 
	INTO @tableID,@tableName,@noRows

	WHILE @@FETCH_STATUS=0 --cat timp nu a ajuns la finalul tabelului si inca ne gaseste valori, stam in while
	BEGIN
		--setam timpul la care a inceput testul actual pe table
		SET @curentTestStart=SYSDATETIME();

		--populam tabelele
		SET @command='PopulateTable '+char(39)+@tableName+char(39)+','+CONVERT(VARCHAR(10),@noRows)--char(39) => simple quote
		PRINT @noRows
		EXEC (@command)

		--setam timpul la care s a oprit testul actual pe table
		SET @curentTestEnd=SYSDATETIME();
		
		--bagam in TestRunTables valorile obtinute (tabelul pe care am lucrat si timpurile pe care le am obtinut)
		INSERT INTO TestRunTables VALUES (@testRunID,@tableID,@curentTestStart,@curentTestEnd)

		--trecem la urmatorul tabel
		FETCH NEXT FROM TablesCursor INTO @tableId,@tableName,@noRows
	END
	--oprim "iteratorul"
	CLOSE TablesCursor
	

	--acelasi lucru ca mai sus, doar ca pe view
	OPEN ViewsCursor
	FETCH FROM ViewsCursor INTO @viewID,@viewName
	WHILE @@FETCH_STATUS=0
		BEGIN
			SET @curentTestStart=SYSDATETIME();
			DECLARE @statement VARCHAR(256)
			SET @statement='SELECT * FROM '+@viewName
			PRINT @statement
			EXEC (@statement)
			SET @curentTestEnd=SYSDATETIME();
			INSERT INTO TestRunViews VALUES(@testRunID,@viewID,@curentTestStart,@curentTestEnd)
			FETCH NEXT FROM ViewsCursor INTO @viewID,@viewName
		END
	CLOSE ViewsCursor
	--odata ce nu mai folosim, dam deallocate la ViewsCursor
	DEALLOCATE ViewsCursor


	--acelasi lucru ca mai sus, doar ca pe delete
	OPEN TablesCursor
	FETCH LAST FROM TablesCursor INTO @tableID,@tableName,@noRows

	WHILE @@FETCH_STATUS=0 --clear
	BEGIN
		EXEC ClearTable @tableName 
		FETCH PRIOR FROM TablesCursor INTO @tableID,@tableName,@noRows
	END 
	CLOSE TablesCursor
	--odata ce nu mai folosim, dam deallocate la TablesCursor
	DEALLOCATE TablesCursor

	
	--setam timpul la care s au incheiat toate testele
	SET @allTestEnd=SYSDATETIME();
	
	--introducem valorile obtinute in TestRuns(timpul la care au inceput si s au incheiat testele)
	UPDATE TestRuns
		SET StartAt=@allTestsStart,EndAt=@allTestEnd
		WHERE TestRunID=@testRunID
END 


SELECT * FROM Views
SELECT * FROM Tables
SELECT * FROM TestViews
SELECT * FROM Tests
SELECT * FROM TestTables 
SELECT * FROM TestRunTables
SELECT * FROM TestRuns
SELECT * FROM TestRunViews
exec ClearTable 'TestRunViews'
exec ClearTable 'TestRuns'
exec ClearTable 'TestRunTables'
exec ClearTable 'TestTables'
exec ClearTable 'Tests'
exec ClearTable 'TestViews'
exec ClearTable 'Tables'
exec ClearTable 'Views'

/*TEST 1
TABLES: Cars*
views NewerCars
*/
EXEC CreateTest 'SmallTest'
EXEC AddTableToTestTable 'Cars'
EXEC RelateTestTables 'Cars','SmallTest',150,0
EXEC AddViewToTestTable 'NewerCars'
EXEC RelateTestsAndViews 'NewerCars','SmallTest'
EXEC RunTest 'SmallTest' 

/*TEST 2
TABLES: Services, Cars
views ServicedCars
*/

EXEC CreateTest'SmallTest2'
EXEC AddTableToTestTable 'Services'
EXEC AddTableToTestTable 'Cars'
EXEC RelateTestTables 'Cars','SmallTest2',150,0
EXEC RelateTestTables 'Services','SmallTest2',100,1 
EXEC AddViewToTestTable 'ServicedCars'
EXEC RelateTestsAndViews 'ServicedCars','SmallTest2'  
EXEC RunTest 'SmallTest2'

/*TEST 3 
TABLES: Cars,Features,CarsAndFeatures
views FeaturesOnCars
*/

EXEC CreateTest 'SmallTest3'
EXEC AddTableToTestTable 'Cars'
EXEC AddTableToTestTable 'Features'
EXEC AddTableToTestTable 'CarsAndFeatures'
EXEC RelateTestTables 'Cars','SmallTest3',140,0
EXEC RelateTestTables 'Features','SmallTest3',140,1
EXEC RelateTestTables 'CarsAndFeatures','SmallTest3',140,2
EXEC AddViewToTestTable 'FeaturesOnCars'
EXEC RelateTestsAndViews 'FeaturesOnCars','SmallTest3'
EXEC RunTest 'SmallTest3'