namespace Laborator_11.domain;

public class Document: Entity<string>
{
    public string Nume { get; set; }
    
    public DateTime DataEmitere { get; set; }

    public Document()
    {
    }
    public Document(string id, string nume, DateTime dataEmitere)
    {
        Id = id;
        Nume = nume;
        DataEmitere = dataEmitere;
    }
    
    public override string ToString()
    {
        return Id + " " + Nume + " " + DataEmitere;
    }
}