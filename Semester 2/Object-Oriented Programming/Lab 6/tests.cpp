#include "domain.h"
#include "vectordinamic.h"
#include "repo.h"
#include "service.h"
#include "tests.h"
#include <cassert>

using std::string;
using std::exception;

void testVector()
{
	VectDinNewDeleteT<Activitate> vect;
	Activitate act1{ "titlu", "descriere", "tip", 123 };
	Activitate act2{ "titlu2", "descriere", "tip", 123 };
	Activitate act3{ "titlu3", "descriere", "tip", 123 };
	Activitate act4{ "titlu4", "descriere", "tip", 123 };
	Activitate act5{ "titlu5", "descriere", "tip", 123 };
	Activitate act6{ "titlu6", "descriere", "tip", 123 };
	vect.add(act1);
	vect.add(act2);
	vect.add(act3);
	vect.add(act4);
	vect.add(act5);
	vect.add(act6);
	VectDinNewDeleteT<Activitate> copie(vect);
	assert(copie.get(0).getDescriere() == "descriere");
	vect.del(0);
	assert(vect.size() == 5);
}

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
	Service serv{ repo, valid };
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
	catch (exception&)
	{
		assert(true);
	}
	try
	{
		serv.adaugaActivitate("titlu", "d", "tip", 123);
		assert(false);
	}
	catch (exception&)
	{
		assert(true);
	}
	try
	{
		serv.adaugaActivitate("titlu", "descriere", "t", 123);
		assert(false);
	}
	catch (exception&)
	{
		assert(true);
	}
	try
	{
		serv.adaugaActivitate("titlu", "descriere", "tip", -2);
		assert(false);
	}
	catch (exception&)
	{
		assert(true);
	}
	VectDinNewDeleteT<Activitate> copie(repo.getAll());
	serv.filtraredescriere("lalalalalalal", copie);
	assert(copie.size() == 0);
	VectDinNewDeleteT<Activitate> copie2(repo.getAll());
	serv.filtraretip("lalalalalalal", copie2);
	assert(copie.size() == 0);
	serv.stergeActivitate("titlu");
	serv.adaugaActivitate("titlu1", "descriere3", "tip1", 123);
	serv.adaugaActivitate("titlu2", "descriere2", "tip1", 122);
	serv.adaugaActivitate("titlu3", "descriere1", "tip3", 121);
	serv.sortare("descriere");
	assert(serv.getAll().get(0).getDurata() == 121);
	serv.sortare("titlu");
	assert(serv.getAll().get(0).getDurata() == 123);
	serv.adaugaActivitate("titlu4", "descriere1", "tip", 120);
	serv.sortare("tip");
	assert(serv.getAll().get(0).getDurata() == 120);
}

void testAll()
{
	testVector();
	testDomain();
	testRepo();
	testService();
}