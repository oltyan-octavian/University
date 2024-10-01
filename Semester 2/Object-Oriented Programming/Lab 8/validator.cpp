#include "validator.h"
#include <string>
#include <exception>

void Validator::validare(const Activitate& act)
{
	if (act.getDescriere().length() < 2)
		throw ValidException("Descrierea trebuie sa fie mai lunga de 2 caractere.");
	if (act.getTitlu().length() < 2)
		throw ValidException("Titlul trebuie sa fie mai lung de 2 caractere.");
	if (act.getTip().length() < 2)
		throw ValidException("Tipul activitatii trebuie sa fie mai lung de 2 caractere.");
	if (act.getDurata() < 0)
		throw ValidException("Durata evenimentului trebuie sa fie mai mare decat 0.");
}


ostream& operator<<(ostream& out, const ValidException& ex) {
	out << ex.msg;
	return out;
}