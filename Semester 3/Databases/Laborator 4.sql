USE Showroom
GO

-- Marca si modelul masinii, alaturi de pretul stocului
SELECT c.Make, c.Model, Sum(c.Price*c.QuantityInStock) AS PretStoc
FROM Cars c
WHERE Make = 'BMW'
GROUP BY Make, Model

-- Numele si prenumele clientilor, numele masinii si data in care au cumparat masina
SELECT c.FirstName, c.LastName, s.SaleDate, ca.Make, ca.Model
FROM Customers c
INNER JOIN Sales s ON c.CustomerID = s.CustomerID
INNER JOIN Cars ca ON s.CarID = ca.CarID;

-- Numele masinii, anul fabricatiei, culoarea si dotarile acestora in functie de ID
SELECT c.Make, c.Model, c.Year, c.Color, f.Description
FROM Cars c 
INNER JOIN CarsAndFeatures cf on c.CarID = cf.CarID 
INNER JOIN Features f on cf.FeatureID = f.FeatureID
WHERE c.CarID = 1

-- Numele si prenumele clientului, numele masinii si reprezentantul de vanzari
SELECT c.FirstName, c.LastName, s.SaleDate, ca.Make, ca.Model, sp.FirstName, sp.LastName
FROM Customers c
INNER JOIN Sales s ON c.CustomerID = s.CustomerID
INNER JOIN Cars ca ON s.CarID = ca.CarID
INNER JOIN Salespeople sp ON s.SalespersonID = sp.SalespersonID;

-- Sumele distincte incasate
SELECT DISTINCT PaymentAmount
FROM Payments

-- Numele si prenumele reprezentantilor de vanzari, alaturi de data si pretul masinilor pe care le au vandut
SELECT sp.FirstName, sp.LastName, s.SaleDate, c.Price
FROM Salespeople sp
INNER JOIN Sales s ON sp.SalespersonID = s.SalespersonID
INNER JOIN Cars c ON s.CarID = c.CarID

-- Marca si anul masinilor, ordonate descrescator
SELECT DISTINCT Year, Make
FROM Cars
ORDER BY Year DESC

-- Numele si prenumele clientilor, alaturi de dotarile pe care le au masinile lor
SELECT c.FirstName, c.LastName, f.Description
FROM Customers c
INNER JOIN Sales s ON c.CustomerID = s.CustomerID
INNER JOIN Cars ca ON s.CarID = ca.CarID
INNER JOIN CarsAndFeatures cf ON ca.CarID = cf.CarID
INNER JOIN Features f ON cf.FeatureID = f.FeatureID;

-- Cate marci de masini au peste 5 masini in stoc, alaturi de numarul acestora
SELECT COUNT(Make) as MakeCount
FROM Cars
WHERE QuantityInStock > 5
GROUP BY Make
HAVING COUNT (Make) > 1

-- Numele si prenumele clientilor care stau pe "Strada ...", alaturi de email-ul reprezentantului de vanzari care le-a vandut masina
SELECT c.FirstName, c.LastName, sp.Email
FROM Customers c
INNER JOIN Sales s ON c.CustomerID = s.CustomerID
INNER JOIN Salespeople sp ON s.SalespersonID = sp.SalespersonID
WHERE Address LIKE 'Strada%'

-- Numarul de dotari de pe masinile care au ID mai mic de 3
SELECT COUNT(FeatureID) as FeatureCount
FROM CarsAndFeatures
WHERE CarID  < 3
GROUP BY CarID
HAVING COUNT(FeatureID) > 4

-- Descrierea reparatiei, costul reparatiei, anul masinii si numarul de masini in stoc
SELECT r.Description, r.Cost, c.Year, i.PurchaseQuantity
FROM Repairs r
INNER JOIN Cars c ON r.CarID = c.CarID
INNER JOIN Inventory i ON c.CarID = i.CarID
WHERE r.Description LIKE 'Faulty%'

-- Numele si prenumele reprezentantilor de vanzari, masinile pe care le au vandut si dotarile de pe acestea
SELECT sp.FirstName, sp.LastName, c.Make, c.Model, f.Description
FROM Salespeople sp
INNER JOIN Sales s ON sp.SalespersonID = s.SalespersonID
INNER JOIN Cars c ON s.CarID = c.CarID
INNER JOIN CarsAndFeatures cf ON c.CarID = cf.CarID
INNER JOIN Features f ON cf.FeatureID = f.FeatureID