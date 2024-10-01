namespace Laborator_11.domain;

public class Achizitie: Entity<string>
{
    public string Produs { get; set; }
    
    public int Cantitate { get; set; }
    
    public double PretProdus { get; set; }

    public Factura Factura { get; set; }
    
    public Achizitie(string id, string produs, int cantitate, double pretProdus, string idDocument)
    {
        Id = id;
        Produs = produs;
        Cantitate = cantitate;
        PretProdus = pretProdus;
        Factura = new Factura();
        Factura.Id = idDocument;
    }
    
    public override string ToString()
    {
        return Id + " " + Produs + " " + Cantitate + " " + PretProdus;
    }
}