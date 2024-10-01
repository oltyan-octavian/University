#include "GUI.h"
#include <random>
#include <fstream>
#include <qdebug.h>

MyTableModel::MyTableModel(QObject* parent, Service& serv) : QAbstractTableModel(parent), serv { serv } {}

int MyTableModel::rowCount(const QModelIndex& /*parent*/) const 
{
	return 15;
}

int MyTableModel::columnCount(const QModelIndex& /*parent*/) const 
{
	return 4;
}

QVariant MyTableModel::data(const QModelIndex& index, int role) const 
{
	if (role == Qt::DisplayRole) 
	{
		if (index.column() == 1)
			return QString::fromStdString(serv.getAll().at(index.row()).getTitlu());
		if (index.column() == 2)
			return QString::fromStdString(serv.getAll().at(index.row()).getDescriere());
		if (index.column() == 3)
			return QString::fromStdString(serv.getAll().at(index.row()).getTip());
		if (index.column() == 4)
			return QString::number(serv.getAll().at(index.row()).getDurata());
	}
	return QVariant();
}

void Gui::initializare()
{
	setLayout(layoutMain);

	activitati = new QListWidget;
	layoutMain->addWidget(activitati);

	mesaj = new QMessageBox;

	boxTitlu = new QLineEdit;
	boxDescriere = new QLineEdit;
	boxTip = new QLineEdit;
	boxDurata = new QLineEdit;

	QFormLayout* form = new QFormLayout;
	form->addRow("Titlu:", boxTitlu);
	form->addRow("Descriere:", boxDescriere);
	form->addRow("Tip:", boxTip);
	form->addRow("Durata:", boxDurata);

	QVBoxLayout* layoutButoane = new QVBoxLayout;
	layoutButoane->addLayout(form);

	adaugaActivitate = new QPushButton("Adauga activitate");
	modificaActivitate = new QPushButton("Modifica activitate");
	cautaActivitate = new QPushButton("Cauta activitate");
	stergeActivitate = new QPushButton("Sterge activitate");
	undoActiune = new QPushButton("Undo la ultima actiune");
	filtrareDescriere = new QPushButton("Filtrare dupa descriere");
	filtrareTip = new QPushButton("Filtrare dupa tip");
	sortareTitlu = new QPushButton("Sortare activitati dupa titlu");
	sortareDescriere = new QPushButton("Sortare activitati dupa descriere");
	sortareTip = new QPushButton("Sortare activitati dupa tip + durata");
	reloadRepo = new QPushButton("Afisare lista originala de activitati (inainte de filtrare)");
	optiuniListaCRUD = new QPushButton("Optiuni lista CRUDA de activitati");
	deschidereListaReadOnly = new QPushButton("Deschidere lista read-only de activitati");
	adaugaLista = new QPushButton("Adauga in lista");
	genereazaLista = new QPushButton("Genereaza lista aleatoare");
	golesteLista = new QPushButton("Goleste lista de activitati");
	genereazaButoane = new QPushButton("Genereaza butoane");
	exit = new QPushButton("Iesire");
	layoutButoane->addWidget(adaugaActivitate);
	layoutButoane->addWidget(modificaActivitate);
	layoutButoane->addWidget(cautaActivitate);
	layoutButoane->addWidget(stergeActivitate);
	layoutButoane->addWidget(undoActiune);
	layoutButoane->addWidget(filtrareDescriere);
	layoutButoane->addWidget(filtrareTip);
	layoutButoane->addWidget(reloadRepo);
	layoutButoane->addWidget(sortareTitlu);
	layoutButoane->addWidget(sortareDescriere);
	layoutButoane->addWidget(sortareTip);
	layoutButoane->addWidget(deschidereListaReadOnly);
	layoutButoane->addWidget(optiuniListaCRUD);
	layoutButoane->addWidget(adaugaLista);
	layoutButoane->addWidget(genereazaLista);
	layoutButoane->addWidget(golesteLista);
	layoutButoane->addWidget(genereazaButoane);
	layoutButoane->addWidget(exit);

	layoutMain->addLayout(layoutButoane);
}

void Gui::notifyObservers()
{
	for (int i = 0; i < observers.size(); i++)
	{
		observers.at(i)->update(serv.getLista());
	}
}

void Gui::conectareButoane()
{
	QObject::connect(activitati, &QListWidget::itemSelectionChanged, [&]()
		{
			auto activitatiSelectate = activitati->selectedItems();
			if (activitatiSelectate.isEmpty())
			{
				boxTitlu->setText("");
				boxDescriere->setText("");
				boxTip->setText("");
				boxDurata->setText("");
			}
			else
			{
				auto activitateCurenta = activitatiSelectate.at(0);
				Activitate p = serv.cautaActivitate(activitateCurenta->text().toStdString());
				boxTitlu->setText(QString::fromStdString(p.getTitlu()));
				boxDescriere->setText(QString::fromStdString(p.getDescriere()));
				boxTip->setText(QString::fromStdString(p.getTip()));
				boxDurata->setText(QString::number(p.getDurata()));
			}
		});

	QObject::connect(adaugaActivitate, &QPushButton::clicked, [&]()
		{
			QString qtitlu = boxTitlu->text();
			QString qdescriere = boxDescriere->text();
			QString qtip = boxTip->text();
			QString qdurata = boxDurata->text();

			string titlu = qtitlu.toStdString();
			string descriere = qdescriere.toStdString();
			string tip = qtip.toStdString();
			int durata = qdurata.toInt();

			try
			{
				serv.adaugaActivitate(titlu, descriere, tip, durata);
				reload(serv.getAll());
			}
			catch (RepoException& ex)
			{
				exceptie();
			}
			catch (ValidException& ex)
			{
				exceptie();
			}
			boxTitlu->setText("");
			boxDescriere->setText("");
			boxTip->setText("");
			boxDurata->setText("");
		});

	QObject::connect(stergeActivitate, &QPushButton::clicked, [&]()
		{
			QString qtitlu = boxTitlu->text();
			string titlu = qtitlu.toStdString();
			try
			{
				serv.stergeActivitate(titlu);
				reload(serv.getAll());
			}
			catch (RepoException& ex)
			{
				exceptie();
			}
			boxTitlu->setText("");
			boxDescriere->setText("");
			boxTip->setText("");
			boxDurata->setText("");
		});

	QObject::connect(modificaActivitate, &QPushButton::clicked, [&]()
		{
			QString qtitlu = boxTitlu->text();
			QString qdescriere = boxDescriere->text();
			QString qtip = boxTip->text();
			QString qdurata = boxDurata->text();
			string titlu = qtitlu.toStdString();
			string descriere = qdescriere.toStdString();
			string tip = qtip.toStdString();
			int durata = qdurata.toInt();
			try
			{
				serv.modificaActivitate(titlu, descriere, tip, durata);
			}
			catch (RepoException& ex)
			{
				exceptie();
			}
			boxTitlu->setText("");
			boxDescriere->setText("");
			boxTip->setText("");
			boxDurata->setText("");
		});

	QObject::connect(cautaActivitate, &QPushButton::clicked, [&]()
		{
			QString qtitlu = boxTitlu->text();
			string titlu = qtitlu.toStdString();
			try
			{
				Activitate act = serv.cautaActivitate(titlu);
				boxTitlu->setText(QString::fromStdString(act.getTitlu()));
				boxDescriere->setText(QString::fromStdString(act.getDescriere()));
				boxTip->setText(QString::fromStdString(act.getTip()));
				boxDurata->setText(QString::number(act.getDurata()));
			}
			catch (RepoException& ex)
			{
				exceptie();
			}
		});

	QObject::connect(undoActiune, &QPushButton::clicked, [&]()
		{
			try
			{
				serv.serviceUndo();
				reload(serv.getAll());

			}
			catch (RepoException& ex)
			{
				exceptie();
			}
			boxTitlu->setText("");
			boxDescriere->setText("");
			boxTip->setText("");
			boxDurata->setText("");
		});

	QObject::connect(filtrareDescriere, &QPushButton::clicked, [&]()
		{
			QString qdescriere = boxDescriere->text();
			string descriere = qdescriere.toStdString();
			vector <Activitate> copie;
			serv.filtraredescriere(descriere, copie);
			reload(copie);
			boxTitlu->setText("");
			boxDescriere->setText("");
			boxTip->setText("");
			boxDurata->setText("");
		});

	QObject::connect(filtrareTip, &QPushButton::clicked, [&]()
		{
			QString qtip = boxTip->text();
			string tip = qtip.toStdString();
			vector <Activitate> copie;
			serv.filtraretip(tip, copie);
			reload(copie);
			boxTitlu->setText("");
			boxDescriere->setText("");
			boxTip->setText("");
			boxDurata->setText("");
		});

	QObject::connect(sortareTitlu, &QPushButton::clicked, [&]() 
		{
			serv.sortare("titlu");
			reload(serv.getAll());
			boxTitlu->setText("");
			boxDescriere->setText("");
			boxTip->setText("");
			boxDurata->setText("");
		});

	QObject::connect(sortareDescriere, &QPushButton::clicked, [&]()
		{
			serv.sortare("descriere");
			reload(serv.getAll());
			boxTitlu->setText("");
			boxDescriere->setText("");
			boxTip->setText("");
			boxDurata->setText("");
		});

	QObject::connect(sortareTip, &QPushButton::clicked, [&]()
		{
			serv.sortare("tip");
			reload(serv.getAll());
			boxTitlu->setText("");
			boxDescriere->setText("");
			boxTip->setText("");
			boxDurata->setText("");
		});

	QObject::connect(reloadRepo, &QPushButton::clicked, [&]()
		{
			reload(serv.getAll());
			boxTitlu->setText("");
			boxDescriere->setText("");
			boxTip->setText("");
			boxDurata->setText("");
		});

	QObject::connect(deschidereListaReadOnly, &QPushButton::clicked, [&]()
		{
			ListaReadOnlyGui* read = new ListaReadOnlyGui(serv);
			this->addObserver(read);
			read->show();
		});

	QObject::connect(optiuniListaCRUD, &QPushButton::clicked, [&]()
		{
			ListaCRUDGui* lst = new ListaCRUDGui(serv);
			this->addObserver(lst);
			lst->show();
		});

	QObject::connect(adaugaLista, &QPushButton::clicked, [&]()
		{
			QString qtext = boxTitlu->text();
			string text = qtext.toStdString();
			try
			{
				serv.adaugaInLista(text);
				notifyObservers();
			}
			catch (RepoException& ex)
			{
				exceptie();
			}
			boxTitlu->setText("");
		});

	QObject::connect(genereazaLista, &QPushButton::clicked, [&]()
		{
			QString qtext = boxTitlu->text();
			int text = qtext.toInt();
			try
			{
				serv.genereazaLista(text);
				notifyObservers();
			}
			catch (RepoException&)
			{
				exceptie();
			}
			boxTitlu->setText("");
		});

	QObject::connect(golesteLista, &QPushButton::clicked, [&]()
		{
			serv.golesteLista();
			notifyObservers();
			boxTitlu->setText("");
		});

	QObject::connect(genereazaButoane, &QPushButton::clicked, [&]() {

		map<string, int> map = serv.getMap();
		vector<QPushButton*>butoaneGenerate;
		int k = 0;
		for (const auto& p : map)
		{
			QPushButton* btn = new QPushButton(QString::fromStdString(p.first));
			butoaneGenerate.push_back(btn);
			layoutMain->addWidget(butoaneGenerate[k]);
			int a = p.second;
			QObject::connect(butoaneGenerate[k], &QPushButton::clicked, [&, a]() {
				auto aa = QString::number(a);
				mesaj->setText(aa);
				mesaj->show();
				});
			k++;
		}
		});

	QObject::connect(exit, &QPushButton::clicked, [&]()
		{
			this->close();
		});
}

void Gui::reload(vector <Activitate>& lista)
{
	activitati->clear();
	for (const auto& p : lista)
	{
		QListWidgetItem* activ = new QListWidgetItem(QString::fromStdString(p.getTitlu()));
		activitati->addItem(activ);
	}
}

void Gui::exceptie()
{
	QMessageBox::warning(this, "Warning", QString::fromStdString("A aparut o eroare."));
}

void ListaCRUDGui::initializare()
{
	setLayout(layoutMain);

	listaActivitati = new QTableWidget;
	layoutMain->addWidget(listaActivitati);

	textBox = new QLineEdit;

	QFormLayout* form = new QFormLayout;
	form->addRow("Text:", textBox);

	QVBoxLayout* layoutButoane = new QVBoxLayout;
	layoutButoane->addLayout(form);

	adaugaLista = new QPushButton("Adauga in lista");
	genereazaLista = new QPushButton("Genereaza lista aleatoare");
	golesteLista = new QPushButton("Goleste lista de activitati");
	exportLista = new QPushButton("Export lista in fisier .csv");
	exit = new QPushButton("Iesire");

	layoutButoane->addWidget(adaugaLista);
	layoutButoane->addWidget(genereazaLista);
	layoutButoane->addWidget(golesteLista);
	layoutButoane->addWidget(exportLista);
	layoutButoane->addWidget(exit);

	layoutMain->addLayout(layoutButoane);
}

void ListaCRUDGui::conectareButoane()
{
	QObject::connect(adaugaLista, &QPushButton::clicked, [&]()
		{
			QString qtext = textBox->text();
			string text = qtext.toStdString();
			try
			{
				serv.adaugaInLista(text);
				reload(serv.getLista());
			}
			catch (RepoException& ex)
			{
				exceptie();
			}
			textBox->setText("");
		});

	QObject::connect(genereazaLista, &QPushButton::clicked, [&]()
		{
			QString qtext = textBox->text();
			int text = qtext.toInt();
			try
			{
				serv.genereazaLista(text);
				reload(serv.getLista());
			}
			catch (RepoException&)
			{
				exceptie();
			}
			textBox->setText("");
		});

	QObject::connect(golesteLista, &QPushButton::clicked, [&]()
		{
			serv.golesteLista();
			reload(serv.getLista());
			textBox->setText("");
		});

	QObject::connect(exportLista, &QPushButton::clicked, [&]()
		{
			QString qtext = textBox->text();
			string text = qtext.toStdString();
			string numeFisier = text + ".csv";
			std::ofstream f(numeFisier);
			f << "Lista actuala de activitati este:\n";
			for (auto& i : serv.getLista())
			{
				f << serv.cautaActivitate(i).getTitlu() << ", " << serv.cautaActivitate(i).getDescriere() << ", " << serv.cautaActivitate(i).getTip() << ", " << serv.cautaActivitate(i).getDurata() << "\n";
			}
			f.close();
			textBox->setText("");
		});

	QObject::connect(exit, &QPushButton::clicked, [&]()
		{
			this->close();
		});
}

void ListaCRUDGui::reload(vector <string> lista)
{
	QStringList titlu;
	titlu.append(QString::fromStdString("Titlu:"));
	titlu.append(QString::fromStdString("Descriere:"));
	titlu.append(QString::fromStdString("Tip:"));
	titlu.append(QString::fromStdString("Durata:"));
	listaActivitati->setHorizontalHeaderLabels(titlu);
	for (int i = 0; i < lista.size(); i++)
	{
		QTableWidgetItem* titlu = new QTableWidgetItem(QString::fromStdString(lista.at(i)));
		listaActivitati->setItem(i, 0, titlu);
		QTableWidgetItem* descriere = new QTableWidgetItem(QString::fromStdString(serv.cautaActivitate(lista.at(i)).getDescriere()));
		listaActivitati->setItem(i, 1, descriere);
		QTableWidgetItem* tip = new QTableWidgetItem(QString::fromStdString(serv.cautaActivitate(lista.at(i)).getTip()));
		listaActivitati->setItem(i, 2, tip);
		int dur = serv.cautaActivitate(lista.at(i)).getDurata();
		QTableWidgetItem* durata = new QTableWidgetItem(QString::number(dur));
		listaActivitati->setItem(i, 3, durata);
	}
}

void ListaCRUDGui::exceptie()
{
	QMessageBox::warning(this, "Warning", QString::fromStdString("A aparut o eroare."));
}

void ListaReadOnlyGui::paintEvent(QPaintEvent* ev) {
	QPainter p{ this };
	for (int i = 0; i < lst.size(); i++)
	{
		std::mt19937 mt{ std::random_device{}() };
		std::uniform_int_distribution<> dist(0, 9);
		int rndNr = dist(mt);
		std::uniform_int_distribution<> dist2(0, 400);
		int len = dist2(mt);
		std::uniform_int_distribution<> dist3(0, 200);
		int inal = dist3(mt);
		QString nume;
		nume.append("poze/");
		nume.append(QString::number(rndNr));
		nume.append(".jpg");
		p.drawImage(len, inal, QImage(nume));
	}
}

void ListaReadOnlyGui::reload(vector <string> lista)
{
	lst = lista;
	this->repaint();
}