use Showroom
go


DROP PROCEDURE IF EXISTS main;
drop table Versiune

create table Versiune(Vrs int)

Insert into Versiune Values (0)

Select * From Versiune


-- modifica coloana din tabel
create procedure Procedura1
as 
begin
    alter table Cars
    alter column Price bigint
end


create procedure Procedura1Reverse
as
begin 
    alter table Cars
    alter column Price int
end

exec Procedura1
exec Procedura1Reverse

-- creaza tabel
CREATE PROCEDURE Procedura2
AS
BEGIN
	CREATE TABLE InventoryManager(
	ManagerID int PRIMARY KEY ,
	FirstName varchar(100),
	LastName varchar(100),
	Experience int 
	);
END

CREATE PROCEDURE Procedura2Reverse
AS
BEGIN
	DROP TABLE InventoryManager;
END

exec Procedura2
exec Procedura2Reverse

-- adauga coloana in tabel
CREATE PROCEDURE Procedura3
AS
BEGIN
	ALTER TABLE Cars 
	ADD Kilometers int
END

CREATE PROCEDURE Procedura3Reverse
AS
BEGIN
	ALTER TABLE Cars
	DROP COLUMN Kilometers
END

exec Procedura3
exec Procedura3Reverse

-- constrangere
CREATE PROCEDURE Procedura4
AS
BEGIN
	ALTER TABLE InventoryManager 
	ADD CONSTRAINT experience_cons
	DEFAULT 4 for Experience
END

CREATE PROCEDURE Procedura4Reverse
AS
BEGIN
	ALTER TABLE InventoryManager
	DROP Constraint experience_cons
END

exec Procedura4

Select * from InventoryManager
Insert Into InventoryManager Values (1,'Ionut', 'Manea',3)
Insert Into InventoryManager(ManagerID,FirstName, LastName) Values (2,'Marius', 'Petrescu')

exec Procedura4Reverse

-- constrangere cheie straina
CREATE PROCEDURE Procedura5
AS
BEGIN
	ALTER TABLE InventoryManager 
	ADD CONSTRAINT fk_constraint FOREIGN KEY(ManagerID) REFERENCES Inventory(InventoryID)
END


CREATE PROCEDURE Procedura5Reverse
AS
BEGIN
	ALTER TABLE InventoryManager
	DROP CONSTRAINT fk_constraint
END

exec Procedura2
exec Procedura5
exec Procedura5Reverse

CREATE PROCEDURE main @Versiune int
AS
BEGIN
	IF @Versiune>5
		BEGIN 
			PRINT 'Versiunea nu e potrivita!';
			return;
		END
	IF @Versiune<0
		BEGIN 
			PRINT 'Versiunea nu e potrivita!';
			return ;
		END

	DECLARE @VersiuneActuala AS INT;
	SELECT @VersiuneActuala = Vrs FROM Versiune;
	
	IF @Versiune=@VersiuneActuala
	 BEGIN
		PRINT 'Nu s-au efectuat modificari!';
		RETURN;
	 END

IF @Versiune>@VersiuneActuala
	 BEGIN
		WHILE @Versiune!=@VersiuneActuala
		BEGIN

			IF @VersiuneActuala=0
				 BEGIN
					Exec Procedura1;
				END
			IF @VersiuneActuala=1
				 BEGIN
					Exec Procedura2;
				END
			IF @VersiuneActuala=2
				 BEGIN
					Exec Procedura3;
				END
			IF @VersiuneActuala=3
				 BEGIN
					Exec Procedura4;
				END
			IF @VersiuneActuala=4
				 BEGIN
					Exec Procedura5;
				END


			SET @VersiuneActuala=@VersiuneActuala+1;

		END

		UPDATE Versiune
		SET Vrs = @Versiune;
		RETURN;
	 END
	 

	 WHILE @Versiune!=@VersiuneActuala
		BEGIN

			

			IF @VersiuneActuala=1
				 BEGIN
					Exec Procedura1Reverse;
				END
			IF @VersiuneActuala=2
				 BEGIN
					Exec Procedura2Reverse;
				END
			IF @VersiuneActuala=3
				 BEGIN
					Exec Procedura3Reverse;
				END
			IF @VersiuneActuala=4
				 BEGIN
					Exec Procedura4Reverse;
				END
			IF @VersiuneActuala=5
				 BEGIN
					Exec Procedura5Reverse;
				END
			set @VersiuneActuala=@VersiuneActuala-1;
		END
	 
	 UPDATE Versiune
	 SET Vrs = @Versiune;
	 RETURN;
END

Exec main 1

select * from InventoryManager
select * from Cars
select * from Inventory