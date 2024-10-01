use Showroom

--Validate the cars
go
create or alter function ValidateCars(@year int, @price int, @quantity int)
returns varchar(20)
as
begin
	declare @error varchar(20)
	set @error=''
	if @year < 1920
	begin
		set @error='error from year'
	end
	if @price < 0
	begin
		set @error='error from price'
	end
	if @quantity < 1
	begin
		set @error='error from quantity'
	end
	return @error
end
go

--Validate the features
go
create or alter function ValidateFeatures(@description varchar(100))
	returns varchar(20)
	as
	begin
		declare @error varchar(20)
		set @error=''
		if LEN(@description) < 10
		begin
		set @error='error from description'
		end
		return @error
	end
go


--Validate the m-n relationship
go
create or alter function ValidateCarsAndFeatures(@carID int, @featureID int)
returns varchar(200)
as
begin
	declare @errormessage varchar(200)
	set @errormessage=''
	if (not(exists(select * from Cars where CarID=@carID)))
		set @errormessage = @errormessage + 'there is no car with this id'

	if(not(exists(select * from Features where FeatureID=@featureID)))
		set @errormessage = @errormessage + 'there is no feature with this id'

	return @errormessage
end
go

	

CREATE TABLE LogTable
(	
	id INT primary key identity(1000,1),
	operationType VARCHAR(20),
	tableName VARCHAR(20),
	executionTime DATETIME,
)


go
create or alter procedure InsertCarsAndFeatures
(
	@carMake varchar(50),
    @carModel varchar(50),
    @carYear int,
    @carColor varchar(50),
    @carPrice int,
    @carQuantityInStock int,
    @featureName varchar(100),
    @featureDescription varchar(100)
)
as
begin
		begin tran
		begin try
		declare @error varchar(200)
		set @error=''
		set @error=dbo.ValidateFeatures(@featureDescription)
		if @error != ''
			RAISERROR(@error, 14, 1)
		insert into Features(Name, Description) values(@featureName, @featureDescription)
		insert into LogTable(operationType,tableName,executionTime) values('Insert','Features',GETDATE())
		DECLARE @featureID int;
        SET @featureID = (select max(FeatureID) from Features);


		SET @error = dbo.ValidateCars(@carYear, @carPrice, @carQuantityInStock)
		if @error != ''
			RAISERROR(@error, 14, 1)
		insert into Cars(Make, Model, Year, Color, Price, QuantityInStock) values (@carMake, @carModel, @carYear, @carColor, @carPrice, @carQuantityInStock)
		insert into LogTable(operationType,tableName,executionTime) values('Insert','Cars',GETDATE())
		DECLARE @carID int;
        SET @carID = (select max(CarID) from Cars);

		set @error = dbo.ValidateCarsAndFeatures(@carID, @featureID)
		if @error != ''
			RAISERROR(@error, 14, 1)
		insert into CarsAndFeatures(CarID, FeatureID) values(@carID, @featureID)
		insert into LogTable(operationType,tableName,executionTime) values('Insert','CarsAndFeatures',GETDATE())
		commit tran
		select 'Transaction Commited'
		end try
		begin catch
		Rollback tran
		select 'Transaction Rollbacked'
		end catch
end
go


------
execute dbo.InsertCarsAndFeatures  
	@carMake = 'BMW',
    @carModel = '7 Series',
    @carYear = 2019,
    @carColor = 'Alpine White',
    @carPrice = 90000,
	@carQuantityInStock = 13,

    @featureName= 'Television Screen',
    @featureDescription = 'TV Screen in the back of the car';

select * from Cars
select * from Features
select * from CarsAndFeatures
select * from LogTable

----Gresit
execute dbo.InsertCarsAndFeatures 
	@carMake = 'BMW',
    @carModel = '7 Series',
    @carYear = 130,
    @carColor = 'Alpine White',
    @carPrice = 90000,
	@carQuantityInStock = 13,

    @featureName= 'Television Screen',
    @featureDescription = 'TV Screen in the back of the car';
select * from Cars
select * from Features
select * from CarsAndFeatures
select * from LogTable


go



go
create or alter procedure InsertWithAllRollbacks
(
	@carMake varchar(50),
    @carModel varchar(50),
    @carYear int,
    @carColor varchar(50),
    @carPrice int,
    @carQuantityInStock int,
    @featureName varchar(100),
    @featureDescription varchar(100)
)
as
begin
	declare @error1 varchar(200)
	declare @error2 varchar(200)
	declare @error3 varchar(200)
	DECLARE @featureID int;
	DECLARE @carID int;

	begin tran
	begin try
		set @error1=dbo.ValidateFeatures(@featureDescription)
		if @error1 != ''
			RAISERROR(@error1, 14, 1)
		insert into Features(Name, Description) values(@featureName, @featureDescription)
		insert into LogTable(operationType,tableName,executionTime) values('Insert','Features',GETDATE())
        SET @featureID = (select max(FeatureID) from Features)


		commit tran
		select 'transaction commited for table features'

	end try
	begin catch
		rollback tran
		select 'transaction for table features rollbacked'
	end catch
	begin tran
	begin try
		SET @error2 = dbo.ValidateCars(@carYear, @carPrice, @carQuantityInStock)
		if @error2 != ''
			RAISERROR(@error2, 14, 1)
		insert into Cars(Make, Model, Year, Color, Price, QuantityInStock) values (@carMake, @carModel, @carYear, @carColor, @carPrice, @carQuantityInStock)
		insert into LogTable(operationType,tableName,executionTime) values('Insert','Cars',GETDATE())
        SET @carID = (select max(CarID) from Cars)

			
			commit tran
			select 'transaction commited in table cars'
	end try
	begin catch
		rollback tran
		select ' transaction rollbacked in table cars'
	end catch

	begin tran
	begin try
		
		set @error3 = dbo.ValidateCarsAndFeatures(@carID, @featureID)
		if @error1 != '' or @error2 != '' or @error3 != ''
		begin
			RAISERROR(@error3,14,1)
		end
		insert into CarsAndFeatures(CarID, FeatureID) values(@carID, @featureID)
		insert into LogTable(operationType,tableName,executionTime) values('Insert','CarsAndFeatures',GETDATE())
		
		commit tran
		select 'transaction commited for table carsAndFeatures'
	end try
	begin catch
		rollback tran
		select 'transaction rollbacked for table carsAndFeatures'
	end catch
end
go


execute dbo.InsertWithAllRollbacks  
	@carMake = 'Alpina',
    @carModel = 'B12',
    @carYear = 2019,
    @carColor = 'Black',
    @carPrice = 190000,
	@carQuantityInStock = 13,

    @featureName= 'TV',
    @featureDescription = 'TV in the back of the car';

select * from Cars
select * from Features
select * from CarsAndFeatures
select * from LogTable

----Gresit
execute dbo.InsertWithAllRollbacks
	@carMake = 'Audi',
    @carModel = 'SQ7',
    @carYear = 130,
    @carColor = 'Purple Haze',
    @carPrice = 290000,
	@carQuantityInStock = 1,

    @featureName= 'AP',
    @featureDescription = 'Auto-Pilot';
select * from Cars
select * from Features
select * from CarsAndFeatures
select * from LogTable