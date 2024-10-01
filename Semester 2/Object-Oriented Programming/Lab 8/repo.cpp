#include "repo.h"
#include "domain.h"
#include <iostream>
#include <map>

using std::cout;
using std::map;

void Repository::adaugaActivitate(const Activitate& act)
{
	if (exist(act))
	{
		throw RepoException("Exista deja o activitate cu titlul: " + act.getTitlu());
	}
	all.push_back(act);
	map <string, int> :: iterator it;
	it = contor.find(act.getTip());
	if (it != contor.end())
	{
		it->second++;
	}
	else
	{
		contor.insert(std::pair<string, int>(act.getTip(), 1));
	}
	metoda1();
}

bool Repository::exist(const Activitate& act) const
{
	try
	{
		cautaActivitate(act.getTitlu());
		return true;
	}
	catch (RepoException&)
	{
		return false;
	}
}

const Activitate& Repository::cautaActivitate(string titlu) const
{
	for(auto& i: all)
	{
		if (i.getTitlu() == titlu)
			return i;
	}
	throw RepoException("Nu exista activitate cu titlul: " + titlu);
}

vector <Activitate>& Repository::getAll() noexcept
{
	return all;
}

void Repository::stergeActivitate(string titlu)
{
	int poz = -1;
	int act = 0;
	for (auto& i: all)
	{
		if (i.getTitlu() == titlu)
			poz = act;
		act++;
	}
	if (poz == -1)
		throw RepoException("Nu exista activitate cu titlul: " + titlu);
	else
	{
		map <string, int> ::iterator it;
		it = contor.find(all.at(poz).getTip());
		it->second--;
		all.erase(all.begin() + poz);
	}
	metoda1();
}

map <string, int>& Repository::getMap() noexcept
{
	return contor;
}

void Repository::modificaActivitate(const string& titlu, const string& descriere, const string& tip, int durata)
{
	for (auto& i: all)
	{
		if (i.getTitlu() == titlu)
		{
			i.setDescriere(descriere);
			i.setTip(tip);
			i.setDurata(durata);
		}
	}
	metoda1();
}

void Lista::adaugaInLista(const Activitate& act, const Repository& repo)
{
	if (repo.exist(act) == false)
	{
		throw RepoException("Nu exista activitatea in lista totala de activitati.");
	}
	else
	{
		for (auto& i: activitati)
		{
			if (act.getTitlu() == i)
				throw RepoException("Activitatea este deja adaugata in lista.");
		}
		activitati.push_back(act.getTitlu());
	}
}

void Lista::golesteLista () noexcept
{
	activitati.clear();
}

vector <string> Lista::getLista()
{
	return activitati;
}

ostream& operator<<(ostream& out, const RepoException& ex) {
	out << ex.msg;
	return out;
}
