#include "entities.h"
#include <string>
bool validare(Familie *fam)
{
	/*
	Verifica astfel incat fam sa fie o cheltuiala cu date valide.
	pre: fam, obiect de tip familie
	post: true, daca datele din fam sunt valide
		  false, altfel
	*/
	if (get_zi(fam) < 1 || get_zi(fam) > 31)
		return false;
	if (get_suma(fam) < 1)
		return false;
	if (strcmp(get_tip(fam), "mancare") == 0 || strcmp(get_tip(fam), "transport") == 0 || strcmp(get_tip(fam), "telefon&internet") == 0 || strcmp(get_tip(fam), "imbracaminte") == 0 || strcmp(get_tip(fam), "altele") == 0)
		return true;
	return false;
}