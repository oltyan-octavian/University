#include <iostream>

using namespace std;

int divide(int& n, int d)
{
    /*
    Primeste parametrii n si d si verifica de cate ori se imparte n la d, returnand acel numar.
    :param &n: numar natural
    :param d: numar natural
    :return: numarul de impartiri reusite, numar natural
    */
    int p;
    p = 0;
    while (n % d == 0)
    {
        p++;
        n = n / d;
    }
    return p;
}

int main()
{
    int optiune;
    bool ok = true;
    while (ok == true)
    {
        cout << "Alegeti o optiune: \n";
        cout << "1. Descompunere in factori primi. \n";
        cout << "2. Iesire. \n";
        cout << "Alege: ";
        cin >> optiune;
        if (optiune == 1)
        {
            int x, d, p, ok;
            d = 2;
            cout << "x = ";
            cin >> x;
            ok = 0;
            while (x > 1)
            {
                p = divide(x, d);
                if (p > 0)
                    if (ok == 0)
                        cout << d << "^" << p;
                    else
                        cout << "+" << d << "^" << p;
                if (p > 0)
                    ok = 1;
                d++;
            }
            cout << '\n';
            cout << '\n';
        }
        else
            if (optiune == 2)
                ok = false;
            else
                cout << "Optiunea aleasa nu exista.\n";
    }
    return 0;
}