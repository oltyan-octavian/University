package ro.iss2024.laborator5iss.repository;


import ro.iss2024.laborator5iss.domain.Entity;

public interface IRepository<ID, E extends Entity<ID>> {
    E findOne(ID id);
    Iterable<E> findAll();
    E save(E entity);
    E delete(ID id);
    E update(E entity);
}
