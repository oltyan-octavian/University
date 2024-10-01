#pragma once
#ifndef STRUCTURI
#define STRUCTURI

struct Familie
{
	int suma, zi;
	char *tip;
};

#endif

Familie* createfam();
void destroyfam(Familie* fam);
int get_suma(Familie* fam);
int get_zi(Familie* fam);
char* get_tip(Familie* fam);
void set_suma(Familie* fam, int suma);
void set_zi(Familie* fam, int zi);
void set_tip(Familie* fam, char tip[]);