#pragma once
#include "service.h"
#include "domain.h"

class Console
{
	Service& serv;
	void adaugaUI();
	void tipareste(const vector <Activitate>& lista);
	void cautaActivitate();
	void stergeActivitate();
	void modificaActivitate();
	void filtraretip();
	void filtraredescriere();
	void sortare();
	void adaugaInLista();
	void tiparesteLista();
	void genereazaLista();
	void golesteLista();
	void tiparesteMap();
	void exportare();
	void consoleUndo();
public:
	Console(Service& serv) noexcept :serv{ serv } {}
	Console(const Console& ot) = delete;
	/*
	Porneste consola.
	*/
	void start();
};