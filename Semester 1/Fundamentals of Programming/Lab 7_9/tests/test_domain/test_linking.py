import unittest

from domain.entities import linking
from domain.validators import LinkingValidator
from exceptions.exceptions import ValidationException


class TestCaseLinkingDomain(unittest.TestCase):
    def setUp(self) -> None:
        self.__validator = LinkingValidator()

    def test_create_linking(self):
        link1 = linking(1, 1, 1234, 10)
        self.assertEqual(link1.getNrlab(), 1)
        self.assertEqual(link1.getNrprob(), 1)
        self.assertEqual(link1.getIdstud(), 1234)
        self.assertEqual(link1.getNota(), 10)

        link1.setNrlab(2)
        link1.setNrprob(2)
        link1.setIdstud(12345)
        link1.setNota(5)

        self.assertEqual(link1.getNrlab(), 2)
        self.assertEqual(link1.getNrprob(), 2)
        self.assertEqual(link1.getIdstud(), 12345)
        self.assertEqual(link1.getNota(), 5)

    def test_equals_linking(self):
        link1 = linking(1, 1, 1234, 10)
        link2 = linking(1, 1, 1234, 10)

        self.assertEqual(link1, link2)

        link3 = linking(1, 1, 12345, 10)
        self.assertNotEqual(link1, link3)

    def test_linking_validator(self):
        link1 = linking(1, 1, 1234, 10)
        self.__validator.validate(link1)
        link2 = linking(-1, 1, 1234, 10)
        link3 = linking(1, 1, -12, 10)

        self.assertRaises(ValidationException, self.__validator.validate, link2)
        self.assertRaises(ValidationException, self.__validator.validate, link3)

