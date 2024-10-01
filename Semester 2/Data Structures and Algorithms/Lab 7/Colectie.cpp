#include "Colectie.h"
#include "IteratorColectie.h"
#include <exception>
#include <iostream>

using namespace std;


Colectie::Colectie() {
	cap = 10;
	elems = new TElem[cap];
	st = new int[cap];
	dr = new int[cap];
	primLiber = 1;
	rad = 0;
	for (int i = 0; i < cap; i++)
	{
		elems[i] = NULL_TELEM;
		st[i] = 0;
		dr[i] = 0;
	}
	n = 0;
}

void Colectie::updatePrimLiber()
{
	primLiber++;
	while (primLiber < cap && elems[primLiber] == NULL_TELEM)
		primLiber++;
}

void Colectie::resize()
{
	TElem* newElems = new TElem[2 * cap];
	int* newSt = new int[2 * cap];
	int* newDr = new int[2 * cap];
	for (int i = cap; i < 2 * cap; i++)
	{
		elems[i] = NULL_TELEM;
		st[i] = 0;
		dr[i] = 0;
	}
	primLiber = cap;
	cap = 2 * cap;
}

int Colectie::adaugaRec(int poz, TElem e)
{
	if (elems[poz] == NULL_TELEM)
	{
		elems[poz] = e;
	}
	else
	{
		if (e <= elems[poz])
		{
			st[poz] = adaugaRec(st[poz], e);
		}
		else
		{
			dr[poz] = adaugaRec(dr[poz], e);
		}
	}
	return poz;
}

void Colectie::adauga(TElem elem) {
	rad = adaugaRec(rad, elem);
	n++;
}

int Colectie::minim()
{
	int min = rad;
	while (st[min] != 0)
		min = st[min];
	return min;
}

bool Colectie::stergeRec(int poz, TElem e)
{
	if (elems[poz] == NULL_TELEM)
		return false;
	else
	{
		if (e < elems[poz])
		{
			st[p] = 
		}
	}
}

bool Colectie::sterge(TElem elem) {
	if (!cauta(elem))
		return false;

}

bool Colectie::cautaRec(int poz, TElem e)
{
	if (elems[poz] == e)
		return true;
	if (elems[poz] == NULL_TELEM)
		return false;
	if (elems[poz] < e)
		return cautaRec(st[poz], e);
	else
		return cautaRec(dr[poz], e);
}

bool Colectie::cauta(TElem elem) {
	return cautaRec(rad, elem);
}

int Colectie::nrAparitii(TElem elem) const {
	/* de adaugat */
	return 0;
}


int Colectie::dim() const {
	return n;
}


bool Colectie::vida() const {
	if (n == 0)
		return true;
	return false;
}

IteratorColectie Colectie::iterator() const {
	return  IteratorColectie(*this);
}


Colectie::~Colectie() {
	delete[] elems;
	delete[] st;
	delete[] dr;
}


