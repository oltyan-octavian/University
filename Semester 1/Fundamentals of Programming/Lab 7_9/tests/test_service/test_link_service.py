import unittest

from domain.validators import LinkingValidator
from repository.link_repo import LinkingRepository
from repository.stud_repo import StudentRepository
from service.service import LinkingService


class TestCaseLinkService(unittest.TestCase):
    def setUp(self) -> None:
        repo = LinkingRepository()
        validator = LinkingValidator()
        srepo = StudentRepository()
        self.__srv = LinkingService(repo, validator, srepo)

    def test_add_link(self):

        added_link = self.__srv.add_links(1, 1, 12345)
        self.assertTrue(added_link.getNrlab() == 1)
        self.assertTrue(added_link.getIdstud() == 12345)

        self.assertEqual(len(self.__srv.get_all_links()), 1)

    def test_delete_link(self):

        self.__srv.add_links(1, 1, 1234)
        self.__srv.del_link(1, 1, 1234)
        self.assertEqual(len(self.__srv.get_all_links()), 0)

    def test_get_all_students(self):

        self.__srv.add_links(1, 1, 1234)
        self.__srv.add_links(1, 2, 12345)
        self.assertIsInstance(self.__srv.get_all_links(), list)
        self.assertEqual(len(self.__srv.get_all_links()), 2)

    def test_notare_link(self):

        added_link = self.__srv.add_links(1, 1, 12345)
        self.assertTrue(added_link.getNota() == 1)
        self.__srv.notare_link(1, 1, 12345, 10)
        new_link = self.__srv.search_link(1, 1, 12345)
        self.assertEqual(new_link.getNota(), 10)

    def test_problem_list(self):
        self.__srv.add_links(1, 1, 1234)
        self.__srv.add_links(1, 1, 12345)
        self.__srv.add_links(1, 1, 123456)
        self.__srv.add_links(1, 1, 12)
        self.__srv.add_links(1, 1, 123)
        self.__srv.add_links(1, 2, 123)

        self.__srv.notare_link(1, 1, 12, 10)
        self.__srv.notare_link(1, 1, 123, 6)
        self.__srv.notare_link(1, 1, 1234, 4)
        self.__srv.notare_link(1, 1, 12345, 8)
        self.__srv.notare_link(1, 1, 123456, 9)

        lista = self.__srv.problem_list(1, 1)
        self.assertTrue(len(lista) == 5)

    def test_sort_by_grade(self):
        self.__srv.add_links(1, 1, 1234)
        self.__srv.add_links(1, 1, 12345)
        self.__srv.add_links(1, 1, 123456)
        self.__srv.add_links(1, 1, 12)
        self.__srv.add_links(1, 1, 123)
        self.__srv.add_links(1, 2, 123)

        self.__srv.notare_link(1, 1, 12, 10)
        self.__srv.notare_link(1, 1, 123, 6)
        self.__srv.notare_link(1, 1, 1234, 4)
        self.__srv.notare_link(1, 1, 12345, 8)
        self.__srv.notare_link(1, 1, 123456, 9)

        lista = self.__srv.sort_by_grade(1, 1)
        self.assertTrue(lista[1][1] == 6)
        self.assertEqual(lista[3][0], 123456)
