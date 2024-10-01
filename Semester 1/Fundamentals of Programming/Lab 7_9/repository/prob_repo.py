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

    def search_prob(self, nrlab, nrprob):
        for i in self.__probleme:
            if i.getNrprob() == nrprob and i.getNrlab() == nrlab:
                return i

    def delete_problem(self, prob):
        self.__probleme.remove(prob)

    def delete_all(self):
        self.__probleme.clear()

    def get_all_problems(self):
        """
        Returneaza o lista cu toate problemele existente
        :rtype: list of objects de tip problema
        """
        return self.__probleme

