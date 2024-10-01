#include "entities.h"
#include "repo.h"
#include "service.h"
#include "console.h"
#include "validator.h"
#include <string>
#include <cassert>
using namespace std;

void test_repo()
{
	/*
	Testele pentru repository.
	*/
	Repository test_repo{};
	test_repo = createrepository();
	assert(test_repo.len == 0);
	Familie* fam1, *fam2, *fam3, *fam4, *fam5, *fam6, *fam7, *fam8, *fam9, *fam10, *fam11, *fam12;
	fam1 = createfam();
	fam1->zi = 13;
	strcpy_s(fam1->tip, 17, "telefon&internet");
	fam1->suma = 1234;
	add_to_repository(test_repo, fam1);
	assert(test_repo.len == 1);
	assert(strcmp(get_tip(test_repo.v[0]), "telefon&internet") == 0);
	assert(get_zi(test_repo.v[0]) == 13);
	add_to_repository(test_repo, fam1);
	assert(test_repo.len == 2);
	int zi = 19;
	char tip[17] = "altele";
	modificare_repository(test_repo, 1, 123, zi, tip);
	assert(test_repo.v[0]->suma == 123);
	stergere_repository(test_repo, 1);
	assert(test_repo.len == 1);
	stergere_repository(test_repo, 1);
	assert(test_repo.len == 0);
	add_to_repository(test_repo, fam1);
	fam2 = createfam();
	fam2->zi = 13;
	strcpy_s(fam2->tip, 17, "telefon&internet");
	fam2->suma = 1234;
	add_to_repository(test_repo, fam2);
	fam3 = createfam();
	fam3->zi = 13;
	strcpy_s(fam3->tip, 17, "telefon&internet");
	fam3->suma = 1234;
	add_to_repository(test_repo, fam3);
	fam4 = createfam();
	fam4->zi = 13;
	strcpy_s(fam4->tip, 17, "telefon&internet");
	fam4->suma = 1234;
	add_to_repository(test_repo, fam4);
	fam5 = createfam();
	fam5->zi = 13;
	strcpy_s(fam5->tip, 17, "telefon&internet");
	fam5->suma = 1234;
	add_to_repository(test_repo, fam5);
	fam6 = createfam();
	fam6->zi = 13;
	strcpy_s(fam6->tip, 17, "telefon&internet");
	fam6->suma = 1234;
	add_to_repository(test_repo, fam6);
	fam7 = createfam();
	fam7->zi = 13;
	strcpy_s(fam7->tip, 17, "telefon&internet");
	fam7->suma = 1234;
	add_to_repository(test_repo, fam7);
	fam8 = createfam();
	fam8->zi = 13;
	strcpy_s(fam8->tip, 17, "telefon&internet");
	fam8->suma = 1234;
	add_to_repository(test_repo, fam8);
	fam9 = createfam();
	fam9->zi = 13;
	strcpy_s(fam9->tip, 17, "telefon&internet");
	fam9->suma = 1234;
	add_to_repository(test_repo, fam9);
	fam10 = createfam();
	fam10->zi = 13;
	strcpy_s(fam10->tip, 17, "telefon&internet");
	fam10->suma = 1234;
	add_to_repository(test_repo, fam10);
	fam11 = createfam();
	fam11->zi = 13;
	strcpy_s(fam11->tip, 17, "telefon&internet");
	fam11->suma = 1234;
	add_to_repository(test_repo, fam11);
	assert(test_repo.len == 11);
	fam12 = createfam();
	fam12->zi = 13;
	strcpy_s(fam12->tip, 17, "telefon");
	fam12->suma = 1234;
	assert(validare(fam12) == false);
	fam12->zi = -2;
	strcpy_s(fam12->tip, 17, "telefon&internet");
	fam12->suma = 1234;
	assert(validare(fam12) == false);
	fam12->zi = 13;
	strcpy_s(fam12->tip, 17, "telefon&internet");
	fam12->suma = -1;
	assert(validare(fam12) == false);
	fam12->zi = 13;
	strcpy_s(fam12->tip, 17, "telefon&internet");
	fam12->suma = 123;
	assert(validare(fam12) == true);
	destroyfam(fam12);
	destroyrepository(test_repo);
}

void test_service()
{
	/*
	Testele pentru service.
	*/
	char ordine[13], filtru[5];
	Repository test_repo{};
	test_repo = createrepo();
	assert(test_repo.len == 0);
	Familie* fam1;
	fam1 = createfam();
	fam1->zi = 13;
	strcpy_s(fam1->tip, 17, "telefon&internet");
	fam1->suma = 1234;
	add_to_repo(test_repo, fam1);
	assert(test_repo.len == 1);
	Familie* fam2;
	fam2 = createfam();
	fam2->zi = 19;
	strcpy_s(fam2->tip, 17, "altele");
	fam2->suma = 12345;
	add_to_repo(test_repo, fam2);
	Familie* fam3;
	fam3 = createfam();
	fam3->zi = 9;
	strcpy_s(fam3->tip, 17, "mancare");
	fam3->suma = 123;
	add_to_repo(test_repo, fam3);
	strcpy_s(ordine, 13, "crescator");
	strcpy_s(filtru, 5, "suma");
	ordonare(test_repo, ordine, filtru);
	assert(test_repo.v[0]->suma == 123);
	strcpy_s(ordine, 13, "crescator");
	strcpy_s(filtru, 5, "tip");
	ordonare(test_repo, ordine, filtru);
	assert(test_repo.v[0]->suma == 12345);
	strcpy_s(ordine, 13, "descrescator");
	strcpy_s(filtru, 5, "tip");
	ordonare(test_repo, ordine, filtru);
	assert(test_repo.v[0]->suma == 1234);
	strcpy_s(ordine, 13, "descrescator");
	strcpy_s(filtru, 5, "suma");
	ordonare(test_repo, ordine, filtru);
	assert(test_repo.v[0]->suma == 12345);
	assert(test_repo.len == 3);

	int zi = 19;
	char tip[17] = "altele";
	modify_repo(test_repo, 1, 123, zi, tip);
	assert(test_repo.v[0]->suma == 123);
	stergere_repo(test_repo, 1);
	assert(test_repo.len == 2);
	stergere_repo(test_repo, 1);
	assert(test_repo.len == 1);
	stergere_repo(test_repo, 1);
	assert(test_repo.len == 0);
	destroyrepository(test_repo);
}

void test_all()
{
	/*
	Apeleaza toate testele.
	*/
	test_repo();
	test_service();
}