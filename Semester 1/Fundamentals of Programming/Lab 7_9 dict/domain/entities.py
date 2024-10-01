class student:
    def __init__(self, id, nume, grup):
        """
        Creeaza un nou student alaturi de id-ul lui, numele si grupul.
        :param id: id-ul studentului
        :type id: int
        :param nume: numele studentului
        :type nume: str
        :param grup: grupul din care face parte studentul
        :type grup: int (>0)
        """
        self.__id = id
        self.__nume = nume
        self.__grup = grup

    def getId(self):
        return self.__id

    def getNume(self):
        return self.__nume

    def getGrup(self):
        return self.__grup

    def setId(self, value):
        self.__id = value

    def setNume(self, value):
        self.__nume = value

    def setGrup(self, value):
        self.__grup = value

    def __eq__(self, other):
        """
        Verifica egalitatea intre studentul curent si studentul other
        :param other: celalalt student
        :type other: Student
        :return: True daca studentii sunt egali (=au acelasi id si acelasi nume), False altfel
        :rtype: bool
        """
        if self.__id == other.getId() and self.__nume == other.getNume():
            return True
        return False

    def __str__(self):
        return "Id student: " + self.__id + '; Nume student: ' + str(self.__nume) + '; Grup student: ' + self.__grup


class problema:
    def __init__(self, nrlab, nrprob, descriere, deadline):
        """
        Creeaza o noua problema alaturi de numarul laboratorului, numarul problemei, descriere si deadline.

        """
        self.__nrlab = nrlab
        self.__nrprob = nrprob
        self.__descriere = descriere
        self.__deadline = deadline

    def getNrlab(self):
        return self.__nrlab

    def getNrprob(self):
        return self.__nrprob

    def getDescriere(self):
        return self.__descriere

    def getDeadline(self):
        return self.__deadline

    def setNrlab(self, value):
        self.__nrlab = value

    def setNrprob(self, value):
        self.__nrprob = value

    def setDescriere(self, value):
        self.__descriere = value

    def setDeadline(self, value):
        self.__deadline = value

    def __eq__(self, other):
        """
        Verifica egalitatea intre problema curenta si problema other
        :param other: problema cealalta
        :type other: problema
        :return: True daca problemele sunt egale (=au acelasi numar), False altfel
        :rtype: bool
        """
        if self.__nrlab == other.getNrlab() and self.__nrprob == other.getNrprob():
            return True
        return False

    def __str__(self):
        return "Numar laborator: " + self.__nrlab + "Numar problema: " + self.__nrprob + '; Descriere problema: ' + self.__descriere + '; Deadline problema: ' + self.__deadline
