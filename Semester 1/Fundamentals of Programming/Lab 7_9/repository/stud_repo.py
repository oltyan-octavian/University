class StudentRepository:
    """
        Clasa creata cu responsabilitatea de a gestiona
        multimea de studenti (i.e. sa ofere un depozit persistent pentru obiecte
        de tip student)
    """
    def __init__(self):
        self.__students = []

    def store(self, stud):
        """
        Adauga un student in lista

        :return: -; lista de studenti se modifica prin adaugarea studentului dat
        :rtype:
        """
        self.__students.append(stud)

    def search_stud(self, idstud):
        for i in self.__students:
            if i.getId() == idstud:
                return i

    def get_stud_name(self, idstud):
        for i in self.__students:
            if i.getId() == idstud:
                return i.getNume()

    def delete_student(self, stud):
        self.__students.remove(stud)

    def delete_all(self):
        self.__students.clear()

    def get_all_students(self):
        """
        Returneaza o lista cu toti studentii existenti
        :rtype: list of objects de tip student
        """
        return self.__students
