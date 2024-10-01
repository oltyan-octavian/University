from domain.entities import student
from domain.entities import problema
from domain.entities import linking

import random
import string


class StudentService:
    """
        GRASP Controller (Curs 6)
        Responsabil de efectuarea operatiilor cerute de utilizator
        Coordoneaza operatiile necesare pentru a realiza actiunea declansata de utilizator
        (i.e. declansare actiune: utilizator -> ui-> obiect tip service in ui -> service -> service coordoneaza operatiile
        folosind alte obiecte (e.g. repo, validator) pentru a realiza efectiv operatia)
        """
    def __init__(self, filerepo, repo, validator):
        """
        Initializeaza service
        :param repo: obiect de tip repo care ne ajuta sa gestionam multimea de seriale
        :type repo: StudentRepository
        :param validator: validator pentru verificarea serialelor
        :type validator: ShowValidator
        """
        self.__filerepo = filerepo
        self.__repo = repo
        self.__validator = validator

    def add_student(self, id, nume, grupa):
        """
        Adauga serial

        """
        s = student(id, nume, grupa)
        self.__validator.validate(s)
        self.__repo.store(s)
        return s

    def add_student_file(self, id, nume, grupa):
        """
        Adauga serial

        """
        s = student(id, nume, grupa)
        self.__validator.validate(s)
        self.__filerepo.store(s)
        return s

    def search_student(self, idstud):
        return self.__repo.search_stud(idstud)

    def del_stud(self, idstud):
        stud_list = self.__repo.get_all_students()
        for i in stud_list:
            if i.getId() == idstud:
                self.__repo.delete_student(i)

    def del_stud_file(self, idstud):
        stud_list = self.__filerepo.get_list()
        print(stud_list)
        for i in stud_list:
            if i.getId() == idstud:
                self.__filerepo.delete_student(i)

    def get_stud_name(self, idstud):
        return self.__repo.get_stud_name(idstud)

    def get_all_students(self):
        """
        Returneaza o lista cu toate serialele disponibile
        :return: lista de seriale disponibile
        :rtype: list of objects de tip Serial
        """
        return self.__repo.get_all_students()

    def get_all_students_file(self):
        """
        Returneaza o lista cu toate serialele disponibile
        :return: lista de seriale disponibile
        :rtype: list of objects de tip Serial
        """
        return self.__filerepo.get_list()

    def get_random_stud_name(self, length):
        letters = string.ascii_lowercase
        result_str = ''.join(random.choice(letters) for i in range(length))
        return result_str

    def add_random_student(self, lungnume):
        stud_id = random.randint(1, 10000)
        stud_name = self.get_random_stud_name(lungnume)
        stud_grup = random.randint(1, 10000)
        s = student(stud_id, stud_name, stud_grup)
        self.__validator.validate(s)
        self.__repo.store(s)
        return s

    def get_random_number(self):
        return random.randint(1, 10000)


class ProblemaService:
    """
        GRASP Controller (Curs 6)
        Responsabil de efectuarea operatiilor cerute de utilizator
        Coordoneaza operatiile necesare pentru a realiza actiunea declansata de utilizator
        (i.e. declansare actiune: utilizator -> ui-> obiect tip service in ui -> service -> service coordoneaza operatiile
        folosind alte obiecte (e.g. repo, validator) pentru a realiza efectiv operatia)
        """
    def __init__(self, filerepo, prepo, validator):
        """
        Initializeaza service

        :param validator: validator pentru verificarea serialelor
        :type validator: ShowValidator
        """
        self.__filerepo = filerepo
        self.__prepo = prepo
        self.__validator = validator

    def add_problems(self, nrlab, nrprob, descriere, deadline):
        """
        Adauga serial
        :return: obiectul de tip Serial creat
        :rtype:-; serialul s-a adaugat in lista
        :raises: ValueError daca serialul are date invalide
        """
        s = problema(nrlab, nrprob, descriere, deadline)

        self.__validator.validate(s)
        self.__prepo.store(s)
        return s

    def add_problems_file(self, nrlab, nrprob, descriere, deadline):
        """
        Adauga serial
        :return: obiectul de tip Serial creat
        :rtype:-; serialul s-a adaugat in lista
        :raises: ValueError daca serialul are date invalide
        """
        s = problema(nrlab, nrprob, descriere, deadline)

        self.__validator.validate(s)
        self.__filerepo.store(s)
        return s

    def del_prob(self, nrlab, nrprob):
        prob_list = self.__prepo.get_all_problems()
        for i in prob_list:
            if i.getNrlab() == nrlab and i.getNrprob() == nrprob:
                self.__prepo.delete_problem(i)

    def del_prob_file(self, nrlab, nrprob):
        prob_list = self.__filerepo.get_list()
        for i in prob_list:
            if i.getNrlab() == nrlab and i.getNrprob() == nrprob:
                self.__filerepo.delete_problem(i)

    def search_problem(self, nrlab, nrprob):
        return self.__prepo.search_prob(nrlab, nrprob)

    def get_all_problems(self):
        """
        Returneaza o lista cu toate serialele disponibile
        :return: lista de seriale disponibile
        :rtype: list of objects de tip Serial
        """
        return self.__prepo.get_all_problems()

    def get_all_problems_file(self):
        """
        Returneaza o lista cu toate serialele disponibile
        :return: lista de seriale disponibile
        :rtype: list of objects de tip Serial
        """
        return self.__filerepo.get_list()


class LinkingService:
    """
        GRASP Controller (Curs 6)
        Responsabil de efectuarea operatiilor cerute de utilizator
        Coordoneaza operatiile necesare pentru a realiza actiunea declansata de utilizator
        (i.e. declansare actiune: utilizator -> ui-> obiect tip service in ui -> service -> service coordoneaza operatiile
        folosind alte obiecte (e.g. repo, validator) pentru a realiza efectiv operatia)
        """
    def __init__(self, lrepo, validator, srepo):
        """
        Initializeaza service

        :param validator: validator pentru verificarea serialelor
        :type validator: ShowValidator
        """
        self.__lrepo = lrepo
        self.__validator = validator
        self.__srepo = srepo

    def add_links(self, nrlab, nrprob, idstud):
        """
        Adauga serial
        :return: obiectul de tip Serial creat
        :rtype:-; serialul s-a adaugat in lista
        :raises: ValueError daca serialul are date invalide
        """
        s = linking(nrlab, nrprob, idstud, 1)

        self.__validator.validate(s)
        self.__lrepo.store(s)
        return s

    def search_link(self, nrlab, nrprob, idstud):
        return self.__lrepo.search_link(nrlab, nrprob, idstud)

    def problem_list(self, nrlab, nrprob):
        link_list = self.__lrepo.get_all_links()
        final_list = []
        for i in link_list:
            if i.getNrlab() == nrlab and i.getNrprob() == nrprob:
                final_list.append(i)
        return final_list

    def student_list(self):
        link_list = self.__lrepo.get_all_links()
        final_list = []
        for i in link_list:
            ok = 0
            for j in final_list:
                if i.getIdstud() == j[0]:
                    j.append(i.getNota())
                    ok = 1
            if ok == 0:
                mini_list = [i.getIdstud(), i.getNota()]
                final_list.append(mini_list)
        return final_list

    def compare(self, x, y):
        if x.getNota() > y.getNota():
            return True
        elif x.getNota() == y.getNota():
            if x.getIdstud() > y.getIdstud():
                return True
        return False

    def GnomeSort(self, lst, reverse):
        pos = 0
        if reverse == False:
            while pos < len(lst):
                if pos == 0 or self.compare(lst[pos], lst[pos - 1]) == True:
                    pos = pos + 1
                else:
                    lst[pos], lst[pos - 1] = lst[pos - 1], lst[pos]
                    pos = pos - 1
        else:
            while pos < len(lst):
                if pos == 0 or self.compare(lst[pos], lst[pos - 1]) == False:
                    pos = pos + 1
                else:
                    lst[pos], lst[pos - 1] = lst[pos - 1], lst[pos]
                    pos = pos - 1

    def partition(self, lst, low, high, reverse):
        if reverse == False:
            pivot = lst[high]
            i = low - 1
            for j in range(low, high):
                if not self.compare(lst[j], pivot):
                    i = i + 1
                    lst[i], lst[j] = lst[j], lst[i]
            lst[i+1], lst[high] = lst[high], lst[i+1]
            return i + 1
        else:
            pivot = lst[high]
            i = low - 1
            for j in range(low, high):
                if self.compare(lst[j], pivot):
                    i = i + 1
                    lst[i], lst[j] = lst[j], lst[i]
            lst[i + 1], lst[high] = lst[high], lst[i + 1]
            return i + 1

    def QuickSort(self, lst, low, high, reverse):
        if low < high:
            pi = self.partition(lst, low, high, reverse)
            self.QuickSort(lst, low, pi-1, reverse)
            self.QuickSort(lst, pi+1, high, reverse)

    def BubbleSort(self, lst, reverse):
        #best case complexity - O(n)
        #worst case complexity - O(n^2)
        #average case complexity - O(n^2)
        n = len(lst)
        if not reverse:
            for i in range(n):
                for j in range(0, n-i-1):
                    if self.compare(lst[j], lst[j + 1]):
                        lst[j], lst[j+1] = lst[j+1], lst[j]
        else:
            for i in range(n):
                for j in range(0, n-i-1):
                    if not self.compare(lst[j], lst[j + 1]):
                        lst[j], lst[j + 1] = lst[j + 1], lst[j]

    def gnome_sort_by_grade(self, nrlab, nrprob, reverse):
        problem_list = self.problem_list(nrlab, nrprob)
        self.GnomeSort(problem_list, reverse)
        return problem_list

    def quick_sort_by_grade(self, nrlab, nrprob, reverse):
        problem_list = self.problem_list(nrlab, nrprob)
        self.QuickSort(problem_list,0, len(problem_list)-1, reverse)
        return problem_list

    def bubble_sort_by_grade(self, nrlab, nrprob, reverse):
        problem_list = self.problem_list(nrlab, nrprob)
        self.BubbleSort(problem_list, reverse)
        return problem_list

    def sort_by_name(self, nrlab, nrprob):
        problem_list = self.problem_list(nrlab, nrprob)
        for i in range(0, len(problem_list) - 1):
            for j in range(i+1, len(problem_list)):
                if self.__srepo.get_stud_name(problem_list[i].getIdstud()) > self.__srepo.get_stud_name(problem_list[j].getIdstud()):
                    problem_list[i], problem_list[j] = problem_list[j], problem_list[i]
        return problem_list

    def final_student_list(self):
        stud_list = self.student_list()
        final_list = []
        for stud in stud_list:
            grade = 0
            for j in range(1, len(stud)):
                grade = grade + stud[j]
            avg = float(grade/(len(stud)-1))
            nume = self.__srepo.get_stud_name(stud[0])
            mini_list = [nume, avg]
            final_list.append(mini_list)
        return final_list

    def del_link(self, nrlab, nrprob, idstud):
        link_list = self.__lrepo.get_all_links()
        for i in link_list:
            if i.getNrlab() == nrlab and i.getNrprob() == nrprob and i.getIdstud() == idstud:
                self.__lrepo.delete_link(i)

    def notare_link(self, nrlab, nrprob, idstud, nota):
        self.__lrepo.notare_link(nrlab, nrprob, idstud, nota)

    def get_all_links(self):
        """
        Returneaza o lista cu toate serialele disponibile
        :return: lista de seriale disponibile
        :rtype: list of objects de tip Serial
        """
        return self.__lrepo.get_all_links()
