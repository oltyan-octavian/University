USE [Showroom]
GO
CREATE VIEW Randomizer
AS
SELECT RAND() AS Value


--functie pentru int random
GO
CREATE FUNCTION dbo.RandomInt(@lower INT,@upper INT) RETURNS INT AS
BEGIN
	RETURN FLOOR((SELECT Value FROM Randomizer)*(@upper-@lower)+@lower);
END


--functie pentru inserare in tabela de Cars(tabel cu 1 primary key, 0 foreign keys)
GO
CREATE OR ALTER PROCEDURE InsertCars @aux INT AS
BEGIN
	INSERT INTO Cars 
	VALUES (@aux,'BMW-'+CONVERT(VARCHAR(20),@aux),'E92 M3-'+CONVERT(VARCHAR(20),@aux),dbo.RandomInt(2006,2014),'Red',50000,15)
END


--functie pentru inserare in tabela de Services(tabel cu un primary key si un foreign key)
GO
CREATE OR ALTER PROCEDURE InsertServices @aux INT AS
BEGIN
	INSERT INTO Services 
	VALUES(@aux,(SELECT TOP 1 CarID FROM Cars ORDER BY newid()),GETDATE(),'Service-'+CONVERT(VARCHAR(20),@aux), 5000+@aux)-- 
END


--functie pentru inserare in tabela de Features(tabel ajutator pentru cel cu 2 primary key-uri)
GO 
CREATE OR ALTER PROCEDURE InsertFeatures @aux INT AS
BEGIN
	INSERT INTO Features
	VALUES (@aux,'Feature-'+CONVERT(VARCHAR(20),@aux), 'Description-'+CONVERT(VARCHAR(20),@aux))
END


--functie pentru inserare in tabela de CarsAndFeatures(tabel cu 2 primary key-uri)
GO
CREATE OR ALTER PROCEDURE InsertCarsAndFeatures @aux INT AS
BEGIN
	declare @carId INT,@featureId INT
	WHILE 1=1
	BEGIN 
		set @carId=(select top 1 CarID from Cars order by newid()) --folosindu-se de newid ii returneaza cel mai nou element adaugat
		set @featureId=(select top 1 FeatureID from Features order by newid())
		IF NOT EXISTS(SELECT 1 FROM CarsAndFeatures WHERE CarID=@carId and FeatureID=@featureId)
			BREAK --in cazul in care if-ul nu se indeplineste, inseamna ca ceva e gresit si ramane in acest while loop
				  --in cazul in care if-ul se indeplineste, da break si se duce mai departe sa il insereze in tabel
	END
	INSERT INTO CarsAndFeatures
		VALUES (@carId,@featureId)

END


--functie pentru popularea tabelelor 
GO
CREATE OR ALTER PROCEDURE PopulateTable @tableName VARCHAR(50),@noRows INT AS
BEGIN
	DECLARE @curentRow INT,@command VARCHAR(256)
	SET @curentRow=0
	WHILE @curentRow<@noRows
		BEGIN
			SELECT @command='Insert'+@tableName+' '+CONVERT(VARCHAR(10),@curentRow)
			EXEC (@command)
			SET @curentRow=@curentRow+1
		END
END


--functie pentru stergerea datelor din tabel
GO
CREATE OR ALTER PROCEDURE ClearTable @tableName VARCHAR(50) as
	EXEC ('DELETE FROM '+@tableName)



--functiile de view
GO--Toate masinile mai noi de 2011
CREATE OR ALTER VIEW NewerCars AS
SELECT C.Make,C.Model
FROM Cars C
WHERE C.Year>2011


GO --Toate service-urile care au costat mai mult de 5030 de euro si masinile pe care au fost efectuate
CREATE OR ALTER VIEW ServicedCars AS
SELECT C.Make,C.Model,S.Description
FROM Cars C
INNER JOIN Services S ON C.CarID=S.CarID
WHERE S.Cost > 5030


GO --Numarul de dotari pe care il are o masina in functie de an
CREATE OR ALTER VIEW FeaturesOnCars AS
SELECT DISTINCT C.Year,COUNT(F.FeatureID) as Features
FROM Features F
INNER JOIN CarsAndFeatures CF ON F.FeatureID=CF.FeatureID
INNER JOIN Cars C ON CF.CarID=C.CarID
GROUP BY C.Year








--functii prin care testeaza daca chestiile scrise mai sus sunt corecte
--pe astea de jos nu le vei apela efectiv decat daca te pune sa ii arati ca merg chestiile cum trebuie
go
--see how many dealerships group by number of slots 
select * from NewerCars
select * from ServicedCars
select * from FeaturesOnCars

/*

*/

select * from Cars
select * from Services
select * from Features
select * from CarsAndFeatures

exec PopulateTable 'Cars',10
exec PopulateTable 'Services',10
exec PopulateTable 'Features',10
exec PopulateTable 'CarsAndfeatures',10
exec ClearTable 'CarsAndFeatures'
exec ClearTable 'Features'
exec ClearTable 'Services'
exec ClearTable 'Cars'