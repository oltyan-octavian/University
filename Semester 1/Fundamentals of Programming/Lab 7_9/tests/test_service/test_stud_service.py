import unittest

from domain.validators import StudentValidator
from repository.stud_repo import StudentRepository
from repository.stud_file_repo import StudentFileRepository
from service.service import StudentService


class TestCaseStudService(unittest.TestCase):
    def setUp(self) -> None:
        repo = StudentRepository()
        validator = StudentValidator()
        file_repo = StudentFileRepository()
        self.__srv = StudentService(file_repo, repo, validator)

    def test_add_stud(self):

        added_stud = self.__srv.add_student(1234, "Ionut Popescu", 215)
        self.assertTrue(added_stud.getNume() == "Ionut Popescu")
        self.assertTrue(added_stud.getId() == 1234)

        self.assertEqual(len(self.__srv.get_all_students()), 1)

    def test_delete_stud(self):

        self.__srv.add_student(12345, "Ionut Popescu", 215)
        self.__srv.del_stud(12345)
        self.assertEqual(len(self.__srv.get_all_students()), 0)

    def test_get_all_students(self):

        self.__srv.add_student(1234, "Ionut Popescu", 215)
        self.__srv.add_student(12345, "Marian Ionescu", 214)
        self.assertIsInstance(self.__srv.get_all_students(), list)
        self.assertEqual(len(self.__srv.get_all_students()), 2)

