USE Showroom
GO

DELETE FROM CarsAndFeatures
DELETE FROM Payments
DELETE FROM Sales
DELETE FROM Services
DELETE FROM Inventory
DELETE FROM Repairs
DELETE FROM Cars
DELETE FROM Features
DELETE FROM Customers
DELETE FROM Salespeople



INSERT INTO Cars(Make, Model, Year, Color, Price, QuantityInStock) VALUES
('BMW', 'E92 M3', 2009, 'Titanium Silver', 35000, 15),
('Mercedes-Benz', 'C63 AMG', 2015, 'Black', 55000, 10),
('Audi', 'SQ7', 2018, 'White', 70000, 20),
('Dacia', 'Logan', 2023, 'Arctic White', 15000, 30),
('BMW', 'G80 M3', 2023, 'British Green', 90000, 10),
('BMW', 'F90 M5', 2022, 'Candy Red', 80000, 25),
('Mercedes-Benz', 'GT63 AMG', 2020, 'Gunpowder Grey', 50000, 15),
('Volkswagen', 'Sharan', 2003, 'Alb Frigider', 200000, 1)



INSERT INTO Features(Name, Description) VALUES
('AC', 'Air Conditioning'),
('HS', 'Heated Seats'),
('VS', 'Ventilated Seats'),
('MS', 'Massaged Seats'),
('DA', 'Driving Assists'),
('ABS', 'Anti-Lock Braking System'),
('TC', 'Traction Control'),
('SDM', 'Self-Driving Mode')



INSERT INTO CarsAndFeatures(CarID, FeatureID) VALUES
(1, 100), (1, 101), (1, 104), (1, 105), (1, 106), (2, 100), (2, 101), (2, 102), (2, 103), (2, 104), (2, 105), (2, 106), (4, 102), (4, 105)



INSERT INTO Customers(FirstName, LastName, Email, Phone, Address) VALUES
('Ionut', 'Popa', 'ionutpopa23@gmail.com', 0765869304, 'Bvd. 21 Decembrie 1989 nr. 162'),
('Marian', 'Petru', 'petru.marian@yahoo.com', 0735917950, 'Aleea Baisoara nr. 25'),
('Marius', 'Colin', 'colinmarius@outlook.com', 0784958201, 'Strada Panselutelor nr. 20'),
('Andrei', 'Olar', 'andreiolar96@gmail.com', 0735847291, 'Strada Fagurilor nr. 16'),
('Horia', 'Brad', 'bradhoria92@outlook.com', 0756839175, 'Calea Dorobantilor nr. 64')



INSERT INTO Salespeople(FirstName, LastName, Email, Phone) VALUES
('Dorian', 'Popa', 'dorianhatz@gmail.com', 0739850281),
('Andrei', 'Selaru', 'selly@gmail.com', 0750273413)



INSERT INTO Sales(CarID, CustomerID, SalespersonID, SaleDate, TotalAmount) VALUES
(1, 201, 300, '2023-10-28', 35000),
(2, 201, 300, '2023-10-28', 55000),
(1, 203, 301, '2023-10-22', 35000),
(5, 201, 300, '2023-09-20', 90000)



INSERT INTO Payments(SaleID, PaymentDate, PaymentAmount) VALUES
(400, '2023-10-28', 35000),
(401, '2023-10-28', 20000),
(401, '2023-10-29', 35000),
(402, '2023-10-23', 35000),
(403, '2023-09-20', 90000)



INSERT INTO Repairs(CarID, RepairDate, Description, Cost) VALUES
(1, '2023-01-23', 'Cracked Front Lip', 1000),
(4, '2023-01-22', 'Faulty Turbocharger', 2500),
(5, '2023-05-26', 'Cracked Back Bumper', 5000)



INSERT INTO Services(CarID, ServiceDate, Description, Cost) VALUES
(3, '2023-05-30', 'Oil Change', 100),
(2, '2022-12-25', 'Fuel Filter Change', 50),
(4, '2023-10-29', 'Coolant Change', 25)



INSERT INTO Inventory(CarID, PurchaseDate, PurchasePrice, PurchaseQuantity) VALUES
(1, '2023-05-12', 350000, 10),
(1, '2023-08-10', 175000, 5),
(5, '2023-01-01', 900000, 10),
(4, '2022-10-23', 150000, 10)



SELECT * FROM Cars
SELECT * FROM Features
SELECT * FROM CarsAndFeatures
SELECT * FROM Customers
SELECT * FROM Salespeople
SELECT * FROM Sales
SELECT * FROM Payments
SELECT * FROM Repairs
SELECT * FROM Services
SELECT * FROM Inventory
