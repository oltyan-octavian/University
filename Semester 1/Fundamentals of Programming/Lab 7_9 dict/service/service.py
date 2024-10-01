from domain.entities import student
from repository.stud_repo import StudentRepository
from domain.validators import StudentValidator
from domain.entities import problema
from repository.prob_repo import ProblemRepository
from domain.validators import ProblemaValidator


class StudentService:
    """
        GRASP Controller (Curs 6)
        Responsabil de efectuarea operatiilor cerute de utilizator
        Coordoneaza operatiile necesare pentru a realiza actiunea declansata de utilizator
        (i.e. declansare actiune: utilizator -> ui-> obiect tip service in ui -> service -> service coordoneaza operatiile
        folosind alte obiecte (e.g. repo, validator) pentru a realiza efectiv operatia)
        """
    def __init__(self, repo, validator):
        """
        Initializeaza service
        :param repo: obiect de tip repo care ne ajuta sa gestionam multimea de seriale
        :type repo: StudentRepository
        :param validator: validator pentru verificarea serialelor
        :type validator: ShowValidator
        """
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

    def get_all_students(self):
        """
        Returneaza o lista cu toate serialele disponibile
        :return: lista de seriale disponibile
        :rtype: list of objects de tip Serial
        """
        return self.__repo.get_all_students()


class ProblemaService:
    """
        GRASP Controller (Curs 6)
        Responsabil de efectuarea operatiilor cerute de utilizator
        Coordoneaza operatiile necesare pentru a realiza actiunea declansata de utilizator
        (i.e. declansare actiune: utilizator -> ui-> obiect tip service in ui -> service -> service coordoneaza operatiile
        folosind alte obiecte (e.g. repo, validator) pentru a realiza efectiv operatia)
        """
    def __init__(self, prepo, validator):
        """
        Initializeaza service

        :param validator: validator pentru verificarea serialelor
        :type validator: ShowValidator
        """
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

    def get_all_problems(self):
        """
        Returneaza o lista cu toate serialele disponibile
        :return: lista de seriale disponibile
        :rtype: list of objects de tip Serial
        """
        return self.__prepo.get_all_problems()


def test_add_student():
    srepo = StudentRepository()
    svalidator = StudentValidator()
    test_srv = StudentService(srepo, svalidator)

    added_student = test_srv.add_student(1234, 'Ionut Popescu', 215)
    assert (added_student.getId()==1234)
    assert (added_student.getNume()=='Ionut Popescu')

    assert (len(test_srv.get_all_students()) == 1)

    try:
        added_student = test_srv.add_student(1234, "", 215)
        assert False
    except ValueError:
        assert True


def test_add_problems():
    prepo = ProblemRepository()
    pvalidator = ProblemaValidator()
    test_srv = ProblemaService(prepo, pvalidator)

    added_problem = test_srv.add_problems(1234, 150, 'Problema cu matrice', '20/10/2022')
    assert (added_problem.getNrlab()==1234)
    assert (added_problem.getNrprob()==150)

    assert (len(test_srv.get_all_problems()) == 1)

    try:
        added_problem = test_srv.add_problems(1234, 150, "", '')
        assert False
    except ValueError:
        assert True