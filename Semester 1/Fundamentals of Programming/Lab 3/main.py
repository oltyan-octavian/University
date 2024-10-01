def print_menu():
    print("Introduceti comanda dorita: ")
    print("1 - Citirea unei liste de numere intregi")
    print("2 - Afisarea secventei de lungime maxima in care oricare doua elemente consecutive sunt relativ prime intre ele")
    print("3 - Afisarea secventei de lungime maxima in care oricare doua elemente consecutive au cel putin 2 cifre distincte comune")
    print("4 - Afisarea secventei care are suma maxima")
    print("5 - Iesire")


def citire(crt_list: list):
    line = input("Introduceti lista cu numere intregi: ")
    nr = line.split()
    int_list = list(map(int, nr))
    return int_list


def cmmdc(a: int, b: int):
    a = abs(a)
    b = abs(b)
    while b > 0:
        r = a % b
        a = b
        b = r
    if a == 1:
        return True
    else:
        return False


def cifre(a: int, b: int):
    a = abs(a)
    b = abs(b)
    if a < 10 or b < 10:
        return False
    c1 = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
    c2 = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
    c = 0
    a = abs(a)
    b = abs(b)
    while a > 0:
        cif = int(a % 10)
        c1[cif] = 1
        a = int(a / 10)
    while b > 0:
        cif = int(b % 10)
        c2[cif] = 1
        b = int(b / 10)
    for i in range(0, 9):
        if c1[i] != 0 and c2[i] != 0:
            c = c + 1
    if c >= 2:
        return True
    else:
        return False


def run():
    crt_list = []
    print_menu()
    while True:
        opt = input("Optiunea aleasa este: ")
        if opt == '1':
            crt_list = citire(crt_list)
        elif opt == '2':
            lm = 1
            si = 0
            l = 1
            n = len(crt_list)
            for i in range(0, n - 1):
                if cmmdc(crt_list[i], crt_list[i + 1]):
                    l = l + 1
                elif l > lm:
                    lm = l
                    si = i - l + 1
                    l = 1
                else:
                    l = 1
            if l > lm:
                lm = l
                si = n - l
            if lm == 1:
                print("Nu exista")
            else:
                for j in range(si, si + lm):
                    print(crt_list[j])
        elif opt == '3':
            lm = 1
            si = 0
            l = 1
            n = len(crt_list)
            for i in range(0, n - 1):
                if cifre(crt_list[i], crt_list[i + 1]):
                    l = l + 1
                elif l > lm:
                    lm = l
                    si = i - l + 1
                    l = 1
                else:
                    l = 1
            if l > lm:
                lm = l
                si = n - l
            if lm == 1:
                print("Nu exista")
            else:
                for j in range(si, si + lm):
                    print(crt_list[j])
        elif opt == '4':
            smax = crt_list[0]
            n = len(crt_list)
            maxs = 0
            maxf = 0
            for i in range(1, n):
                if crt_list[i] > smax:
                    smax = crt_list[i]
                    maxs = i
            lmax = 1
            for i in range(0, n-1):
                sact = crt_list[i]
                lact = 1
                if sact > smax:
                    maxs = i
                    smax = sact
                    lmax = 1
                for j in range(i+1, n):
                    sact = sact + crt_list[j]
                    lact = lact + 1
                    if sact > smax:
                        maxs = i
                        maxf = j
                        smax = sact
                        lmax = lact
                    elif sact == smax and lact > lmax:
                        maxs = i
                        maxf = j
                        smax = sact
                        lmax = lact
            if lmax == 1:
                print(crt_list[maxs])
            else:
                for k in range(maxs, maxf + 1):
                    print(crt_list[k])
        elif opt == '5':
            return
        else:
            print("Optiunea aleasa nu exista.")


run()
