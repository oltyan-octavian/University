from domain.entities import student


class StudentFileRepository:
    """
        Clasa creata cu responsabilitatea de a gestiona
        multimea de studenti (i.e. sa ofere un depozit persistent pentru obiecte
        de tip student)
    """
    def __init__(self):
        self.__nume_fisier = "repository/StudentFileRepo.txt"

    def __load_from_file(self):
        try:
            f = open(self.__nume_fisier, "r")
        except IOError:
            return []
        line = f.readline().strip()
        rez = []
        while line != "":
            lista = line.split(",")
            s = student(lista[0], lista[1], lista[2])
            rez.append(s)
            line = f.readline().strip()
        f.close()
        return rez

    def store(self, stud):
        lista = self.__load_from_file()
        if stud in lista:
            raise ValueError("Studentul se afla deja in lista.")
        lista.append(stud)
        self.__store_to_file(lista)

    def get_list(self):
        lista = self.__load_from_file()
        return lista

    def delete_student(self, stud):
        lista = self.__load_from_file()
        lista.remove(stud)
        self.__store_to_file(lista)

    def __store_to_file(self, lista):
        try:
            f = open(self.__nume_fisier, "w")
        except IOError:
            return None
        for el in lista:
            strf = str(el.getId()) + "," + str(el.getNume()) + "," + str(el.getGrup()) + "\n"
            f.write(strf)
        f.close()
