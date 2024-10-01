#pragma once
#include "domain.h"
#include "repo.h"
#include "validator.h"
#include <string>
#include <vector>
#include <stack>

using std::string;
using std::stack;
using std::vector;
using std::make_unique;
using std::unique_ptr;

class ActiuneUndo {
public:
	virtual void doUndo() {};
	virtual ~ActiuneUndo() {};
};

class UndoAdauga : public ActiuneUndo {
private:
	Activitate activ;
	Repository& rep;
public:
	UndoAdauga(Activitate& act, Repository& reposit) : activ{ act }, rep{ reposit } {}
	void doUndo() override {
		rep.stergeActivitate(activ.getTitlu());
	}
};

class UndoSterge : public ActiuneUndo {
private:
	Activitate activ;
	Repository& rep;
public:
	UndoSterge(Activitate& act, Repository& reposit) : activ{ act }, rep{ reposit } {}
	void doUndo() override {
		rep.adaugaActivitate(activ);
	}
};

class UndoModifica : public ActiuneUndo {
private:
	Activitate activ;
	Repository& rep;
public:
	UndoModifica(Activitate& act, Repository& reposit) : activ{ act }, rep{ reposit } {}
	void doUndo() override {
		rep.modificaActivitate(activ.getTitlu(), activ.getDescriere(), activ.getTip(), activ.getDurata());
	}
};

class Service
{
	Repository& repo;
	Validator& valid;
	Lista& lista;
	vector<unique_ptr<ActiuneUndo>> undo;
public:
	Service(Repository& repo, Validator& valid, Lista& lst) noexcept : repo{ repo }, valid{ valid }, lista{ lst } {}
	Service(const Service& ot) = delete;
	/*
	Returneaza lista de activitati.
	*/
	vector <Activitate>& getAll() noexcept
	{
		return repo.getAll();
	}
	/*
	Adauga activitate in repo.
	*/
	void adaugaActivitate(const string& titlu, const string& descriere, const string& tip, int durata);
	/*
	Cauta activitate in repo.
	*/
	const Activitate& cautaActivitate(string titlu);
	/*
	Sterge activitate din repo.
	*/
	void stergeActivitate(const string& titlu);
	/*
	Filtrare dupa descriere.
	*/
	void filtraredescriere(const string& descriere, vector <Activitate>& copie);
	/*
	Filtrare dupa tip.
	*/
	void filtraretip(const string& tip, vector <Activitate>& copie);
	/*
	Sortare in repo.
	*/
	void sortare(const string& filtru);
	/*
	Modifica activitate din repo.
	*/
	void modificaActivitate(const string& titlu, const string& descriere, const string& tip, int durata);
	/*
	Adauga activitate in lista.
	*/
	void adaugaInLista(const string& titlu);
	/*
	Goleste lista de activitati.
	*/
	void golesteLista() noexcept;
	/*
	Genereaza o lista random de activitati.
	*/
	void genereazaLista(int numar);
	/*
	Returneaza lista de activitati.
	*/
	void serviceUndo();

	vector <string> getLista();
	map <string, int>& getMap() noexcept;
};

