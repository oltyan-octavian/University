#include <iostream>
#include <string>
#include "console.h"
#include "service.h"
#include "repo.h"
#include "entities.h"
#include "validator.h"

using namespace std;

void print_menu()
{
	/*
	Afiseaza meniul aplicatiei.
	*/
	cout << "1. Adaugare de cheltuieli. \n";
	cout << "2. Modificare cheltuiala. \n";
	cout << "3. Stergere cheltuiala. \n";
	cout << "4. Afisare cheltuieli. \n";
	cout << "5. Vizualizare lista de cheltuieli filtrat dupa o proprietate (suma, ziua, tipul). \n";
	cout << "6. Vizualizare lista de cheltuieli ordonat dupa suma sau tip (crescator/descrescator) \n";
	cout << "0. Iesire. \n";
	cout << "Alegere: ";
}

void add_to_rep(Repository &repo)
{
	/*
	Adauga o cheltuiala in repository.
	pre: repo, obiect de tip repository
		 zi, vector de caractere
		 tip, vector de caractere
		 suma, int
	*/
	Familie* fam;
	fam = createfam();
	char tip[17];
	int suma, zi;
	cout << "\nIntroduceti ziua in care s-a efectuat plata: ";
	cin >> zi;
	cin.ignore();
	cout << "Introduceti tipul cheltuielii : ";
	cin.getline(tip, 17);
	cout << "Introduceti suma platii: ";
	cin >> suma;
	set_zi(fam, zi);
	set_tip(fam, tip);
	set_suma(fam, suma);
	if (validare(fam) == true)
		add_to_repo(repo, fam);
	else
		cout << "Adaugarea a esuat. \n";
}

void modificare_rep(Repository& repo)
{
	/*
	Modifica o cheltuiala din repository.
	pre: repo, obiect de tip repository
		 pozitie, int
		 zi, vector de caractere
		 tip, vector de caractere
		 suma, int
	*/
	char tip[17];
	Familie* fam;
	fam = createfam();
	int suma, zi, pozitie;
	cout << "\nIntroduceti pozitia pe care se afla cheltuiala careia doriti sa ii efectuati modificari: ";
	cin >> pozitie;
	cout << "\nIntroduceti ziua in care s-a efectuat plata: ";
	cin >> zi;
	cin.ignore();
	cout << "Introduceti tipul cheltuielii : ";
	cin.getline(tip, 17);
	cout << "Introduceti suma platii: ";
	cin >> suma;
	set_suma(fam, suma);
	set_tip(fam, tip);
	set_zi(fam, zi);
	if (validare(fam) == false)
		cout << "Datele introduse nu sunt potrivite.\n";
	else
		if (pozitie > repo.len || pozitie <= 0)
			cout << "Pozitia nu e potrivita.\n";
		else
			modify_repo(repo, pozitie, suma, zi, tip);
	destroyfam(fam);
}

void stergere_rep(Repository& repo)
{
	/*
	Sterge o cheltuiala din repository.
	pre: repo, obiect de tip repository
		 pozitie, int
	*/
	int pozitie;
	cout << "\nIntroduceti pozitia pe care se afla cheltuiala pe care doriti sa o stergeti: ";
	cin >> pozitie;
	if (pozitie > repo.len || pozitie <= 0)
		cout << "Pozitia nu e potrivita.\n";
	else
		stergere_repo(repo, pozitie);
	
}

void afisare_rep(Repository repo)
{
	/*
	Afiseaza cheltuielile din repository.
	pre: repo, obiect de tip repository
	*/
	cout << endl;
	for (int i = 0; i < repo.len; i++)
	{
		cout << "Nr. " << i + 1 << endl;
		cout << "Ziua platii: " << get_zi(repo.v[i]);
		cout << endl;
		cout << "Tipul platii: ";
		for (int j = 0; j < strlen(get_tip(repo.v[i])); j++)
		{
			cout << get_tip(repo.v[i])[j];
		}
		cout << endl;
		cout << "Suma platita: " << get_suma(repo.v[i]);
		cout << endl << endl;
	}
}

void vizualizare_suma(Repository repo)
{
	/*
	Afiseaza cheltuielile din repository in functie de suma introdusa de la tastatura.
	pre: repo, obiect de tip repository
		 suma, int
	*/
	int suma;
	char* tip;
	cout << "Introduceti suma dorita: ";
	cin >> suma;
	cout << endl;
	cout << "Cheltuielile cu suma mai mica de " << suma << "sunt: \n";
	for (int i = 0; i < repo.len; i++)
	{
		if (get_suma(repo.v[i]) < suma)
		{
			cout << "Nr. " << i + 1 << endl;
			cout << "Ziua platii: " << get_zi(repo.v[i]);
			cout << endl;
			cout << "Tipul platii: ";
			tip = get_tip(repo.v[i]);
			for (int j = 0; j < strlen(tip); j++)
				cout << tip[j];
			cout << endl;
			cout << "Suma platita: " << get_suma(repo.v[i]);
			cout << endl << endl;
		}
	}
	cout << endl;
	cout << "Cheltuielile cu suma mai mare sau egala cu " << suma << "sunt: \n";
	for (int i = 0; i < repo.len; i++)
	{
		if (get_suma(repo.v[i]) > suma)
		{
			cout << "Nr. " << i + 1 << endl;
			cout << "Ziua platii: " << get_zi(repo.v[i]);
			cout << endl;
			cout << "Tipul platii: ";
			tip = get_tip(repo.v[i]);
			for (int j = 0; j < strlen(tip); j++)
				cout << tip[j];
			cout << endl;
			cout << "Suma platita: " << get_suma(repo.v[i]);
			cout << endl << endl;
		}
	}
}

void vizualizare_tip(Repository repo)
{
	/*
	Afiseaza cheltuielile din repository in functie de tipul introdus de la tastatura.
	pre: repo, obiect de tip repository
		 tip, string care e unul dintre "mancare", "transport", "telefon&internet", "imbracaminte", "altele"
	*/
	char tip[17], *tipul;
	cout << "Introduceti tipul cheltuielii: ";
	cin >> tip;
	cout << endl;
	for (int i = 0; i < repo.len; i++)
	{
		if (strcmp(tip, get_tip(repo.v[i])) == 0)
		{
			cout << "Nr. " << i + 1 << endl;
			cout << "Ziua platii: " << get_zi(repo.v[i]);
			cout << endl;
			cout << "Tipul platii: ";
			tipul = get_tip(repo.v[i]);
			for (int j = 0; j < strlen(tip); j++)
				cout << tip[j];
			cout << endl;
			cout << "Suma platita: " << get_suma(repo.v[i]);
			cout << endl << endl;
		}
	}
}

void vizualizare_zi(Repository repo)
{
	/*
	Afiseaza cheltuielile din repository in functie de ziua introdusa de la tastatura.
	pre: repo, obiect de tip repository
		 zi, int
	*/
	int zi;
	char* tip;
	cout << "Introduceti ziua dorita: ";
	cin >> zi;
	cout << endl;
	cout << "Cheltuielile dinaintea zilei de " << zi << " sunt: \n";
	for (int i = 0; i < repo.len; i++)
	{
		if (get_zi(repo.v[i]) < zi)
		{
			cout << "Nr. " << i + 1 << endl;
			cout << "Ziua platii: " << get_zi(repo.v[i]);
			cout << endl;
			cout << "Tipul platii: ";
			tip = get_tip(repo.v[i]);
			for (int j = 0; j < strlen(tip); j++)
				cout << tip[j];
			cout << endl;
			cout << "Suma platita: " << get_suma(repo.v[i]);
			cout << endl << endl;
		}
	}
	cout << endl;
	cout << "Cheltuielile de dupa ziua de " << zi << " sunt: \n";
	for (int i = 0; i < repo.len; i++)
	{
		if (get_zi(repo.v[i]) > zi)
		{
			cout << "Nr. " << i + 1 << endl;
			cout << "Ziua platii: " << get_zi(repo.v[i]);
			cout << endl;
			cout << "Tipul platii: ";
			tip = get_tip(repo.v[i]);
			for (int j = 0; j < strlen(tip); j++)
				cout << tip[j];
			cout << endl;
			cout << "Suma platita: " << get_suma(repo.v[i]);
			cout << endl << endl;
		}
	}
}

void vizualizare(Repository repo)
{
	/*
	Afiseaza cheltuielile din repository in functie de filtrul introdus de la tastatura.
	pre: repo, obiect de tip repository
		 filtru, string care e unul dintre "zi", "tip", "suma"
	*/
	char filtru[10];
	int ok = 1;
	cout << "Introduceti dupa ce doriti sa filtrati rezultatele: ";
	cin.ignore();
	cin.getline(filtru, 10);
	if (strcmp(filtru, "zi") == 0)
	{
		vizualizare_zi(repo);
		ok = 0;
	}
	if (strcmp(filtru, "tip") == 0)
	{
		vizualizare_tip(repo);
		ok = 0;
	}
	if (strcmp(filtru, "suma") == 0)
	{
		vizualizare_suma(repo);
		ok = 0;
	}
	if (ok == 1)
	{
		cout << "Nu ati introdus o optiune corecta.\n";
	}
}

void ordoneaza(Repository& repo)
{
	/*
	Ordoneaza cheltuielile din repository in functie de conditiile introduse.
	pre: repo, obiect de tip repository
		 ordine, string care e fie "crescator", fie "descrescator"
		 filtru, string care e fie "suma", fie "tip"
	*/
	char filtru[5], ordine[13];
	cout << "Introduceti dupa ce doriti sa ordonati rezultatele(suma/tip): ";
	cin.ignore();
	cin.getline(filtru, 5);
	cout << endl;
	cout << "Introduceti in ce ordine doriti sa fie rezultatele(crescator/descrescator): ";
	cin.getline(ordine, 13);
	ordonare(repo, ordine, filtru);
	afisare_rep(repo);
}

void meniu()
{
	/*
	Meniul aplicatiei.
	*/
	Repository repo{};
	repo = createrepo();
	bool ok = true;
	int alegere;
	while (ok == true)
	{
		print_menu();
		cin >> alegere;
		if (alegere == 0)
			ok = false;
		if (alegere == 1)
		{
			add_to_rep(repo);
			cout << '\n';
		}
		if (alegere == 2)
		{
			modificare_rep(repo);
			cout << '\n';
		}
		if (alegere == 3)
		{
			stergere_rep(repo);
			cout << '\n';
		}
		if (alegere == 4)
			afisare_rep(repo);
		if (alegere == 5)
			vizualizare(repo);
		if (alegere == 6)
			ordoneaza(repo);
		if (alegere > 6 || alegere < 0)
			cout << "Alegerea nu exista. \n";
	}
	destroyrepository(repo);
}