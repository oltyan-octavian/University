#pragma once
#include "domain.h"
#include <string>
#include <ostream>
#include <iostream>
#include <vector>
#include <map>

using std::string;
using std::map;
using std::vector;
using std::ostream;

class BaseRepo
{
public:
	virtual void metoda1() = 0;
	~BaseRepo() {}
};

class Repository : public BaseRepo
{
	vector <Activitate> all;
	map <string, int> contor;
public:
	Repository() = default;
	Repository(const Repository& ot) = delete;
	/*
	Adauga activitate in lista.
	*/
	bool exist(const Activitate& act) const;
	void adaugaActivitate(const Activitate& act);
	/*
	Cauta activitate in lista.
	*/
	const Activitate& cautaActivitate(string titlu) const;
	/*
	Returneaza lista de activitati.
	*/
	vector <Activitate>& getAll() noexcept;
	/*
	Sterge activitate din lista.
	*/
	void stergeActivitate(string titlu);
	/*
	Modifica activitate din lista.
	*/
	void modificaActivitate(const string& titlu, const string& descriere, const string& tip, int durata);
	map <string, int>& getMap() noexcept;

	void metoda1() override
	{
		std::cout << "metoda a fost apelata" << std::endl;
	}

};

class Lista
{
	vector <string> activitati;
public:
	Lista() = default;
	void golesteLista () noexcept;
	void adaugaInLista(const Activitate& act, const Repository& repo);
	vector <string> getLista();
};

class RepoException {
	string msg;
public:
	RepoException(string m) :msg{ m } {}
	//functie friend (vreau sa folosesc membru privat msg)
	friend ostream& operator<<(ostream& out, const RepoException& ex);
};

ostream& operator<<(ostream& out, const RepoException& ex);