#pragma once


#include "LO.h"

class LO;


class Iterator {
	friend class LO;
private:
	//constructorul primeste o referinta catre Container
	//iteratorul va referi primul element din container
	Iterator(const LO& lo);

	//contine o referinta catre containerul pe care il itereaza
	const LO& lista;

	/* aici e reprezentarea  specifica a iteratorului */
	Nod* curent;

public:

	//reseteaza pozitia iteratorului la inceputul containerului
	//complexitate O(1)
	void prim();

	//muta iteratorul in container
	// arunca exceptie daca iteratorul nu e valid
	//complexitate O(1)
	void urmator();

	//verifica daca iteratorul e valid (indica un element al containerului)
	//complexitate O(1)
	bool valid() const;

	//returneaza valoarea elementului din container referit de iterator
	//arunca exceptie daca iteratorul nu e valid
	//complexitate O(1)
	TElement element() const;
};
