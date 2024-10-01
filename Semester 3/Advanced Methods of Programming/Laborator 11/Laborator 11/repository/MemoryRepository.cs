using Laborator_11.domain;

namespace Laborator_11.repository;

public class MemoryRepository<ID,E>:IRepository<ID,E> where E: Entity<ID>
{
    protected IDictionary<ID, E> Entities = new Dictionary<ID, E>();
    
    public IEnumerable<E> FindAll()
    {
        return Entities.Values.ToList<E>();
    }

}