drop database Showroom

Create database Showroom
go
use Showroom
go

create table Cars(
CarID int primary key IDENTITY(1,1),
Make varchar(50),
Model varchar(50),
Year int,
Color varchar(50),
Price int,
QuantityInStock int
)

create table Customers(
CustomerID int primary key IDENTITY(200,1),
FirstName varchar(50),
LastName varchar(50),
Email varchar(50),
Phone int,
Address varchar(50)
)

create table Salespeople(
SalespersonID int primary key IDENTITY(300,1),
FirstName varchar(50),
LastName varchar(50),
Email varchar(50),
Phone int
)

create table Sales(
SaleID int primary key IDENTITY(400,1),
CarID int foreign key references Cars(CarID),
CustomerID int foreign key references Customers(CustomerID),
SalespersonID int foreign key references Salespeople(SalespersonID),
SaleDate date,
TotalAmount int
)

create table Payments(
PaymentID int primary key IDENTITY(500,1),
SaleID int foreign key references Sales(SaleID),
PaymentDate date,
PaymentAmount int
)

create table Repairs(
RepairID int primary key IDENTITY(600,1),
CarID int foreign key references Cars(CarID),
RepairDate date,
Description varchar(100),
Cost int
)

create table Services(
ServiceID int primary key IDENTITY(700,1),
CarID int foreign key references Cars(CarID),
ServiceDate date,
Description varchar(100),
Cost int
)

create table Inventory(
InventoryID int primary key IDENTITY(800,1),
CarID int foreign key references Cars(CarID),
PurchaseDate date,
PurchasePrice int,
PurchaseQuantity int
)

create table Features(
FeatureID int primary key IDENTITY(100,1),
Name varchar(100),
Description varchar(100)
)

create table CarsAndFeatures(
CarID int foreign key references Cars(CarID),
FeatureID int foreign key references Features(FeatureID),
constraint pk_CarsAndFeatures primary key (CarID, FeatureID)
)