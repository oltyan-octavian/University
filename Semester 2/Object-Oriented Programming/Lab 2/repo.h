#pragma once
#include "entities.h"
#ifndef REPO
#define REPO

struct Repository
{
	int len,capacitate;
	Familie** v;
};

#endif

Repository createrepository();
void destroyrepository(Repository repo);
void extendrepository(Repository repo);
void add_to_repository(Repository &repo, Familie* fam);
void modificare_repository(Repository &repo, int pozitie, int suma, int zi, char tip[]);
void stergere_repository(Repository &repo, int pozitie);