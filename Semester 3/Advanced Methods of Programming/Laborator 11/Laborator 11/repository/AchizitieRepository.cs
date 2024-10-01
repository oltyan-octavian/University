using Laborator_11.domain;

namespace Laborator_11.repository;

public class AchizitieRepository:FileRepository<string, Achizitie>
{
    public FacturaRepository RepoFacturi;

    public AchizitieRepository(string fileName, FacturaRepository repoFacturi) : base(fileName,
        ToFile.CreateAchizitie)
    {
        this.RepoFacturi = repoFacturi;
        loadFromFile();
    }
    
    protected void loadFromFile()
    {
        List<Achizitie> list = DataRead.ReadData(FileName, CreateEntity);
        list.ForEach(x => Entities[x.Id] = x);
        for (int i = 0; i < list.Count; i++)
        {
            adaugaAchizitieFactura(list[i], list[i].Factura.Id);
        }
    }
    public void adaugaAchizitieFactura(Achizitie achizitie, string idFactura)
    {
        RepoFacturi.adaugaAchizitie(idFactura, achizitie);
    }
    
}