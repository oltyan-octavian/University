#include "service.h"

void Service::adaugaActivitate(const string& titlu, const string& descriere, const string& tip, int durata)
{
	Activitate act{ titlu, descriere, tip, durata };
	valid.validare(act);
	repo.adaugaActivitate(act);
}

const Activitate& Service::cautaActivitate(string titlu)
{
	return repo.cautaActivitate(titlu);
}

void Service::stergeActivitate(const string& titlu)
{
	repo.stergeActivitate(titlu);
}

void Service::filtraredescriere(const string& descriere, VectDinNewDeleteT<Activitate>& copie)
{
	for (int i = 0; i < repo.getAll().size(); i++)
	{
		if (repo.getAll().get(i).getDescriere() != descriere)
		{
			copie.del(i);
		}
	}
}

void Service::filtraretip(const string& descriere, VectDinNewDeleteT<Activitate>& copie)
{
	for (int i = 0; i < repo.getAll().size(); i++)
	{
		if (repo.getAll().get(i).getDescriere() != descriere)
		{
			copie.del(i);
		}
	}
}

void Service::modificaActivitate(const string& titlu, const string& descriere, const string& tip, int durata)
{
	repo.modificaActivitate(titlu, descriere, tip, durata);
}

void Service::sortare(const string& filtru)
{
	if (filtru == "titlu")
	{
		for (int i = 0; i < repo.getAll().size() - 1; i++)
		{
			for (int j = i + 1; j < repo.getAll().size(); j++)
			{
				if (repo.getAll().get(i).getTitlu() > repo.getAll().get(j).getTitlu())
				{
					Activitate aux;
					aux = repo.getAll().get(i);
					repo.getAll().set(i, repo.getAll().get(j));
					repo.getAll().set(j, aux);
				}
			}
		}
	}
	if (filtru == "descriere")
	{
		for (int i = 0; i < repo.getAll().size() - 1; i++)
		{
			for (int j = i + 1; j < repo.getAll().size(); j++)
			{
				if (repo.getAll().get(i).getDescriere() > repo.getAll().get(j).getDescriere())
				{
					Activitate aux;
					aux = repo.getAll().get(i);
					repo.getAll().set(i, repo.getAll().get(j));
					repo.getAll().set(j, aux);
				}
			}
		}
	}
	if (filtru == "tip")
	{
		for (int i = 0; i < repo.getAll().size() - 1; i++)
		{
			for (int j = i + 1; j < repo.getAll().size(); j++)
			{
				if (repo.getAll().get(i).getTip() > repo.getAll().get(j).getTip())
				{
					Activitate aux;
					aux = repo.getAll().get(i);
					repo.getAll().set(i, repo.getAll().get(j));
					repo.getAll().set(j, aux);
				}
				else
				{
					if (repo.getAll().get(i).getTip() == repo.getAll().get(j).getTip() && repo.getAll().get(i).getDurata() > repo.getAll().get(j).getDurata())
					{
						Activitate aux;
						aux = repo.getAll().get(i);
						repo.getAll().set(i, repo.getAll().get(j));
						repo.getAll().set(j, aux);
					}
				}
			}
		}
	}
}