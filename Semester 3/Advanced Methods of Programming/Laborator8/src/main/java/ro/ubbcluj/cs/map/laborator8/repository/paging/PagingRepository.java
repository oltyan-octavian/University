package ro.ubbcluj.cs.map.laborator8.repository.paging;

import ro.ubbcluj.cs.map.laborator8.domain.Entity;
import ro.ubbcluj.cs.map.laborator8.repository.Repository;

public interface PagingRepository <ID, E extends Entity<ID>> extends Repository<ID,E>{
    Page<E> findAll(Pageable pageable);
}
