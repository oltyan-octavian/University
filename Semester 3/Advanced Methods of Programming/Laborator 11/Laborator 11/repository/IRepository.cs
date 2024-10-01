using Laborator_11.domain;

namespace Laborator_11.repository;

public interface IRepository<ID, E> where E:Entity <ID>
{

    IEnumerable<E> FindAll();
}