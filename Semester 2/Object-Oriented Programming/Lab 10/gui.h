#pragma once
#include <QtWidgets/qlabel.h>
#include <QtWidgets/qpushbutton.h>
#include <QtWidgets/qboxlayout.h>
#include <QtWidgets/qlineedit.h>
#include <QtWidgets/qformlayout.h>
#include <QtWidgets/qlistwidget.h>
#include <QtWidgets/qmessagebox.h> 
#include <QtWidgets/qtablewidget.h>
#include <QtMultimedia/qaudio.h>
#include <QAudioOutput>
#include <QPainter>
#include <qmediaplayer.h>
#include <qurl.h>
#include <vector>
#include "Service.h"

using std::vector;

class MyTableModel : public QAbstractTableModel{
protected:
	Service& serv;
public:
	MyTableModel(QObject* parent, Service& serv);
	/**
	* number of rows
	*/
	int rowCount(const QModelIndex& parent = QModelIndex()) const override;
	/**
	* number of columns
	*/
	int columnCount(const QModelIndex& parent = QModelIndex()) const override;
	/**
	* Value at a given position
	*/
	QVariant data(const QModelIndex& index, int role = Qt::DisplayRole) const override;
};

class Observer {
public:
	virtual void update(vector <string> lista) = 0;
};

class Gui : public QWidget {
protected:
	Service& serv;
	QHBoxLayout* layoutMain = new QHBoxLayout;
	QListWidget* activitati;
	QMessageBox* mesaj;
	QLineEdit* boxTitlu;
	QLineEdit* boxDescriere;
	QLineEdit* boxTip;
	QLineEdit* boxDurata;
	QPushButton* adaugaActivitate;
	QPushButton* stergeActivitate;
	QPushButton* modificaActivitate;
	QPushButton* undoActiune;
	QPushButton* cautaActivitate;
	QPushButton* filtrareDescriere;
	QPushButton* filtrareTip;
	QPushButton* sortareTitlu;
	QPushButton* sortareDescriere;
	QPushButton* sortareTip;
	QPushButton* reloadRepo;
	QPushButton* optiuniListaCRUD;
	QPushButton* deschidereListaReadOnly;
	QPushButton* golesteLista;
	QPushButton* adaugaLista;
	QPushButton* genereazaLista;
	QPushButton* genereazaButoane;
	QPushButton* exit;

	void initializare();
	void conectareButoane();
	void reload(vector <Activitate>& lista);
	void exceptie();

	vector <Observer*> observers;
	
public:
	Gui(Service& srv) : serv{ srv }
	{
		initializare();
		conectareButoane();
		reload(serv.getAll());
	}
	void addObserver(Observer* obs) {
		observers.push_back(obs);
	}

	void notifyObservers();
};

class ListaCRUDGui : public QWidget, public Observer {
	Service& serv;
	QHBoxLayout* layoutMain = new QHBoxLayout;
	QTableWidget* listaActivitati;
	QLineEdit* textBox;
	QPushButton* golesteLista;
	QPushButton* adaugaLista;
	QPushButton* genereazaLista;
	QPushButton* exportLista;
	QPushButton* exit;

	void initializare();
	void conectareButoane();
	void reload(vector <string> lista);
	void exceptie();
public:
	ListaCRUDGui(Service& srv) : serv{ srv }
	{
		initializare();
		conectareButoane();
		reload(serv.getLista());
	}
	void update(vector <string> lista)
	{
		reload(lista);
	}
};

class ListaReadOnlyGui : public QWidget, public Observer {
	Service& serv;
	vector <string> lst;
	void paintEvent(QPaintEvent* ev) override;
	void reload(vector <string> lista);
public:
	ListaReadOnlyGui(Service& srv) : serv{ srv }
	{
		reload(serv.getLista());
	}
	void update(vector <string> lista)
	{
		reload(lista);
	}
};