#pragma once
#include "domain.h"
#include "vectordinamic.h"
#include <string>
#include <ostream>

using std::string;
using std::ostream;

class Repository
{
	VectDinNewDeleteT <Activitate> all;
	bool exist(const Activitate& act) const;
public:
	Repository() = default;
	Repository(const Repository& ot) = delete;
	/*
	Adauga activitate in lista.
	*/
	void adaugaActivitate(const Activitate& act);
	/*
	Cauta activitate in lista.
	*/
	const Activitate& cautaActivitate(string titlu) const;
	/*
	Returneaza lista de activitati.
	*/
	VectDinNewDeleteT<Activitate>& getAll() noexcept;
	/*
	Sterge activitate din lista.
	*/
	void stergeActivitate(string titlu);
	/*
	Modifica activitate din lista.
	*/
	void modificaActivitate(const string& titlu, const string& descriere, const string& tip, int durata);
};

class RepoException {
	string msg;
public:
	RepoException(string m) :msg{ m } {}
	//functie friend (vreau sa folosesc membru privat msg)
	friend ostream& operator<<(ostream& out, const RepoException& ex);
};

ostream& operator<<(ostream& out, const RepoException& ex);