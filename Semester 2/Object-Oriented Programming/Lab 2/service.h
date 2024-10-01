#pragma once
#include "repo.h"
#ifndef SERVICE
#define SERVICE

#endif

Repository createrepo();
void add_to_repo(Repository &repo, Familie* fam);
void modify_repo(Repository &repo, int pozitie, int suma, int zi, char tip[]);
void stergere_repo(Repository &repo, int pozitie);
void ordonare(Repository& repo, char ordine[], char filtru[]);