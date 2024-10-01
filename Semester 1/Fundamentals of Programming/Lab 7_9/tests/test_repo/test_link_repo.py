import unittest

from domain.entities import linking
from repository.link_repo import LinkingRepository


class TestCaseLinkRepo(unittest.TestCase):
    def setUp(self) -> None:
        self.__repo = LinkingRepository()
        self.__repo.delete_all()
        self.__populate_list()

    def __populate_list(self):
        link1 = linking(1, 1, 1, 10)
        link2 = linking(1, 2, 2, 5)
        link3 = linking(1, 1, 3, 6)
        link4 = linking(2, 1, 4, 10)
        link5 = linking(1, 3, 5, 8)
        link6 = linking(1, 1, 6, 9)
        link7 = linking(2, 4, 7, 7)
        link8 = linking(2, 3, 8, 4)
        link9 = linking(1, 6, 9, 8)
        link10 = linking(1, 2, 10, 9)

        self.__repo.store(link1)
        self.__repo.store(link2)
        self.__repo.store(link3)
        self.__repo.store(link4)
        self.__repo.store(link5)
        self.__repo.store(link6)
        self.__repo.store(link7)
        self.__repo.store(link8)
        self.__repo.store(link9)
        self.__repo.store(link10)

    def test_search_link(self):
        p = self.__repo.search_link(1, 3, 5)
        self.assertTrue(p.getNota() == 8)

        p1 = self.__repo.search_link(12345, 1, 3)
        self.assertIs(p1, None)

    def test_get_all(self):
        initial_size = len(self.__repo.get_all_links())
        crt_shows = self.__repo.get_all_links()
        self.assertIsInstance(crt_shows, list)

        self.assertEqual(len(crt_shows), initial_size)

        link2 = self.__repo.search_link(1, 6, 9)
        link5 = self.__repo.search_link(2, 4, 7)
        self.__repo.delete_link(link2)
        self.__repo.delete_link(link5)

        crt_shows = self.__repo.get_all_links()
        self.assertEqual(len(crt_shows), initial_size - 2)

        self.__repo.store(linking(1, 2, 4, 5))
        self.assertTrue(len(self.__repo.get_all_links()) == initial_size - 1)

    def test_store(self):
        initial_size = len(self.__repo.get_all_links())
        link1 = linking(1, 1, 1, 10)
        self.__repo.store(link1)

        self.assertEqual(len(self.__repo.get_all_links()), initial_size + 1)
        link2 = linking(1, 2, 2, 5)
        self.__repo.store(link2)
        self.assertEqual(len(self.__repo.get_all_links()), initial_size + 2)

    def test_delete(self):
        initial_size = len(self.__repo.get_all_links())
        link1 = linking(1, 1, 1, 10)
        self.__repo.store(link1)
        link2 = linking(1, 2, 2, 5)
        self.__repo.store(link2)

        self.__repo.delete_link(link1)
        self.assertTrue(len(self.__repo.get_all_links()) == initial_size + 1)

        stud_left = self.__repo.search_link(1, 2, 2)
        self.assertEqual(stud_left.getNota(), 5)

    def tearDown(self) -> None:
        self.__repo.delete_all()
