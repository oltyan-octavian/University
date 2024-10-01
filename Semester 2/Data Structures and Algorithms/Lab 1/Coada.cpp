#include "Coada.h"
#include <exception>
#include <iostream>

using namespace std;


Coada::Coada() {
	this->v = (TElem*)malloc(10 * sizeof(TElem));
	this->lungime = 0;
	this->capacitate = 10;
	this->prim = 0;
}

/// <summary>
/// BC = WC = AC = O(1)
/// </summary>
void Coada::adauga(TElem elem) {
	if (this->lungime == this->capacitate)
	{
		TElem* aux;
		aux = (TElem*)realloc(v, static_cast<unsigned long long>(2) * this->capacitate * sizeof(TElem));
		if (aux != nullptr)
		{
			this->v = aux;
			this->capacitate = 2 * this->capacitate;
		}
	}
	this->v[this->lungime] = elem;
	this->lungime += 1;
}


//arunca exceptie daca coada e vida
/// <summary>
/// BC = WC = AC = O(1)
/// </summary>
TElem Coada::element() const {
	if (this->lungime == 0)
		throw runtime_error("ERR: Coada este vida.");
	else
		return this->v[this->prim];
}

/// <summary>
/// BC = WC = AC = O(n)
/// </summary>
TElem Coada::sterge() {
	if (this->lungime != 0 && this->prim != this->capacitate)
	{
		int poz_ant = prim;
		this->prim++;
		return this->v[poz_ant];
	}
	else
		throw runtime_error("ERR: Coada este vida.");
}

/// <summary>
/// BC = WC = AC = O(1)
/// </summary>
bool Coada::vida() const {
	if (this->lungime != this->prim)
		return false;
	return true;
}


Coada::~Coada() {
	free(v);
	this->capacitate = 0;
	this->lungime = 0;
}

