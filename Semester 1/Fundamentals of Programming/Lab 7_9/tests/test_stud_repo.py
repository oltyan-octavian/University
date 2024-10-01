import unittest

from domain.entities import student
from repository.stud_repo import StudentRepository


class TestCaseStudRepo(unittest.TestCase):
    def setUp(self) -> None:
        self.__repo = StudentRepository()
        self.__repo.delete_all()
        self.__populate_list()

    def __populate_list(self):
        stud1 = student(1, "Ionut Popescu", 215)
        stud2 = student(2, "Andrei Ionescu", 125)
        stud3 = student(3, "Alexandru Dragomir", 215)
        stud4 = student(4, "Tudor Olar", 214)
        stud5 = student(5, "Octavian Oltyan", 213)
        stud6 = student(6, "Andreea Lung", 212)
        stud7 = student(7, "Laurentiu Diaconescu", 211)
        stud8 = student(8, "Tiberiu Biro", 210)
        stud9 = student(9, "Dorian Popa", 915)
        stud10 = student(10, "Cerasela Vacarescu", 925)

        self.__repo.store(stud1)
        self.__repo.store(stud2)
        self.__repo.store(stud3)
        self.__repo.store(stud4)
        self.__repo.store(stud5)
        self.__repo.store(stud6)
        self.__repo.store(stud7)
        self.__repo.store(stud8)
        self.__repo.store(stud9)
        self.__repo.store(stud10)

    def test_search_stud(self):
        p = self.__repo.search_stud(3)
        self.assertTrue(p.getNume() == 'Alexandru Dragomir')
        self.assertTrue(p.getId() == 3)
        self.assertEqual(p.getGrup(), 215)

        p1 = self.__repo.search_stud(12345)
        self.assertIs(p1, None)

    def test_get_all(self):
        initial_size = len(self.__repo.get_all_students())
        crt_shows = self.__repo.get_all_students()
        self.assertIsInstance(crt_shows, list)

        self.assertEqual(len(crt_shows), initial_size)

        stud2 = self.__repo.search_stud(2)
        stud5 = self.__repo.search_stud(5)
        self.__repo.delete_student(stud2)
        self.__repo.delete_student(stud5)

        crt_shows = self.__repo.get_all_students()
        self.assertEqual(len(crt_shows), initial_size - 2)

        self.__repo.store(student(11, "Andrei Urdea", 412))
        self.assertTrue(len(self.__repo.get_all_students()) == initial_size - 1)

    def test_store(self):
        initial_size = len(self.__repo.get_all_students())
        stud1 = student(1, "Ionut Popescu", 215)
        self.__repo.store(stud1)
        self.assertEqual(len(self.__repo.get_all_students()), initial_size + 1)
        stud2 = student(2, "Andrei Ionescu", 125)
        self.__repo.store(stud2)
        self.assertEqual(len(self.__repo.get_all_students()), initial_size + 2)

    def test_delete(self):
        initial_size = len(self.__repo.get_all_students())
        stud1 = student(1, "Ionut Popescu", 215)
        self.__repo.store(stud1)
        stud2 = student(2, "Andrei Ionescu", 125)
        self.__repo.store(stud2)

        self.__repo.delete_student(stud1)
        self.assertTrue(len(self.__repo.get_all_students()) == initial_size + 1)

        stud_left = self.__repo.search_stud(2)
        self.assertEqual(stud_left.getNume(), "Andrei Ionescu")

    def tearDown(self) -> None:
        self.__repo.delete_all()
