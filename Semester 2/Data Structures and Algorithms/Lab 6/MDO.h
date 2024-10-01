#pragma once

#include <vector>

typedef int TCheie;
typedef int TValoare;

#include <utility>
using std::vector;
typedef std::pair<TCheie, TValoare> TElem;
typedef std::pair<TCheie, vector<TValoare>> TSectiune;
#define MAXMD 100001 // maximul numarul elementelor
using namespace std;

class IteratorMDO;

typedef bool(*Relatie)(TCheie, TCheie);

class MDO {
	friend class IteratorMDO;
private:
	/* aici e reprezentarea */
	int m;
	TSectiune* elemente;
	int* urmator;
	int primLiber;
	Relatie rel;
	int nrelem;
	
	// complexitate: BC = O(1), WC = AC = OC = O(n)
	void update_primLiber();//actualizeaza primul liber

	// complexitate: O(1)
	int dispersie(TCheie c) const;//functia de dispersie

public:

	// constructorul implicit al MultiDictionarului Ordonat
	// complexitate: O(cap)
	MDO(Relatie r);

	// adauga o pereche (cheie, valoare) in MDO
	// complexitate: O(n)
	void adauga(TCheie c, TValoare v);

	//cauta o cheie si returneaza vectorul de valori asociate
	// complexitate: BC = O(1), WC = AC = OC = O(n)
	vector<TValoare> cauta(TCheie c) const;

	//sterge o cheie si o valoare 
	//returneaza adevarat daca s-a gasit cheia si valoarea de sters
	// complexitate: O(n)
	bool sterge(TCheie c, TValoare v);

	//returneaza numarul de perechi (cheie, valoare) din MDO 
	// complexitate: O(1)
	int dim() const;

	//verifica daca MultiDictionarul Ordonat e vid 
	// complexitate: O(1)
	bool vid() const;

	// se returneaza iterator pe MDO
	// iteratorul va returna perechile in ordine in raport cu relatia de ordine
	// complexitate: O(1)
	IteratorMDO iterator() const;
	
	
	// elimina o cheie impreuna cu toate valorile sale 
	// returneaza un vector cu valorile care au fost anterior asociate acestei chei (si au fost eliminate)
	// pseudocod:
	// Subprogram stergeValoriPentruCheie(mdo, cheie):
	//		vector <- cauta(mdo, cheie)
	//		pentru i=1, size(vector) executa:
	//			sterge(mdo, cheie, vector.elems[i])
	//		stergeValoriPentruCheie <- vector
	// SfSubprogram
	// complexitate: O(n)
	vector<TValoare> stergeValoriPentruCheie(TCheie cheie);

	// destructorul 	
	// complexitate: O(1)
	~MDO();

};
