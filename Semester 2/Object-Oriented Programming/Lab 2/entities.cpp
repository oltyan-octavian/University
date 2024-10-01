#include "entities.h"
#include <stdlib.h>
#include <string>

Familie* createfam()
{
	Familie *fam;
	fam = (Familie*)malloc(sizeof(Familie));
	if (fam != NULL)
	{
		fam->tip = (char*)malloc(17 * sizeof(char));
		fam->suma = 1;
		fam->zi = 1;
	}
	return fam;
}

void destroyfam(Familie* fam)
{
	free(fam->tip);
	free(fam);
}

int get_suma(Familie* fam)
{
	return fam->suma;
}

int get_zi(Familie* fam)
{
	return fam->zi;
}

char* get_tip(Familie* fam)
{
	return fam->tip;
}

void set_suma(Familie* fam, int suma)
{
	fam->suma = suma;
}

void set_zi(Familie* fam, int zi)
{
	fam->zi = zi;
}

void set_tip(Familie* fam, char tip[])
{
	strcpy_s(fam->tip, 17, tip);
}