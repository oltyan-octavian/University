#pragma once
#include "Multime.h"

typedef int TElem;

class IteratorMultime
{
	friend class Multime;

private:

	//constructorul primeste o referinta catre Container
	//iteratorul va referi primul element din container
	//complexitate: O(1)
	IteratorMultime(const Multime& m);

	//contine o referinta catre containerul pe care il itereaza
	const Multime& mult;

	/* aici e reprezentarea  specifica a iteratorului */
	int curent;
public:

	//reseteaza pozitia iteratorului la inceputul containerului
	//complexitate: O(1)
	void prim();

	//muta iteratorul in container
	// arunca exceptie daca iteratorul nu e valid
	//complexitate: O(1)
	void urmator();

	//muta iteratorul inapoi in container
	// arunca exceptie daca iteratorul nu e valid
	// pre: it: iterator pe multimea mult
	// post: it': iterator pe multimea mult
	// daca !valid(it) atunci
	//		@arunca exceptie
	// sfdaca
	// daca it.curent = mult.prim atunci
	//		it.curent = -1
	// altfel
	//		it.curent = mult.prec[it.curent]
	// sfdaca
	//complexitate: O(1)
	void anterior();

	//verifica daca iteratorul e valid (indica un element al containerului)
	//complexitate: O(1)
	bool valid() const;

	//returneaza valoarea elementului din container referit de iterator
	//arunca exceptie daca iteratorul nu e valid
	//complexitate: O(1)
	TElem element() const;
};

