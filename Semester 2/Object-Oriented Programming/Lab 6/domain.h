#pragma once
#include <string>

using std::string;

class Activitate
{
	std::string titlu;
	std::string descriere;
	std::string tip;
	int durata;
public:
	/*
	Constructor.
	*/
	Activitate(const string tit, const string desc, const string ti, int dur) :titlu{ tit }, descriere{ desc }, tip{ ti }, durata{ dur } {}
	Activitate() {}
	/*
	Getter pentru titlu.
	*/
	string getTitlu() const {
		return titlu;
	}
	/*
	Setter pentru titlu.
	*/
	void setTitlu(const string tit) {
		this->titlu = tit;
	}
	/*
	Getter pentru descriere.
	*/
	string getDescriere() const {
		return descriere;
	}
	/*
	Setter pentru descriere.
	*/
	void setDescriere(const string desc) {
		this->descriere = desc;
	}
	/*
	Getter pentru tip.
	*/
	string getTip() const {
		return tip;
	}
	/*
	Setter pentru tip.
	*/
	void setTip(const string ti) {
		this->tip = ti;
	}
	/*
	Getter pentru durata.
	*/
	int getDurata() const noexcept {
		return durata;
	}
	/*
	Setter pentru durata.
	*/
	void setDurata(const int dur) noexcept{
		this->durata = dur;
	}
};