#include "repo.h"
#include "domain.h"
#include <iostream>

using std::cout;

void Repository::adaugaActivitate(const Activitate& act)
{
	if (exist(act))
	{
		throw RepoException("Exista deja o activitate cu titlul: " + act.getTitlu());
	}
	all.add(act);
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
	for(int i = 0; i < all.size(); i++)
	{
		if (all.get(i).getTitlu() == titlu)
			return all.get(i);
	}
	throw RepoException("Nu exista activitate cu titlul: " + titlu);
}

VectDinNewDeleteT<Activitate>& Repository::getAll() noexcept
{
	return all;
}

void Repository::stergeActivitate(string titlu)
{
	int poz = -1;
	for (int i = 0; i < all.size(); i++)
	{
		if (all.get(i).getTitlu() == titlu)
			poz = i;
	}
	if (poz == -1)
		throw RepoException("Nu exista activitate cu titlul: " + titlu);
	else
		all.del(poz);
}

void Repository::modificaActivitate(const string& titlu, const string& descriere, const string& tip, int durata)
{
	int i = 0;
	for (i = 0; i < all.size(); i++)
	{
		if (all.get(i).getTitlu() == titlu)
		{
			Activitate nou(titlu, descriere, tip, durata);
			all.set(i, nou);
		}
	}
}

ostream& operator<<(ostream& out, const RepoException& ex) {
	out << ex.msg;
	return out;
}
