import math


# Functie de quicksort
# arr - lista care trebuie sortata
# return - lista sortata
def quicksort(arr):
    if len(arr) <= 1:
        return arr
    else:
        pivot = arr[0]
        left = [x for x in arr[1:] if x >= pivot]
        right = [x for x in arr[1:] if x < pivot]
        return quicksort(left) + [pivot] + quicksort(right)


# primeste 2 puncte in spatiu, calculeaza distanta euclidiana dintre ele
# nr1 - lista, reprezinta primul punct (coordonatele x si y)
# nr2 - lista, reprezinta al doilea punct (coordonatele x si y)
# return - int, distanta euclidiana dintre numere
# Rezolvare identica cu a ChatGPT-ului, complexitate timp si spatiu O(1)
def exercitiul2(nr1, nr2):
    x = [int(x) for x in nr1.split(',')]
    y = [int(y) for y in nr2.split(',')]
    dist = math.sqrt((x[1] - x[0]) ** 2 + (y[1] - y[0]) ** 2)
    return dist


def exercitiul2_chatgpt(p1, p2):
    x1, y1 = p1
    x2, y2 = p2
    distanta = math.sqrt((x2 - x1) ** 2 + (y2 - y1) ** 2)
    return distanta


# primeste un sir in care se repeta un numar, gaseste elementul care se repeta
# sir - sir de numere
# return - numarul care se repeta
# Rezolvare asemanatoare cu a ChatGPT-ului, acesta a folosit dictionar in loc de vector frecventa.
# Complexitate timp si spatiu O(n)
def exercitiul5(sir):
    x = [int(x) for x in sir.split(',')]
    vect_frecv = [0] * len(x)
    for y in range(0, len(x)):
        vect_frecv[x[y]] = vect_frecv[x[y]] + 1
    for i in range(0, len(x)):
        if vect_frecv[i] == 2:
            return i


def exercitiul5_chatgpt(nums):
    frecventa = {}
    for num in nums:
        if num in frecventa:
            return num
        else:
            frecventa[num] = 1


# primeste un sir de elemente si un numar i, returneaza al i-lea cel mai mare numar din sir
# sir - sir de numere
# i - int, al catelea element descrescator sa returneze
# return - al i-lea cel mai mare numar din sir
# Rezolvare identica cu a ChatGPT-ului, complexitate timp O(n log n) complexitate spatiu O(n)
def exercitiul7(sir, i):
    x = [int(x) for x in sir.split(',')]
    sortat = quicksort(x)
    return sortat[i - 1]


def exercitiul7_chatgpt(nums, k):
    nums_sorted = sorted(nums, reverse=True)  # Sortăm lista în ordine descendentă
    return nums_sorted[k - 1]  # Returnăm elementul de pe poziția k-1


# primeste o matrice, returneaza suma elementelor din submatricea dintre 2 casute
# A - matrice de numere
# nr1 - sir, coltul din stanga sus al submatricii
# nr2 - sir, coltul din dreapta jos al submatricii
# return - int, suma elementelor din submatrice
# Rezolvare identica cu a ChatGPT-ului, complexitate timp O(m*n), complexitate spatiu O(1)
def exercitiul9(A, nr1, nr2):
    x = [int(x) for x in nr1.split(',')]
    y = [int(y) for y in nr2.split(',')]
    suma = 0
    for i in range(x[0], y[0] + 1):
        for j in range(x[1], y[1] + 1):
            suma = suma + A[i][j]
    return suma


def exercitiul9_chatgpt(matrice, perechi):
    rezultate = []
    for pereche in perechi:
        (p, q), (r, s) = pereche
        suma_submatrice = 0
        for i in range(p, r + 1):
            for j in range(q, s + 1):
                suma_submatrice += matrice[i][j]
        rezultate.append(suma_submatrice)
    return rezultate


# primeste o matrice formata din 0 si 1, returneaza linia pe care se afla cei mai multi de 1
# A - matrice de numere, 0 sau 1
# return - int, linia pe care se afla cele mai multe elemente de 1
# Rezolvare identica cu a ChatGPT-ului, complexitate timp O(m*n), complexitate spatiu O(1)
def exercitiul10(A):
    max = 0
    curent = 0
    for i in range(0, len(A)):
        nr1 = 0
        for j in range(0, len(A[i])):
            if A[i][j] == "1":
                nr1 = nr1 + 1
        if nr1 > max:
            max = nr1
            curent = i
    return curent + 1


def exercitiul10_chatgpt(matrice):
    max_linie = -1
    max_nr_1 = 0

    for i, linie in enumerate(matrice):
        nr_1 = sum(linie)
        if nr_1 > max_nr_1:
            max_nr_1 = nr_1
            max_linie = i

    return max_linie


def teste():
    assert(exercitiul2("1,5","4,1") == 5)
    assert(exercitiul5("1,2,3,3,4,5") == 3)
    assert(exercitiul5("1,2,3,4,5,6,7,8,8") == 8)
    assert(exercitiul7("4,2,1,3,6,5", 1) == 6)
    assert(exercitiul7("4,2,1,3,6,5", 4) == 3)
    assert(exercitiul9([[0, 2, 5, 4, 1], [4, 8, 2, 3, 7], [6, 3, 4, 6, 2], [7, 3, 1, 8, 3], [1, 5, 7, 9, 4]], "1,1", "3,3") == 38)
    assert(exercitiul10([["0", "0", "0", "1", "1"], ["0", "1", "1", "1", "1"], ["0", "0", "1", "1", "1"]]) == 2)


# Press the green button in the gutter to run the script.
if __name__ == '__main__':
    teste()
    n = int(input("Introduceti care din exercitii doriti sa le accesati(2, 5, 7, 9, 10): "))
    if n == 2:
        nr1 = input("Introduceti prima locatie (in formatul x,y): ")
        nr2 = input("Introduceti a doua locatie (in formatul x,y): ")
        dist = exercitiul2(nr1, nr2)
        print("Distanta euclidiana dintre cele doua locatii este egala cu", dist)
    if n == 5:
        sir = input("Introduceti un sir de numere in care se repeta unul singur: ")
        i = exercitiul5(sir)
        print("Numarul care se repeta este", i)
    if n == 7:
        sir = input("Introduceti un sir de numere: ")
        i = int(input("Introduceti numarul k: "))
        numar = exercitiul7(sir, i)
        print("Al", i, "-lea cel mai mare numar din sir este", numar)
    if n == 9:
        matrice = []
        x = int(input("Urmeaza sa introduceti o matrice cu m linii si n coloane. Introduceti n: "))
        y = int(input("Introduceti m: "))
        for i in range(0, y):
            linie = input("Introduceti cate o linie a matricei(in stilul 1,1,1,1): ")
            impartit = [int(impartit) for impartit in linie.split(',')]
            matrice.append(impartit)
        nr1 = input("Introduceti prima locatie (in formatul x,y): ")
        nr2 = input("Introduceti a doua locatie (in formatul x,y): ")
        suma = exercitiul9(matrice, nr1, nr2)
        print("Suma dintre cele doua locatii este", suma)
    if n == 10:
        matrice = []
        x = int(input("Urmeaza sa introduceti o matrice cu m linii si n coloane. Introduceti n: "))
        y = int(input("Introduceti m: "))
        for i in range(0, y):
            linie = input("Introduceti cate o linie a matricei(in stilul 1,1,1,1): ")
            impartit = linie.split(",")
            matrice.append(impartit)
        maxim = exercitiul10(matrice)
        print("Cele mai multe elemente 1 se afla pe linia", maxim)



