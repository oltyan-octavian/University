import unittest

from domain.entities import problema
from repository.prob_repo import ProblemRepository


class TestCaseProbRepo(unittest.TestCase):
    def setUp(self) -> None:
        self.__repo = ProblemRepository()
        self.__repo.delete_all()
        self.__populate_list()

    def __populate_list(self):
        prob1 = problema(1, 1, "Problema cu matrice", "02/02/2022")
        prob2 = problema(1, 2, "Problema cu triunghiuri", "05/07/2022")
        prob3 = problema(1, 3, "Problema cu patrate", "01/02/2022")
        prob4 = problema(2, 1, "Problema cu cercuri", "10/12/2022")
        prob5 = problema(2, 5, "Problema cu integrale", "02/02/2023")
        prob6 = problema(2, 2, "Problema cu derivate", "20/02/2023")
        prob7 = problema(3, 6, "Problema cu cuburi", "02/10/2023")
        prob8 = problema(3, 2, "Problema cu conuri", "10/02/2022")
        prob9 = problema(4, 6, "Problema cu cilindri", "12/12/2022")
        prob10 = problema(5, 7, "Problema cu paralelipipede", "25/02/2022")

        self.__repo.store(prob1)
        self.__repo.store(prob2)
        self.__repo.store(prob3)
        self.__repo.store(prob4)
        self.__repo.store(prob5)
        self.__repo.store(prob6)
        self.__repo.store(prob7)
        self.__repo.store(prob8)
        self.__repo.store(prob9)
        self.__repo.store(prob10)

    def test_search_prob(self):
        p = self.__repo.search_prob(1, 3)
        self.assertTrue(p.getDescriere() == "Problema cu patrate")
        self.assertEqual(p.getDeadline(), "01/02/2022")

        p1 = self.__repo.search_prob(12345, 1)
        self.assertIs(p1, None)

    def test_get_all(self):
        initial_size = len(self.__repo.get_all_problems())
        crt_shows = self.__repo.get_all_problems()
        self.assertIsInstance(crt_shows, list)

        self.assertEqual(len(crt_shows), initial_size)

        prob2 = self.__repo.search_prob(1, 2)
        prob5 = self.__repo.search_prob(2, 5)
        self.__repo.delete_problem(prob2)
        self.__repo.delete_problem(prob5)

        crt_shows = self.__repo.get_all_problems()
        self.assertEqual(len(crt_shows), initial_size - 2)

        self.__repo.store(problema(7, 2, "Problema cu piramide", "05/02/2023"))
        self.assertTrue(len(self.__repo.get_all_problems()) == initial_size - 1)

    def test_store(self):
        initial_size = len(self.__repo.get_all_problems())
        prob1 = problema(1, 1, "Problema cu matrice", "02/02/2022")
        self.__repo.store(prob1)

        self.assertEqual(len(self.__repo.get_all_problems()), initial_size + 1)
        prob2 = problema(1, 2, "Problema cu triunghiuri", "05/07/2022")
        self.__repo.store(prob2)
        self.assertEqual(len(self.__repo.get_all_problems()), initial_size + 2)

    def test_delete(self):
        initial_size = len(self.__repo.get_all_problems())
        prob1 = problema(1, 1, "Problema cu matrice", "02/02/2022")
        self.__repo.store(prob1)
        prob2 = problema(1, 2, "Problema cu triunghiuri", "05/07/2022")
        self.__repo.store(prob2)

        self.__repo.delete_problem(prob1)
        self.assertTrue(len(self.__repo.get_all_problems()) == initial_size + 1)

        stud_left = self.__repo.search_prob(1, 2)
        self.assertEqual(stud_left.getDescriere(), "Problema cu triunghiuri")

    def tearDown(self) -> None:
        self.__repo.delete_all()
