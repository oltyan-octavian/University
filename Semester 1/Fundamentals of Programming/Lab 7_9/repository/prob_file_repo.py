from domain.entities import problema


class ProblemFileRepository:
    """
        Clasa creata cu responsabilitatea de a gestiona
        multimea de studenti (i.e. sa ofere un depozit persistent pentru obiecte
        de tip student)
    """
    def __init__(self):
        self.__nume_fisier = "repository/ProblemFileRepo.txt"

    def __load_every_line(self):
        try:
            f = open(self.__nume_fisier, "r")
        except IOError:
            return []
        nrlab = f.readline().strip()
        nrprob = f.readline().strip()
        descriere = f.readline().strip()
        deadline = f.readline().strip()
        rez = []
        while deadline != "":
            p = problema(nrlab, nrprob, descriere, deadline)
            rez.append(p)
            nrlab = f.readline().strip()
            nrprob = f.readline().strip()
            descriere = f.readline().strip()
            deadline = f.readline().strip()
        f.close()
        return rez

    def store(self, prob):
        lista = self.__load_every_line()
        if prob in lista:
            raise ValueError("Problema se afla deja in lista.")
        lista.append(prob)
        self.__store_every_line(lista)

    def get_list(self):
        lista = self.__load_every_line()
        return lista

    def delete_problem(self, prob):
        lista = self.__load_every_line()
        lista.remove(prob)
        self.__store_every_line(lista)

    def __store_every_line(self, lista):
        try:
            f = open(self.__nume_fisier, "w")
        except IOError:
            return None
        for el in lista:
            f.write(str(el.getNrlab()) + '\n')
            f.write(str(el.getNrprob()) + '\n')
            f.write(str(el.getDescriere()) + '\n')
            f.write(str(el.getDeadline()) + '\n')
        f.close()
