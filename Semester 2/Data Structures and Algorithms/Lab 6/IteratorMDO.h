#pragma once

#include "MDO.h"
#include <algorithm>

class IteratorMDO {
	friend class MDO;
private:

	//constructorul primeste o referinta catre Container
	//iteratorul va referi primul element din container
	// complexitate: O(1)
	IteratorMDO(const MDO& dictionar);

	//contine o referinta catre containerul pe care il itereaza
	const MDO& dict;
	/* aici e reprezentarea  specifica a iteratorului */
	vector <TSectiune> ord;
	int curentTD;
	int curentVECT;

public:

	//reseteaza pozitia iteratorului la inceputul containerului
	// complexitate: O(1)
	void prim();

	//muta iteratorul in container
	// arunca exceptie daca iteratorul nu e valid
	// complexitate: O(1)
	void urmator();

	//verifica daca iteratorul e valid (indica un element al containerului)
	// complexitate: O(1)
	bool valid() const;

	//returneaza valoarea elementului din container referit de iterator
	//arunca exceptie daca iteratorul nu e valid
	// complexitate: O(1)
	TElem element() const;
};

