#pragma once
#include "domain.h"
#include <ostream>

using std::ostream;

class Validator
{
public:
	void validare(const Activitate& act);
};

class ValidException {
	string msg;
public:
	ValidException(string m) :msg{ m } {}
	//functie friend (vreau sa folosesc membru privat msg)
	friend ostream& operator<<(ostream& out, const ValidException& ex);
};

ostream& operator<<(ostream& out, const ValidException& ex);