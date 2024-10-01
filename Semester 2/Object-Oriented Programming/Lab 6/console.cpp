#include "console.h"
#include "domain.h"
#include <iostream>
#include <string>

using std::cout;
using std::cin;
using std::exception;

void Console::tipareste(const VectDinNewDeleteT<Activitate>& lista)
{
	cout << "Activitati:\n";
	for (int i = 0; i < lista.size(); i++)
	{
		cout << " " << lista.get(i).getTitlu() << " " << lista.get(i).getDescriere() << " " << lista.get(i).getTip() << " " << lista.get(i).getDurata() << "\n";
	}
}

void Console::adaugaUI()
{
	string tit, desc, tip;
	int dur;
	cout << "Introduceti titlul: ";
	cin >> tit;
	cout << "Introduceti descrierea: ";
	cin >> desc;
	cout << "Introduceti tipul: ";
	cin >> tip;
	cout << "Introduceti durata: ";
	cin >> dur;
	try
	{
		serv.adaugaActivitate(tit, desc, tip, dur);
		cout << "Adaugat cu succes.\n";
	}
	catch (const exception& ex)
	{
		cout << ex.what()<<"\n";
		cout << "Adaugare esuata.\n";
	}

	
}

void Console::cautaActivitate()
{
	string titlu;
	cout << "Introduceti titlul: ";
	cin >> titlu;
	Activitate act = serv.cautaActivitate(titlu);
	cout << " " << act.getTitlu() << " " << act.getDescriere() << " " << act.getTip() << " " << act.getDurata() << "\n";
}

void Console::stergeActivitate()
{
	string titlu;
	cout << "Introduceti titlul: ";
	cin >> titlu;
	serv.stergeActivitate(titlu);
}

void Console::modificaActivitate()
{
	string tit, desc, tip;
	int dur;
	cout << "Introduceti titlul: ";
	cin >> tit;
	cout << "Introduceti descrierea: ";
	cin >> desc;
	cout << "Introduceti tipul: ";
	cin >> tip;
	cout << "Introduceti durata: ";
	cin >> dur;
	serv.modificaActivitate(tit, desc, tip, dur);
}

void Console::filtraredescriere()
{
	string desc;
	VectDinNewDeleteT<Activitate> copie(serv.getAll());
	cout << "Introduceti descrierea: ";
	cin >> desc;
	serv.filtraredescriere(desc, copie);
	tipareste(copie);
}

void Console::filtraretip()
{
	string tip;
	VectDinNewDeleteT<Activitate> copie(serv.getAll());
	cout << "Introduceti tipul: ";
	cin >> tip;
	serv.filtraretip(tip, copie);
	tipareste(copie);
}

void Console::sortare()
{
	string filtru;
	cout << "Introduceti filtrul dupa care sortati(titlu, descriere, tip): ";
	cin >> filtru;
	serv.sortare(filtru);
	tipareste(serv.getAll());
}

void Console::start()
{
	while (true)
	{
		cout << "Meniu: \n";
		cout << "1. Adauga activitate.\n";
		cout << "2. Tipareste activitatile.\n";
		cout << "3. Cauta activitate.\n";
		cout << "4. Sterge activitate.\n";
		cout << "5. Modifica activitate.\n";
		cout << "6. Filtreaza activitatile dupa descriere.\n";
		cout << "7. Filtreaza activitatile dupa tip.\n";
		cout << "8. Sortare.\n";
		cout << "10. Iesire.\n";
		cout << "Introduceti comanda: ";
		int cmd;
		cin >> cmd;
		try
		{
			switch (cmd) {
			case 1:
				adaugaUI();
				break;
			case 2:
				tipareste(serv.getAll());
				break;
			case 3:
				cautaActivitate();
				break;
			case 4:
				stergeActivitate();
				break;
			case 5:
				modificaActivitate();
				break;
			case 6:
				filtraredescriere();
				break;
			case 7:
				filtraretip();
				break;
			case 8:
				sortare();
				break;
			case 10:
				return;
			default:
				cout << "Comanda invalida.\n";
			}
		}
		catch (const RepoException& ex) {
			cout << ex << '\n';
		}
	}
}