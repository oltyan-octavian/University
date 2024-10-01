#pragma once

class Iterator;

typedef int TComparabil;
typedef TComparabil TElement;

typedef bool (*Relatie)(TElement, TElement);

#define NULL_TELEMENT -1

class Nod {
private:
	TElement cheie;
	Nod* prec;
	Nod* urm;
public:
	Nod() : cheie{ NULL_TELEMENT }, prec{ nullptr }, urm{ nullptr } {}
	Nod(TElement c) : cheie{ c }, prec { nullptr }, urm{ nullptr } {}
	Nod(TElement c, Nod* p, Nod* u) : cheie{ c }, prec{ p }, urm{ u } {}
	TElement getCheie() {
		return this->cheie;
	}
	Nod* getPrec() {
		return this->prec;
	}
	Nod* getUrm() {
		return this->urm;
	}
	void setPrec(Nod* nod) {
		this->prec = nod;
	}
	void setUrm(Nod* nod) {
		this->urm = nod;
	}
	~Nod() {}
};

class LO {
private:
	friend class Iterator;
private:
	Nod* prim;
	Nod* ultim;
	Relatie relatie;
public:
	// constructor
	//complexitate O(1)
	LO(Relatie r);

	// returnare dimensiune
	//complexitate O(n)
	int dim();

	// verifica daca LO e vida
	//complexitate O(1)
	bool vida() const;

	// returnare element
	//arunca exceptie daca i nu e valid
	//complexitate O(n)
	TElement element(int i);

	// adaugare element in LO a.i. sa se pastreze ordinea intre elemente
	//complexitate O(n)
	void adauga(TElement e);

	// sterge element de pe o pozitie i si returneaza elementul sters
	//arunca exceptie daca i nu e valid
	//complexitate O(n)
	//bc: O(1)
	//wc: O(n)
	//ac: O(n)
	TElement sterge(int i);

	// cauta element si returneaza prima pozitie pe care apare (sau -1)
	//complexitate overall O(n)
	//bc: O(1)
	//wc: O(n)
	//ac: O(n)
	int cauta(TElement e);

	// returnare iterator
	//complexitate O(1)
	Iterator iterator();

	//destructor
	//complexitate O(1)
	~LO();

};
