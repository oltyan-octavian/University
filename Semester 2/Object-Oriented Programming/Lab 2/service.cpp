#include <string>
#include "entities.h"
#include "repo.h"
#include "service.h"

using namespace std;

Repository createrepo()
{
	/*
	Creeaza repository.
	post: repo, obiect de tip repository
	*/
	Repository repo{};
	repo = createrepository();
	return repo;
}

void add_to_repo(Repository& repo, Familie* fam)
{
	/*
	Adauga o cheltuiala in repository.
	pre: repo, obiect de tip repository
		 fam, obiect de tip familie
	*/
	add_to_repository(repo, fam);
}

void modify_repo(Repository& repo, int pozitie, int suma, int zi, char tip[])
{
	/*
	Modifica o cheltuiala din repository.
	pre: repo, obiect de tip repository
		 pozitie, int
		 suma, int
		 zi, vector de caractere
		 tip, vector de caractere
	*/
	modificare_repository(repo, pozitie, suma, zi, tip);
}

void stergere_repo(Repository& repo, int pozitie)
{
	/*
	Sterge o cheltuiala din repository.
	pre: repo, obiect de tip repository
		 pozitie, int
	*/
	stergere_repository(repo, pozitie);
}

int parametru_ordonare(char ordine[], char filtru[])
{
	if (strcmp(ordine, "crescator") == 0 && strcmp(filtru, "suma") == 0)
	{
		return 1;
	}
	if (strcmp(ordine, "descrescator") == 0 && strcmp(filtru, "suma") == 0)
	{
		return 2;
	}
	if (strcmp(ordine, "crescator") == 0 && strcmp(filtru, "tip") == 0)
	{
		return 3;
	}
	if (strcmp(ordine, "descrescator") == 0 && strcmp(filtru, "tip") == 0)
	{
		return 4;
	}
	return 0;
}

void ordonare(Repository& repo, char ordine[], char filtru[])
{
	/*
	Ordoneaza cheltuielile din repository.
	pre: repo, obiect de tip repository
		 ordine, string care e fie "crescator", fie "descrescator"
		 filtru, string care e fie "suma", fie "tip"
	*/
	int i, j, p;
	p = parametru_ordonare(ordine, filtru);
	for (i = 0; i < repo.len; i++)
	{
		for (j = i + 1; j < repo.len; j++)
		{
			if (p == 1)
				if (get_suma(repo.v[i]) > get_suma(repo.v[j]))
					swap(repo.v[i], repo.v[j]);
			if (p == 2)
				if (get_suma(repo.v[i]) < get_suma(repo.v[j]))
					swap(repo.v[i], repo.v[j]);
			if (p == 3)
				if (strcmp(repo.v[i]->tip, repo.v[j]->tip) > 0)
					swap(repo.v[i], repo.v[j]);
			if (p == 4)
				if (strcmp(repo.v[i]->tip, repo.v[j]->tip) < 0)
					swap(repo.v[i], repo.v[j]);
		}
	}
}