#include <QtWidgets/QApplication>
#include "gui.h"

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    Repository repo;
    Validator valid;
    Lista lista;
    Service serv(repo, valid, lista);
    Gui gui(serv);
    gui.show();
    return a.exec();
}
