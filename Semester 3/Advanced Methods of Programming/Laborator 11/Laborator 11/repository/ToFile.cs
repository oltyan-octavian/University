using Laborator_11.domain;

namespace Laborator_11.repository;

public class ToFile
{
    public static Document CreateDocument(string line)
    {
        string[] fields = line.Split(',');

        Document document = new Document()
        {
            Id = fields[0],
            Nume = fields[1],
            DataEmitere = DateTime.Parse(fields[2])
        };

        return document;
    }
    
    public static Factura CreateFactura(string line)
    {
        string[] fields = line.Split(',');
        string newId = fields[0];
        DateTime newDataScadenta = DateTime.Parse(fields[1]);
        Categorie newTipCategorie = (Categorie)Enum.Parse(typeof(Categorie), fields[2]);
        Factura factura = new Factura(newId, newDataScadenta, newTipCategorie);
        return factura;
    }
    
    public static Achizitie CreateAchizitie(string line)
    {
        string[] fields = line.Split(',');
        string newId = fields[0];
        string newProdus = fields[1];
        int newCantitate = int.Parse(fields[2]);
        double newPret = double.Parse(fields[3]);
        string newIdDocument = fields[4];
        return new Achizitie(newId, newProdus, newCantitate, newPret, newIdDocument);
    }
    
}