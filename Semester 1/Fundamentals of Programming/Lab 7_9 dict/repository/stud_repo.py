class StudentRepository:
    """
        Clasa creata cu responsabilitatea de a gestiona
        multimea de studenti (i.e. sa ofere un depozit persistent pentru obiecte
        de tip student)
    """
    def __init__(self):
        self.__students = {}

    def store(self, stud):
        """
        Adauga un student in lista
        :return: -; lista de studenti se modifica prin adaugarea studentului dat
        :rtype:
        """
        id = str(stud.getId())
        self.__students[id] = stud

    def get_all_students(self):
        """
        Returneaza o lista cu toti studentii existenti
        :rtype: list of objects de tip student
        """
        return self.__students

