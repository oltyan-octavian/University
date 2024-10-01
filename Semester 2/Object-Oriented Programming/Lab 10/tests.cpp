#include "domain.h"
#include "repo.h"
#include "service.h"
#include "tests.h"
#include <cassert>
#include <vector>

using std::string;
using std::vector;
using std::exception;

void testDomain()
{
	string titlu = "titlu";
	string descriere = "descriere";
	string tip = "tip";
	constexpr int durata = 2;
	Activitate act{ titlu, descriere, tip, durata };
	assert(act.getTitlu() == "titlu");
	assert(act.getDescriere() == "descriere");
	assert(act.getTip() == "tip");
	assert(act.getDurata() == 2);
	act.setTitlu("altTitlu");
	act.setDescriere("altaDescriere");
	act.setTip("altTip");
	act.setDurata(3);
	assert(act.getTitlu() == "altTitlu");
	assert(act.getDescriere() == "altaDescriere");
	assert(act.getTip() == "altTip");
	assert(act.getDurata() == 3);
}

void testRepo()
{
	Repository repo;
	Activitate act{ "titlu", "descriere", "tip", 12 };
	repo.adaugaActivitate(act);
	assert(repo.getAll().size() == 1);
	Activitate ac = repo.cautaActivitate("titlu");
	assert(ac.getDescriere() == "descriere");
	try
	{
		ac = repo.cautaActivitate("lalala");
		assert(false);
	}
	catch (RepoException&)
	{
		assert(true);
	}
	try
	{
		Activitate act2{ "titlu", "descriere2", "tip2", 123 };
		repo.adaugaActivitate(act2);
		assert(false);
	}
	catch (RepoException&)
	{
		assert(true);
	}
	try
	{
		repo.stergeActivitate("lalalalalala");
		assert(false);
	}
	catch (RepoException&)
	{
		assert(true);
	}
	repo.stergeActivitate("titlu");
	assert(repo.getAll().size() == 0);
	Activitate act3{ "titlu", "descriere", "tip", 12 };
	repo.adaugaActivitate(act3);
	repo.modificaActivitate("titlu", "descrierenou", "tip", 111);
	Activitate ac2 = repo.cautaActivitate("titlu");
	assert(ac2.getDescriere() == "descrierenou");
}

void testService()
{
	Repository repo;
	Validator valid;
	Lista list;
	Service serv{ repo, valid, list };
	try
	{
		serv.serviceUndo();
		assert(false);
	}
	catch (RepoException&)
	{
		assert(true);
	}
	serv.adaugaActivitate("xd", "xd", "xd", 123);
	assert(serv.getAll().size() == 1);
	serv.serviceUndo();
	assert(serv.getAll().size() == 0);
	serv.adaugaActivitate("xd", "xd", "xd", 123);
	assert(serv.getAll().size() == 1);
	serv.modificaActivitate("xd", "xdd", "xdd", 12345);
	serv.serviceUndo();
	serv.stergeActivitate("xd");
	assert(serv.getAll().size() == 0);
	serv.serviceUndo();
	assert(serv.getAll().size() == 1);
	serv.stergeActivitate("xd");
	serv.adaugaActivitate("titlu", "descriere", "tip", 12);
	assert(serv.getAll().size() == 1);
	Activitate ac = serv.cautaActivitate("titlu");
	assert(ac.getDescriere() == "descriere");
	serv.stergeActivitate("titlu");
	assert(serv.getAll().size() == 0);
	serv.adaugaActivitate("titlu", "descriere", "tip", 12);
	serv.modificaActivitate("titlu", "descrierenou", "tip", 111);
	Activitate ac2 = serv.cautaActivitate("titlu");
	assert(ac2.getDescriere() == "descrierenou");
	try
	{
		serv.adaugaActivitate("t", "descriere", "tip", 123);
		assert(false);
	}
	catch (ValidException&)
	{
		assert(true);
	}
	try
	{
		serv.adaugaActivitate("titlu", "d", "tip", 123);
		assert(false);
	}
	catch (ValidException&)
	{
		assert(true);
	}
	try
	{
		serv.adaugaActivitate("titlu", "descriere", "t", 123);
		assert(false);
	}
	catch (ValidException&)
	{
		assert(true);
	}
	try
	{
		serv.adaugaActivitate("titlu", "descriere", "tip", -2);
		assert(false);
	}
	catch (ValidException&)
	{
		assert(true);
	}
	vector <Activitate> copie;
	serv.filtraredescriere("lalalalalalal", copie);
	assert(copie.size() == 0);
	serv.filtraredescriere("descrierenou", copie);
	vector <Activitate> copie2;
	serv.filtraretip("lalalalalalal", copie2);
	assert(copie2.size() == 0);
	serv.filtraretip("tip", copie);
	serv.stergeActivitate("titlu");
	serv.adaugaActivitate("titlu1", "descriere3", "tip1", 123);
	serv.adaugaActivitate("titlu2", "descriere2", "tip1", 122);
	serv.adaugaActivitate("titlu3", "descriere1", "tip3", 121);
	serv.sortare("descriere");
	assert(serv.getAll().at(0).getDurata() == 121);
	serv.sortare("titlu");
	assert(serv.getAll().at(0).getDurata() == 123);
	serv.adaugaActivitate("titlu4", "descriere1", "tip", 120);
	serv.sortare("tip");
	assert(serv.getAll().at(0).getDurata() == 120);
	serv.adaugaInLista("titlu1");
	Activitate act6{ "titlu123231", "dsds", "dsdsds", 1234 };
	try
	{
		serv.adaugaInLista("titlu1");
		assert(false);
	}
	catch (RepoException&)
	{
		assert(true);
	}
	try
	{
		list.adaugaInLista(act6, repo);
		assert(false);
	}
	catch (RepoException&)
	{
		assert(true);
	}
	serv.golesteLista();
	assert(serv.getLista().size() == 0);
	serv.genereazaLista(1234);
	assert(serv.getLista().size() == serv.getAll().size());
	serv.golesteLista();
	serv.genereazaLista(2);
	try
	{
		serv.genereazaLista(0);
		assert(false);
	}
	catch (RepoException&)
	{
		assert(true);
	}
	map <string, int> mp;
	mp = serv.getMap();
}

void testAll()
{
	testDomain();
	testRepo();
	testService();
}