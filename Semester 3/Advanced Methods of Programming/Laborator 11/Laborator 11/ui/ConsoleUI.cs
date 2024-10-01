using Laborator_11.domain;
using Laborator_11.service;

namespace Laborator_11.ui;

public class ConsoleUI
{
    private DocumentService DocumentService;
    private FacturaService FacturaService;
    private AchizitieService AchizitieService;
    
    public ConsoleUI(DocumentService documentService, FacturaService facturaService, AchizitieService achizitieService)
    {
        DocumentService = documentService;
        FacturaService = facturaService;
        AchizitieService = achizitieService;
    }
    
    private static void PrintMenu()
    {
        Console.WriteLine("Meniu:");
        Console.WriteLine("1. Sa se afiseze toate documentele emise in 2023.");
        Console.WriteLine("2. Sa se afiseze toate facturile scadente in luna curenta.");
        Console.WriteLine("3. Sa se afiseze toate facturile cu cel putin 3 produse achizitionate.");
        Console.WriteLine("4. Sa se afisze toate achizitiile din categoria Utilities.");
        Console.WriteLine("5. Sa se afiseze categoria de facturi care a inregistrat cele mai multe cheltuieli.");
        Console.WriteLine("6. Exit");
    }

    private void DocumenteAn()
    {
        List<Document> documente = DocumentService.FindAllDocumente();
        var result = from document in documente
            where document.DataEmitere.Year == 2023
            select new { document.Nume, document.DataEmitere };
        
        foreach (var r in result)
        {
            Console.WriteLine(r);
        }
    }
    
    private void FacturiScadente()
    {
        List<Factura> facturi = FacturaService.FindAllFacturi();
        DateTime currentDate = DateTime.Now;
        int currentMonth = currentDate.Month;
        
        var facturiScadenteInLunaCurenta = from factura in facturi
            where factura.DataScadenta.Month == currentMonth
            select new { Nume = factura.Nume, DataScadenta = factura.DataScadenta };
        
        foreach (var factura in facturiScadenteInLunaCurenta)
        {
            Console.WriteLine($"{factura.Nume}, {factura.DataScadenta}");
        }
    }
    
    private void FacturiProduse()
    {
        var facturiCuCelPutin3Produse = from factura in FacturaService.FindAllFacturi()
            let sumaCantitati = factura.Achizitii.Sum(achizitie => achizitie.Cantitate)
            where sumaCantitati >= 3
            select new
            {
                NumeFactura = factura.Nume,
                NrProduse = sumaCantitati
            };
        
        foreach (var factura in facturiCuCelPutin3Produse)
        {
            Console.WriteLine($"{factura.NumeFactura}, {factura.NrProduse}");
        }
    }
    
    private void AchizitiiCategorie()
    {
        var achizitiiUtilities = from achizitie in AchizitieService.FindAllAchizitii()
            join factura in FacturaService.FindAllFacturi() on achizitie.Factura.Id equals factura.Id
            where factura.Categorie == Categorie.Utilities
            select new
            {
                Produs = achizitie.Produs,
                NumeFactura = factura.Nume
            };
        
        foreach (var achizitie in achizitiiUtilities)
        {
            Console.WriteLine($"{achizitie.Produs}, {achizitie.NumeFactura}");
        }
    }
    
    private void FacturiCategorie()
    {
        var cheltuieliPeCategorie = from factura in FacturaService.FindAllFacturi()
            join achizitie in AchizitieService.FindAllAchizitii() on factura.Id equals achizitie.Factura.Id
            group new { factura.Categorie, Cheltuieli = achizitie.Cantitate * achizitie.PretProdus } by factura.Categorie into grupCategorie
            select new
            {
                Categorie = grupCategorie.Key,
                TotalCheltuieli = grupCategorie.Sum(x => x.Cheltuieli)
            };
        
        var categoriaMaxCheltuieli = cheltuieliPeCategorie.OrderByDescending(x => x.TotalCheltuieli).FirstOrDefault();
        
        if (categoriaMaxCheltuieli != null)
        {
            Console.WriteLine($"Categoria cu cele mai multe cheltuieli: {categoriaMaxCheltuieli.Categorie}");
            Console.WriteLine($"Total cheltuieli: {categoriaMaxCheltuieli.TotalCheltuieli}");
        }
        else
        {
            Console.WriteLine("Nu exista date disponibile.");
        }
    }

    public void run()
    {
        bool run = true;
        PrintMenu();
        while (run)
        {
            Console.Write("\nIntroduceti comanda: ");
            string input = Console.ReadLine();
            int numar;
            try
            {
                numar = int.Parse(input);
            }
            catch (FormatException)
            {
                Console.WriteLine("Raspuns invalid.");
                numar = 7;
            }
            switch (numar)
            {
                case 0:
                    PrintMenu();
                    break;
                case 1:
                    DocumenteAn();
                    break;
                case 2:
                    FacturiScadente();
                    break;
                case 3:
                    FacturiProduse();
                    break;
                case 4:
                    AchizitiiCategorie();
                    break;
                case 5:
                    FacturiCategorie();
                    break;
                case 6:
                    run = false;
                    break;
                case 7:
                    break;
                default:
                    Console.WriteLine("Raspuns invalid.");
                    break;
            }
        }
    }
}