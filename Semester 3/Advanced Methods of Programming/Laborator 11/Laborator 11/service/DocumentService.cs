using Laborator_11.domain;
using Laborator_11.repository;

namespace Laborator_11.service;

public class DocumentService
{
    private IRepository<string, Document> repo;

    public DocumentService(IRepository<string, Document> repo)
    {
        this.repo = repo;
    }

    public List<Document> FindAllDocumente()
    {
        return repo.FindAll().ToList();
    }
}