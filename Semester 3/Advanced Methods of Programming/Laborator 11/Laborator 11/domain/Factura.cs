namespace Laborator_11.domain;

public enum Categorie
{
    Utilities,
    Groceries,
    Fashion,
    Entertainment,
    Electronics
}
public class Factura : Document
{
    public DateTime DataScadenta { get; set; }

    public List<Achizitie> Achizitii { get; set; }
     
    public Categorie Categorie { get; set; }
    
    public Factura()
    {
    }
    
    public Factura(string id, DateTime dataScadenta, Categorie categorie)
    {
        Id = id;
        DataScadenta = dataScadenta;
        Categorie = categorie;
        Achizitii = new List<Achizitie>();
    }
}