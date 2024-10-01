import unittest

from domain.validators import ProblemaValidator
from repository.prob_file_repo import ProblemFileRepository
from repository.prob_repo import ProblemRepository
from service.service import ProblemaService


class TestCaseProbService(unittest.TestCase):
    def setUp(self) -> None:
        filerepo = ProblemFileRepository()
        repo = ProblemRepository()
        validator = ProblemaValidator()
        self.__srv = ProblemaService(filerepo, repo, validator)

    def test_add_prob(self):

        added_prob = self.__srv.add_problems(1, 1, "Problema cu matrice", "02/02/2020")
        self.assertTrue(added_prob.getNrlab() == 1)
        self.assertTrue(added_prob.getDescriere() == "Problema cu matrice")

        self.assertEqual(len(self.__srv.get_all_problems()), 1)

    def test_delete_prob(self):

        self.__srv.add_problems(1, 1, "Problema cu matrice", "02/02/2020")
        self.__srv.del_prob(1, 1)
        self.assertEqual(len(self.__srv.get_all_problems()), 0)

    def test_get_all_students(self):

        self.__srv.add_problems(1, 1, "Problema cu matrice", "02/02/2020")
        self.__srv.add_problems(1, 2, "Problema cu triunghiuri", "02/02/2022")
        self.assertIsInstance(self.__srv.get_all_problems(), list)
        self.assertEqual(len(self.__srv.get_all_problems()), 2)

