#include <string>
#include "repo.h"
#include "entities.h"
#include <iostream>

using namespace std;

Repository createrepository()
{
	/*
	Creeaza un repository.
	post: repo, obiect de tip repository
	*/
	Repository repo{};
	repo.len = 0;
	repo.capacitate = 10;
	repo.v = (Familie**)malloc(10 * sizeof(Familie*));
	return repo;
}

void destroyrepository(Repository repo)
{
	for (int i = 0; i < repo.len; i++)
	{
		destroyfam(repo.v[i]);
	}
	free(repo.v);
}

void extendrepository(Repository repo)
{
	if (repo.len == repo.capacitate)
	{
		repo.capacitate = 2 * repo.capacitate;
		Familie** temp = (Familie**)realloc(repo.v, repo.capacitate * sizeof(Familie*));
		if (temp != NULL)
			repo.v = temp;
	}
}


void add_to_repository(Repository &repo, Familie* fam)
{
	/*
	Adauga o cheltuiala in repository.
	pre: repo, obiect de tip repository
		 fam, obiect de tip familie
	*/
	extendrepository(repo);
	repo.v[repo.len] = fam;
	repo.len++;
}

void modificare_repository(Repository &repo, int pozitie, int suma, int zi, char tip[])
{
	/*
	Modifica o cheltuiala din repository.
	pre: repo, obiect de tip repository
		 pozitie, int
		 suma, int
		 zi, vector de caractere
		 tip, vector de caractere
	*/
	set_suma(repo.v[pozitie - 1], suma);
	set_zi(repo.v[pozitie - 1], zi);
	set_tip(repo.v[pozitie - 1], tip);
}

void stergere_repository(Repository &repo, int pozitie)
{
	/*
	Sterge o cheltuiala din repository.
	pre: repo, obiect de tip repository
		 pozitie, int
	*/
	for (int i = pozitie - 1; i < repo.len - 1; i++)
	{
		repo.v[i] = repo.v[i + 1];
	}
	repo.len = repo.len - 1;
}