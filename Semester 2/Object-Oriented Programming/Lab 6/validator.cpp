#include "validator.h"
#include <string>
#include <exception>

void Validator::validare(const Activitate& act)
{
	if (act.getDescriere().length() < 2)
		throw std::exception("Descrierea trebuie sa fie mai lunga de 2 caractere.");
	if (act.getTitlu().length() < 2)
		throw std::exception("Titlul trebuie sa fie mai lung de 2 caractere.");
	if (act.getTip().length() < 2)
		throw std::exception("Tipul activitatii trebuie sa fie mai lung de 2 caractere.");
	if (act.getDurata() < 0)
		throw std::exception("Durata evenimentului trebuie sa fie mai mare decat 0.");
}
