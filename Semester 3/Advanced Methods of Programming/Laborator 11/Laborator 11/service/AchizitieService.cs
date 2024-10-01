using Laborator_11.domain;
using Laborator_11.repository;

namespace Laborator_11.service;

public class AchizitieService
{
    public IRepository<string, Achizitie> Repo;

    public AchizitieService(IRepository<string, Achizitie> Repo)
    {
        this.Repo = Repo;
    }

    public List<Achizitie> FindAllAchizitii()
    {
        return Repo.FindAll().ToList();
    }

}