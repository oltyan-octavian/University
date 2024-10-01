#include "IteratorMDO.h"
#include "MDO.h"
#include <iostream>
#include <vector>
#include <exception>
using namespace std;

int MDO::dispersie(TCheie c) const
{
	return abs(c) % m;
}

void MDO::update_primLiber()
{
	primLiber++;
	while (primLiber < m && elemente[primLiber].first != -1)
		primLiber++;
}

MDO::MDO(Relatie r) {
	/* de adaugat */
	m = MAXMD;
	elemente = new TSectiune[m];
	urmator = new int[m];
	for (int i = 0; i < m; i++)
	{
		urmator[i] = -1;
		elemente[i].first = -1;
	}
	primLiber = 0;
	rel = r;
	nrelem = 0;
}


void MDO::adauga(TCheie c, TValoare v) {
	/* de adaugat */
	nrelem++;
	int i = dispersie(c);
	if (elemente[i].first == -1)
	{
		elemente[i].first = c;
		elemente[i].second.push_back(v);
		if (i == primLiber)
			update_primLiber();
		return;
	}
	int j = -1;
	while (elemente[i].first != c && urmator[i] != -1)
	{
		j = i;
		i = urmator[i];
	}
	if (elemente[i].first == c)
	{
		elemente[i].second.push_back(v);
		if (i == primLiber)
			update_primLiber();
		return;
	}
	if (primLiber >= m)
	{
		nrelem--;
		throw exception();
	}

	elemente[primLiber].first = c;
	elemente[primLiber].second.push_back(v);
	urmator[j] = primLiber;
	update_primLiber();
}

vector<TValoare> MDO::cauta(TCheie c) const {
	/* de adaugat */
	int i = dispersie(c);
	while (i != -1 && elemente[i].first != c)
		i = urmator[i];
	if (i != -1)
		return elemente[i].second;
	return vector<TValoare>();
}

bool MDO::sterge(TCheie c, TValoare v) {
	/* de adaugat */
	int i = dispersie(c);
	int j = -1;
	int k = 0;

	//verificam daca i are element anterior
	while (k < m && j == -1)
	{
		if (urmator[k] == i)
			j = k;
		else
			k++;
	}

	//localizam cheia c in lista intrepatrunsa
	while (elemente[i].first != c && i != -1)
	{
		j = i;
		i = urmator[i];
	}

	//daca nu exista cheia c in lista intrepatrunsa returnam fals
	if (i == -1)
		return false;

	bool sters = false;

	//localizam valoarea v in lista de valori a cheii c
	for (auto val = elemente[i].second.begin(); val != elemente[i].second.end(); val++)
		if (*val == v)
		{
			elemente[i].second.erase(val);
			sters = true;
			break;
		}

	// in cazul in care nu s-a gasit perechea cheie-valoare, se va returna fals
	if (!sters) {
		return false;
	}

	nrelem--;

	// daca vectorul asociat cheii a ramas fara elemente, vom sterge vectorul din lista
	if (elemente[i].second.size() == 0)
	{
		bool gata = false;
		while (!gata)
		{
			int p = urmator[i];
			int pp = i;
			while (p != -1 && dispersie(elemente[p].first) != i)
			{
				pp = p;
				p = urmator[p];
			}
			if (p == -1)
			{
				gata = true;
			}
			else
			{
				elemente[i] = elemente[p];
				j = pp;
				i = p;
			}
		}
		if (j != -1)
			urmator[j] = urmator[i];
		elemente[i].first = -1;
		elemente[i].second.clear();
		urmator[i] = -1;
		if (primLiber > i)
			primLiber = i;
	}
	return true;
}

int MDO::dim() const {
	/* de adaugat */
	return nrelem;
}

bool MDO::vid() const {
	/* de adaugat */
	if (nrelem == 0)
		return true;
	return false;
}

IteratorMDO MDO::iterator() const {
	return IteratorMDO(*this);
}

vector<TValoare> MDO::stergeValoriPentruCheie(TCheie cheie)
{
	vector<TValoare> vect;
	vect = cauta(cheie);
	for (int i = 0; i < vect.size(); i++)
	{
		sterge(cheie, vect.at(i));
	}
	return vect;
}

MDO::~MDO() {
	/* de adaugat */
	delete[] elemente;
	delete[] urmator;
}
