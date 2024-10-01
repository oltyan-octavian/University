#include "IteratorMultime.h"
#include "Multime.h"
#include <exception>

IteratorMultime::IteratorMultime(const Multime& m) : mult(m) {
	/* de adaugat */
	curent = m.prim;
}

TElem IteratorMultime::element() const {
	/* de adaugat */
	if (!valid())
		throw std::exception();
	return mult.e[curent];
}

bool IteratorMultime::valid() const {
	/* de adaugat */
	return curent != -1;
}

void IteratorMultime::urmator() {
	/* de adaugat */
	if (!valid())
		throw std::exception();
	curent = mult.urm[curent];
}

void IteratorMultime::anterior() {
	/* de adaugat */
	if (!valid())
		throw std::exception();
	if (curent == mult.prim)
		curent = -1;
	else
		curent = mult.prec[curent];
}

void IteratorMultime::prim() {
	/* de adaugat */
	curent = mult.prim;
}

