using Laborator_11.domain;

namespace Laborator_11.repository;

public class FacturaRepository:FileRepository<string,Factura>
{
    public DocumentRepository RepoDocument;

    public FacturaRepository(string fileName, DocumentRepository repoDocument) : base(fileName,
        ToFile.CreateFactura)
    {
        this.RepoDocument = repoDocument;
        loadFromFile();
    }
    
    protected void loadFromFile()
    {
        List<Factura> list = DataRead.ReadData(FileName, CreateEntity);
        list.ForEach(x => Entities[x.Id] = x);
        list.ForEach(x => Entities[x.Id] = FindOne(x.Id));
    }
    
    public Factura FindOne(string id)
    {
        IEnumerable<Document> documente = RepoDocument.FindAll();
        Factura rezultat = null;
        foreach (var factura in Entities.Values)
        {
            if (id == factura.Id)
            {
                rezultat = factura;
                break;
            }
        }

        if (rezultat == null)
        {
            throw new NullReferenceException("Nu exista factura cu acest id");
        }
        
        foreach (var doc in documente)
        {
            if (doc.Id == id)
            {
                rezultat.Nume = doc.Nume;
                rezultat.DataEmitere = doc.DataEmitere;
                break;
            }
        }

        return rezultat;
    }
    
    public void adaugaAchizitie(string id, Achizitie achizitie)
    {
        foreach (var f in Entities.Values)
        {
            if (id == f.Id)
            {
                f.Achizitii.Add(achizitie);
                break;
            }
        }
    }


}