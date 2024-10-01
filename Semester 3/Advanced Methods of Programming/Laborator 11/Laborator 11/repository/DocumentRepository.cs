using System.Data;
using Laborator_11.domain;

namespace Laborator_11.repository;

public class DocumentRepository: FileRepository<string,Document>
{
    public DocumentRepository(string fileName) : base(fileName, ToFile.CreateDocument)
    {
        loadFromFile();
    }

    protected void loadFromFile()
    {
        List<Document> list = DataRead.ReadData(FileName, CreateEntity);
        list.ForEach(x=>Entities[x.Id]=x);
    }

    public Document FindOne(string id)
    {
        foreach (Document entity in Entities.Values)
        {
            if (entity.Id == id)
            {
                return entity;
            }
        }

        return null;
    }

}