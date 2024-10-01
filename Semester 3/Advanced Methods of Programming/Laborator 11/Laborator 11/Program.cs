using System.Reflection.Metadata;
using Laborator_11.repository;
using Laborator_11.service;
using Laborator_11.ui;
using Document = Laborator_11.domain.Document;


public class Program
{
    public static void Main()
    {
        DocumentRepository documentRepo = new DocumentRepository("C:\\Users\\Octa\\Documents\\Laboratoare Anul II\\MAP\\Laborator 11\\Laborator 11\\data\\documente.txt");
        DocumentService documentService = new DocumentService(documentRepo);


        FacturaRepository facturaRepo = new FacturaRepository("C:\\Users\\Octa\\Documents\\Laboratoare Anul II\\MAP\\Laborator 11\\Laborator 11\\data\\facturi.txt",documentRepo);
        FacturaService facturaService = new FacturaService(facturaRepo);


        AchizitieRepository achizitieRepo =
            new AchizitieRepository("C:\\Users\\Octa\\Documents\\Laboratoare Anul II\\MAP\\Laborator 11\\Laborator 11\\data\\achizitii.txt", facturaRepo);
        AchizitieService achizitieService = new AchizitieService(achizitieRepo);


        ConsoleUI consoleUi = new ConsoleUI(documentService, facturaService, achizitieService);
        consoleUi.run();
    }
}