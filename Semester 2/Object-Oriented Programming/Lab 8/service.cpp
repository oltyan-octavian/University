#include "service.h"
#include <random>
#include <exception>
#include "GSL-main/include/gsl/gsl"

void Service::adaugaActivitate(const string& titlu, const string& descriere, const string& tip, int durata)
{
	Activitate act{ titlu, descriere, tip, durata };
	valid.validare(act);
	undo.push_back(make_unique<UndoAdauga>(act, repo));
	repo.adaugaActivitate(act);
}

const Activitate& Service::cautaActivitate(string titlu)
{
	return repo.cautaActivitate(titlu);
}

void Service::stergeActivitate(const string& titlu)
{
	Activitate act = cautaActivitate(titlu);
	undo.push_back(make_unique<UndoSterge>(act, repo));
	repo.stergeActivitate(titlu);
}

void Service::filtraredescriere(const string& descriere, vector <Activitate>& copie)
{
	for (auto& i: repo.getAll())
	{
		if (i.getDescriere() == descriere)
		{
			copie.push_back(i);
		}
	}
}

void Service::filtraretip(const string& tip, vector <Activitate>& copie)
{
	for (auto& i : repo.getAll())
	{
		if (i.getTip() == tip)
		{
			copie.push_back(i);
		}
	}
}

void Service::modificaActivitate(const string& titlu, const string& descriere, const string& tip, int durata)
{
	Activitate act = cautaActivitate(titlu);
	undo.push_back(make_unique<UndoModifica>(act, repo));
	repo.modificaActivitate(titlu, descriere, tip, durata);
}

void Service::sortare(const string& filtru)
{
	if (filtru == "titlu")
	{
		std::sort(repo.getAll().begin(), repo.getAll().begin() + repo.getAll().size(), [](Activitate const& l, Activitate const& r) { return l.getTitlu() < r.getTitlu(); });
	}
	if (filtru == "descriere")
	{
		std::sort(repo.getAll().begin(), repo.getAll().begin() + repo.getAll().size(), [](Activitate const& l, Activitate const& r) { return l.getDescriere() < r.getDescriere(); });
	}
	if (filtru == "tip")
		std::sort(repo.getAll().begin(), repo.getAll().begin() + repo.getAll().size(), [](Activitate const& l, Activitate const& r) { 
		if (l.getTip() != r.getTip())
			return l.getTip() < r.getTip();
		else
			return l.getDurata() < r.getDurata();
			});
}

void Service::adaugaInLista(const string& titlu)
{
	lista.adaugaInLista(repo.cautaActivitate(titlu), repo);
}

void Service::golesteLista () noexcept
{
	lista.golesteLista();
}

void Service::genereazaLista(int numar)
{
	if (numar > repo.getAll().size())
	{
		for (auto& i: repo.getAll())
		{
			lista.adaugaInLista(i, repo);
		}
	}
	else
	{
		if (numar < 1)
			throw RepoException("Numarul este mai mic de 1, nicio activitate nu a fost adaugata in lista.");
		else
		{
			lista.golesteLista();
			int adaugate = 0;
			while (adaugate < numar)
			{
				std::mt19937 mt{ std::random_device{}() };
				const unsigned int size = gsl::narrow_cast<unsigned int>(repo.getAll().size());
				std::uniform_int_distribution<> dist(0, size - 1);
				const int rndNr = dist(mt);
				try
				{
					lista.adaugaInLista(repo.getAll().at(rndNr), repo);
					adaugate++;
				}
				catch (RepoException&)
				{
					;
				}
			}
		}
	}
}

vector <string> Service::getLista()
{
	return lista.getLista();
}

map <string, int>& Service::getMap() noexcept
{
	return repo.getMap();
}

void Service::serviceUndo()
{
	if (undo.empty())
	{
		throw RepoException("Nu se mai poate face undo!");
	}
	undo.back()->doUndo();
	undo.pop_back();
}