using Laborator_11.domain;

namespace Laborator_11.repository;

public delegate E CreateEntity<E>(string line);

public class FileRepository<ID,E> : MemoryRepository<ID, E>  where E: Entity<ID>
{

    protected string FileName;

    protected CreateEntity<E> CreateEntity;

    public FileRepository(string fileName, CreateEntity<E> createEntity) : base()
    {
        this.FileName = fileName;
        this.CreateEntity = createEntity;
    }

}