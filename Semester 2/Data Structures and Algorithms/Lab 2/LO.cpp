#include "Iterator.h"
#include "LO.h"

#include <iostream>
using namespace std;

#include <exception>

LO::LO(Relatie r) {
	this->relatie = r;
	this->prim = nullptr;
	this->ultim = nullptr;
}

// returnare dimensiune
int LO::dim() {
	int dimensiune = 0;
	Iterator it = this->iterator();
	it.prim();
	while (it.valid())
	{
		it.urmator();
		dimensiune++;
	}
	return dimensiune;
}

// verifica daca LO e vida
bool LO::vida() const {
	if (this->prim == nullptr)
		return true;
	else
		return false;
}

// returnare element
//arunca exceptie daca i nu e valid
TElement LO::element(int i) {
	if (this->dim() <= i || i < 0)
		throw exception("Pozitia cautata nu este valida.");
	Iterator it = this->iterator();
	it.prim();
	int poz = 0;
	while (it.valid() && poz != i)
	{
		it.urmator();
		poz++;
	}
	return it.curent->getCheie();
}

// sterge element de pe o pozitie i si returneaza elementul sters
//arunca exceptie daca i nu e valid
TElement LO::sterge(int i) {
	TElement sters = NULL_TELEMENT;
	int poz = 0;
	if (i >= this->dim() || i < 0 || this->vida())
		throw exception("A aparut o eroare.");
	if (i == 0)
	{
		if (this->dim() == 1)
		{
			sters = this->prim->getCheie();
			delete this->prim;
			this->prim = nullptr;
			this->ultim = nullptr;
		}
		else
		{
			sters = this->prim->getCheie();
			Nod* urm = this->prim->getUrm();
			urm->setPrec(nullptr);
			delete this->prim;
			this->prim = urm;
		}
	}
	else
	{
		if (i == this->dim() - 1)
		{
			if (this->dim() == 1)
			{
				sters = this->ultim->getCheie();
				delete this->ultim;
				this->prim = nullptr;
				this->ultim = nullptr;
			}
			else
			{
				sters = this->ultim->getCheie();
				Nod* prec = this->ultim->getPrec();
				prec->setUrm(nullptr);
				delete this->ultim;
				this->ultim = prec;
			}
		}
		else
		{
			Iterator it = this->iterator();
			it.prim();
			int poz = 0;
			while (poz != i)
			{
				it.urmator();
				poz++;
			}
			sters = it.curent->getCheie();
			Nod* prec, * urm;
			prec = it.curent->getPrec();
			urm = it.curent->getUrm();
			it.curent->getPrec()->setUrm(urm);
			it.curent->getUrm()->setPrec(prec);
			delete it.curent;
		}
	}
	return sters;
}

// cauta element si returneaza prima pozitie pe care apare (sau -1)
int LO::cauta(TElement e) {
	Iterator it = this->iterator();
	it.prim();
	int pozitie = 0;
	if (this->prim == nullptr || !this->relatie(this->prim->getCheie(), e) || !this->relatie(e, this->ultim->getCheie()))
		return -1;
	while (it.valid() && this->relatie(it.curent->getCheie(), e) && !this->relatie(e, it.curent->getCheie()))
	{
		it.urmator();
		pozitie++;
	}
	if (it.valid() && this->relatie(it.curent->getCheie(), e) && this->relatie(e, it.curent->getCheie()))
		return pozitie;
	else
		return -1;
}

// adaugare element in LO
void LO::adauga(TElement e) {
	Nod* p = new Nod(e);
	Iterator it = this->iterator();
	it.prim();
	if (this->vida()) //daca e vida lista
	{
		this->prim = p;
		this->ultim = p;
	}
	else // daca nu e vida lista
	{
		if (this->relatie(e, this->prim->getCheie())) // daca e mai mic decat primul el din lista
		{
			this->prim->setPrec(p);
			p->setUrm(this->prim);
			this->prim = p;
		}
		else
			{
				while (it.valid() && this->relatie(it.curent->getCheie(), e))
				{
					it.urmator();
				}
				if (!it.valid())
				{
					this->ultim->setUrm(p);
					p->setPrec(this->ultim);
					this->ultim = p;
				}
				else
				{
					p->setUrm(it.curent);
					p->setPrec(it.curent->getPrec());
					it.curent->getPrec()->setUrm(p);
					it.curent->setPrec(p);
				}
			}
	}
}

// returnare iterator
Iterator LO::iterator() {
	return Iterator(*this);
}


//destructor
LO::~LO() {
	int n = this->dim();
	while (n > 0)
	{
		this->sterge(0);
		n--;
	}
}