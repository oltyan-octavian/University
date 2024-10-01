#pragma once

#define CAP 10;
typedef int TElem;

typedef bool(*Relatie)(TElem, TElem);

//in implementarea operatiilor se va folosi functia (relatia) rel (de ex, pentru <=)
// va fi declarata in .h si implementata in .cpp ca functie externa colectiei
bool rel(TElem, TElem);

class IteratorMultime;

class Multime {

	friend class IteratorMultime;

private:
	/* aici e reprezentarea */
	int cp;
	int len;
	TElem* e;
	int* urm;
	int* prec;
	int prim;
	int primLiber;

	//complexitate: O(1)
	int aloca();
	
	//complexitate: O(1)
	void dealoca(int i);


	/*
	CF:theta(1) daca nu se face realocare
	CD: theta(n) daca se face realocare
	TOTAL: O(n)
	*/
	int creeazaNod(TElem e);

	//complexitate: O(n)
	void resize();
public:
	//constructorul implicit
	//complexitate: O(n)
	Multime();

	//adauga un element in multime
	//returneaza adevarat daca elementul a fost adaugat (nu exista deja in multime)
	/*
	CF:theta(1) daca se adauga primul element
	CD:theta(n^2) daca se face redimensionarea
	TOTAL: O(n^2)
	n - adaugari
	O(n) + O(n) + ... + O(n) (de n - 1 ori) / n = O(n) amortizat
	*/
	bool adauga(TElem e);

	//sterge un element din multime
	//returneaza adevarat daca elementul a existat si a fost sters
	//complexitate: O(1)
	bool sterge(TElem e);

	//verifica daca un element se afla in multime
	/*
	CF:theta(1) cand elementul se afla pe prima pozitie
	CD:theta(n) cand elementul nu se afla in lista
	TOTAL:O(n)
	*/
	bool cauta(TElem elem) const;

	//intoarce numarul de elemente din multime;
	//complexitate: O(1)
	int dim() const;

	//verifica daca multimea e vida;
	//complexitate: O(1)
	bool vida() const;

	//returneaza un iterator pe multime
	//complexitate: O(1)
	IteratorMultime iterator() const;

	// destructorul multimii
	~Multime();

};

