#pragma once
#include "service.h"
#include "domain.h"

class Console
{
	Service& serv;
	void adaugaUI();
	void tipareste(const VectDinNewDeleteT<Activitate>& lista);
	void cautaActivitate();
	void stergeActivitate();
	void modificaActivitate();
	void filtraretip();
	void filtraredescriere();
	void sortare();
public:
	Console(Service& serv) noexcept :serv{ serv } {}
	Console(const Console& ot) = delete;
	/*
	Porneste consola.
	*/
	void start();
};