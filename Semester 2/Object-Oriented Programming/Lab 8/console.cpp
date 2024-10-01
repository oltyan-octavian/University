#include "console.h"
#include "domain.h"
#include <iostream>
#include <fstream>
#include <string>

using std::cout;
using std::cin;
using std::exception;

void Console::tipareste(const vector <Activitate>& lista)
{
	cout << "Activitati:\n";
	for (auto& i: lista)
	{
		cout << " " << i.getTitlu() << " " << i.getDescriere() << " " << i.getTip() << " " << i.getDurata() << "\n";
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
	catch (const ValidException& ex)
	{
		cout << ex <<"\n";
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
	vector <Activitate> copie;
	cout << "Introduceti descrierea: ";
	cin >> desc;
	serv.filtraredescriere(desc, copie);
	tipareste(copie);
}

void Console::filtraretip()
{
	string tip;
	vector <Activitate> copie;
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

void Console::adaugaInLista()
{
	string titlu;
	cout << "Introduceti titlul: ";
	cin >> titlu;
	try
	{
		serv.adaugaInLista(titlu);
		cout << "Adaugat cu succes.\n";
	}
	catch (const RepoException& ex)
	{
		cout << ex <<"\n";
		cout << "Adaugare esuata.\n";
	}
	cout << "Numar al activitatilor din lista: " << serv.getLista().size() << "\n";
}

void Console::tiparesteLista()
{
	cout << "Lista actuala de activitati:\n";
	for (auto& i: serv.getLista())
	{
		cout << serv.cautaActivitate(i).getTitlu() << " " << serv.cautaActivitate(i).getDescriere() << " " << serv.cautaActivitate(i).getTip() << " " << serv.cautaActivitate(i).getDurata() << "\n";
	}
	cout << "Numar al activitatilor din lista: " << serv.getLista().size() << "\n";
}

void Console::genereazaLista()
{
	int numar;
	cout << "Introduceti numarul de elemente din lista generata aleator: ";
	cin >> numar;
	try
	{
		serv.genereazaLista(numar);
		cout << "Lista generata cu succes.\n";
	}
	catch (const RepoException& ex)
	{
		cout << ex << "\n";
		cout << "Lista nu a fost generata cu succes.\n";
	}
	cout << "Numar al activitatilor din lista: " << serv.getLista().size() << "\n";
}

void Console::golesteLista()
{
	serv.golesteLista();
	cout << "Lista golita cu succes.\n";
	cout << "Numar al activitatilor din lista: " << serv.getLista().size() << "\n";
}

void Console::tiparesteMap()
{
	map <string, int> contor;
	contor = serv.getMap();
	map <string, int> ::iterator it;
	for (it = contor.begin(); it != contor.end(); it++)
	{
		cout << it->first << " " << it->second << '\n';
	}
}

void Console::exportare()
{
	string tip, numeFisier;
	cout << "Introduceti numele fisierului: ";
	cin >> numeFisier;
	numeFisier = numeFisier + ".csv";
	std::ofstream f(numeFisier);
	f << "Lista actuala de activitati este:\n";
	for (auto& i : serv.getLista())
	{
		f << serv.cautaActivitate(i).getTitlu() << ", " << serv.cautaActivitate(i).getDescriere() << ", " << serv.cautaActivitate(i).getTip() << ", " << serv.cautaActivitate(i).getDurata() << "\n";
	}
	cout << "Numar al activitatilor din lista: " << serv.getLista().size() << "\n";
	f.close();
}

void Console::consoleUndo()
{
	serv.serviceUndo();
}

void Console::start()
{
	while (true)
	{
		cout << "\n\n-----------------------------------------------------------\n";
		cout << "Meniu: \n";
		cout << "1. Adauga activitate.\n";
		cout << "2. Tipareste activitatile.\n";
		cout << "3. Cauta activitate.\n";
		cout << "4. Sterge activitate.\n";
		cout << "5. Modifica activitate.\n";
		cout << "6. Filtreaza activitatile dupa descriere.\n";
		cout << "7. Filtreaza activitatile dupa tip.\n";
		cout << "8. Sortare.\n";
		cout << "-----------------------------------------------------------\n";
		cout << "9. Afiseaza lista actuala de activitati.\n";
		cout << "10. Adauga activitate in lista actuala.\n";
		cout << "11. Genereaza o lista actuala random de activitati.\n";
		cout << "12. Goleste lista actuala de activitati.\n";
		cout << "13. Export la lista actuala de activitati.\n";
		cout << "-----------------------------------------------------------\n";
		cout << "14. Afiseaza contorul.\n";
		cout << "15. Undo.\n";
		cout << "-----------------------------------------------------------\n";
		cout << "20. Iesire.\n";
		cout << "-----------------------------------------------------------\n";
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
			case 9:
				tiparesteLista();
				break;
			case 10:
				adaugaInLista();
				break;
			case 11:
				genereazaLista();
				break;
			case 12:
				golesteLista();
				break;
			case 13:
				exportare();
				break;
			case 14:
				tiparesteMap();
				break;
			case 15:
				consoleUndo();
				break;
			case 20:
				return;
			default:
				cout << "Comanda invalida.\n";
			}
		}
		catch (const RepoException& ex) {
			cout << ex << '\n';
		}
		catch (const ValidException& ex1) {
			cout << ex1 << '\n';
		}
	}
}