#include "Multime.h"
#include "IteratorMultime.h"
#include <iostream>

using namespace std;

//o posibila relatie
bool rel(TElem e1, TElem e2) {
	if (e1 <= e2) {
		return true;
	}
	else {
		return false;
	}
}

int Multime::aloca()
{
	int i = primLiber;
	primLiber = urm[primLiber];
	return i;
}

void Multime::dealoca(int i)
{
	urm[i] = primLiber;
	primLiber = i;
}

int Multime::creeazaNod(TElem e)
{
	if (primLiber == -1) 
	{
		int cpVeche = cp;
		resize();
		for (int i = cpVeche; i < cp - 1; i++)
			urm[i] = i + 1;
		urm[cp - 1] = -1;
		primLiber = cpVeche;
	}
	int i = aloca();
	if (i != -1) 
	{
		this->e[i] = e;
		urm[i] = -1;
		prec[i] = -1;
	}
	return i;
}

void Multime::resize()
{
	int new_cp = cp * 2;
	TElem* new_e = new TElem[new_cp];
	int* new_urm = new int[new_cp];
	int* new_prec = new int[new_cp];
	for (int i = 0; i < cp; i++) 
	{
		new_e[i] = e[i];
		new_urm[i] = urm[i];
		new_prec[i] = prec[i];
	}
	delete[] e;
	delete[] urm;
	delete[] prec;
	e = new_e;
	urm = new_urm;
	prec = new_prec;
	cp = new_cp;
}

Multime::Multime() {
	/* de adaugat */
	cp = CAP;
	e = new TElem[cp];
	urm = new int[cp];
	prec = new int[cp];

	for (int i = 0; i < cp - 1; i++)
		urm[i] = i + 1;

	urm[cp - 1] = -1;
	prim = -1;
	primLiber = 0;
	len = 0;
}


bool Multime::adauga(TElem elem) {
	/* de adaugat */
	if (cauta(elem))
		return false;
	int poz = creeazaNod(elem);
	if (prim == -1) 
	{
		prim = poz;
		len++;
		return true;
	}
	for (int i = prim; i != -1; i = urm[i])
		if (!rel(e[i], elem)) 
		{
			if (i != prim) 
			{
				urm[prec[i]] = poz;
				prec[poz] = prec[i];
				prec[i] = poz;
				urm[poz] = i;
			}
			else 
			{
				if (i == prim) 
				{
					urm[poz] = i;
					prec[i] = poz;
					prim = poz;
				}
			}
			len++;
			return true;
		}
		else 
		{
			if (urm[i] == -1) 
			{
				urm[i] = poz;
				prec[poz] = i;
				urm[poz] = -1;
				len++;
				return true;
			}
		}
}


bool Multime::sterge(TElem elem) {
	/* de adaugat */
	if (!cauta(elem))
		return false;
	if (urm[prim] == -1) 
	{
		dealoca(prim);
		len--;
		prim = -1;
		return true;
	}
	for (int i = prim; i != -1; i = urm[i])
		if (e[i] == elem) 
		{
			if (urm[i] != -1) 
			{
				if (i != prim) 
				{
					urm[prec[i]] = urm[i];
					prec[urm[i]] = prec[i];
				}
				else 
				{
					prec[urm[i]] = -1;
					prim = urm[i];
				}
			}
			else 
				urm[prec[i]] = -1;
			dealoca(i);
			len--;
			return true;
		}
}


bool Multime::cauta(TElem elem) const {
	/* de adaugat */
	for (int i = prim; i != -1; i = urm[i])
		if (e[i] == elem)
			return true;
	return false;
}


int Multime::dim() const {
	/* de adaugat */
	return len;
}



bool Multime::vida() const {
	/* de adaugat */
	return len <= 0;
}

IteratorMultime Multime::iterator() const {
	return IteratorMultime(*this);
}


Multime::~Multime() {
	/* de adaugat */
	delete[] e;
	delete[] urm;
	delete[] prec;
}
