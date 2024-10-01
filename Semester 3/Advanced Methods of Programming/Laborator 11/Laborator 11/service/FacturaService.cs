using Laborator_11.domain;
using Laborator_11.repository;

namespace Laborator_11.service;

public class FacturaService
{
    public IRepository<string, Factura> repo;

    public FacturaService(IRepository<string, Factura> repo)
    {
        this.repo = repo;
    }
    
    public List<Factura> FindAllFacturi()
    {
        return repo.FindAll().ToList();
    }
}