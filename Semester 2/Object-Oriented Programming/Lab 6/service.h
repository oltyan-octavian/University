#pragma once
#include "domain.h"
#include "repo.h"
#include "validator.h"
#include <string>
#include <vector>

using std::string;
using std::vector;

class Service
{
	Repository& repo;
	Validator& valid;
public:
	Service(Repository& repo, Validator& valid) noexcept : repo{ repo }, valid{ valid } {}
	Service(const Service& ot) = delete;
	/*
	Returneaza lista de activitati.
	*/
	VectDinNewDeleteT<Activitate>& getAll() noexcept
	{
		return repo.getAll();
	}
	/*
	Adauga activitate in lista.
	*/
	void adaugaActivitate(const string& titlu, const string& descriere, const string& tip, int durata);
	/*
	Cauta activitate in lista.
	*/
	const Activitate& cautaActivitate(string titlu);
	/*
	Sterge activitate din lista.
	*/
	void stergeActivitate(const string& titlu);
	/*
	Filtrare dupa descriere.
	*/
	void filtraredescriere(const string& descriere, VectDinNewDeleteT<Activitate>& copie);
	/*
	Filtrare dupa tip.
	*/
	void filtraretip(const string& descriere, VectDinNewDeleteT<Activitate>& copie);
	/*
	Sortare in lista.
	*/
	void sortare(const string& filtru);
	/*
	Modifica activitate din lista.
	*/
	void modificaActivitate(const string& titlu, const string& descriere, const string& tip, int durata);
};