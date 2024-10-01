class ProblemRepository:
    """
        Clasa creata cu responsabilitatea de a gestiona
        multimea de probleme (i.e. sa ofere un depozit persistent pentru obiecte
        de tip problema)
    """
    def __init__(self):
        self.__probleme = []

    def store(self, prob):
        """
        Adauga o problema in lista
        :return: -; lista de probleme se modifica prin adaugarea problemei date
        :rtype:
        """
        self.__probleme.append(prob)

    def get_all_problems(self):
        """
        Returneaza o lista cu toate problemele existente
        :rtype: list of objects de tip problema
        """
        return self.__probleme

