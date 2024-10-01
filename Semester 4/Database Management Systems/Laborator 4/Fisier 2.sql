Use Showroom
go

CREATE TABLE Lab4LoggingTable(
	ID INT PRIMARY KEY IDENTITY(2000,1),
	operationType VARCHAR(50),
	executionTime DATETIME,
	logMessage VARCHAR(50)
)
SELECT * FROM Lab4LoggingTable
SELECT * FROM Cars



-- Dirty Reads 
--Doesn't work
SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;
BEGIN TRANSACTION
SELECT * FROM Cars;
INSERT INTO Lab4LoggingTable(operationType, executionTime, logMessage) VALUES('SELECT CARS', CURRENT_TIMESTAMP, 'Select after update')
WAITFOR DELAY '00:00:10'
SELECT * FROM Cars;
INSERT INTO Lab4LoggingTable(operationType, executionTime, logMessage) VALUES('SELECT CARS', CURRENT_TIMESTAMP, 'SELECT after rollback')
COMMIT TRANSACTION


--Works
SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
BEGIN TRANSACTION
SELECT * FROM Cars;
INSERT INTO Lab4LoggingTable(operationType, executionTime, logMessage) VALUES('SELECT CARS', CURRENT_TIMESTAMP, 'Select after update')
WAITFOR DELAY '00:00:10'
SELECT * FROM Cars;
INSERT INTO Lab4LoggingTable(operationType, executionTime, logMessage) VALUES('SELECT CARS', CURRENT_TIMESTAMP, 'SELECT after rollback')
COMMIT TRANSACTION



-- Non Repetable Reads
BEGIN TRANSACTION
WAITFOR DELAY '00:00:05';
UPDATE Cars SET Price=150000 WHERE CarID=1;
INSERT INTO Lab4LoggingTable(operationType, executionTime, logMessage) VALUES('UPDATE CARS', CURRENT_TIMESTAMP, 'Delay for update')
COMMIT TRANSACTION
INSERT INTO Lab4LoggingTable(operationType, executionTime, logMessage) VALUES('UPDATE CARS', CURRENT_TIMESTAMP, 'Rollback')



-- Phantom Reads --
Select * from Cars
BEGIN TRANSACTION
INSERT INTO Lab4LoggingTable(operationType, executionTime, logMessage) VALUES('PHANTOM READ INSERT', CURRENT_TIMESTAMP, 'Delay for insert started')
WAITFOR DELAY '00:00:05'
INSERT INTO Cars(Make, Model, Year, Color, Price, QuantityInStock) VALUES ('Dacia', 'Sandero', 2002, 'Blue', 8000, 12);
INSERT INTO Lab4LoggingTable(operationType, executionTime, logMessage) VALUES('PHANTOM READ INSERT', CURRENT_TIMESTAMP, 'Insert')
COMMIT TRANSACTION
INSERT INTO Lab4LoggingTable(operationType, executionTime, logMessage) VALUES('PHANTOM READ INSERT', CURRENT_TIMESTAMP, 'Insert Rollback')



--DeadLock--
--Doesn't work
SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;
BEGIN TRAN;
UPDATE Salespeople SET FirstName='MARIUS' WHERE SalespersonID=300;
WAITFOR DELAY '00:00:05';
UPDATE Cars SET QuantityInStock=30 WHERE CarID = 2;
COMMIT TRAN;
INSERT INTO Lab4LoggingTable(operationType, executionTime, logMessage) VALUES('DEADLOCK', CURRENT_TIMESTAMP, 'Second transaction')



select * from Cars
select * from Salespeople


--Works
SET DEADLOCK_PRIORITY HIGH;
BEGIN TRAN;
UPDATE Salespeople SET FirstName='ALEX' WHERE SalespersonID=300;
WAITFOR DELAY '00:00:05';
UPDATE Cars SET QuantityInStock=35 WHERE CarID = 2;
COMMIT TRAN;
INSERT INTO Lab4LoggingTable(operationType, executionTime, logMessage) VALUES('DEADLOCK', CURRENT_TIMESTAMP, 'Second transaction')
