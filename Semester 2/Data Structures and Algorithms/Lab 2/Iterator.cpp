#include "Iterator.h"
#include "LO.h"
#include <exception>

Iterator::Iterator(const LO& lo) : lista(lo) {
	this->curent = lista.prim;
}

void Iterator::prim() {
	this->curent = lista.prim;
}

void Iterator::urmator() {
	if (this->valid())
		this->curent = this->curent->getUrm();
	else
		throw std::exception("Iteratorul nu este valid.");
}

bool Iterator::valid() const {
	if (this->curent != nullptr)
		return true;
	return false;
}

TElement Iterator::element() const {
	if (!this->valid())
		throw std::exception("Iteratorul nu este valid.");
	else
		return this->curent->getCheie();
}
