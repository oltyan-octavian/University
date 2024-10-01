import unittest

from domain.entities import problema
from domain.validators import ProblemaValidator
from exceptions.exceptions import ValidationException


class TestCaseProblemDomain(unittest.TestCase):
    def setUp(self) -> None:
        self.__validator = ProblemaValidator()

    def test_create_problem(self):
        prob1 = problema(1, 1, "Problema cu matrice", "02/12/2023")
        self.assertEqual(prob1.getNrlab(), 1)
        self.assertEqual(prob1.getNrprob(), 1)
        self.assertEqual(prob1.getDescriere(), "Problema cu matrice")
        self.assertEqual(prob1.getDeadline(), "02/12/2023")

        prob1.setNrlab(2)
        prob1.setNrprob(2)
        prob1.setDescriere("Problema fara matrice")
        prob1.setDeadline("12/02/2023")

        self.assertEqual(prob1.getNrlab(), 2)
        self.assertEqual(prob1.getNrprob(), 2)
        self.assertEqual(prob1.getDescriere(), "Problema fara matrice")
        self.assertEqual(prob1.getDeadline(), "12/02/2023")

    def test_equals_problem(self):
        prob1 = problema(1, 1, "Problema cu matrice", "02/12/2023")
        prob2 = problema(1, 1, "Problema cu matrice", "02/12/2023")

        self.assertEqual(prob1, prob2)

        prob3 = problema(1, 2, "Problema cu matrice", "02/12/2023")
        self.assertNotEqual(prob1, prob3)

    def test_problem_validator(self):
        prob1 = problema(1, 1, "Problema cu matrice", "02/12/2023")
        self.__validator.validate(prob1)
        prob2 = problema(-1, 1, "Problema cu matrice", "02/12/2023")
        prob3 = problema(1, 1, "", "02/12/2023")

        self.assertRaises(ValidationException, self.__validator.validate, prob2)
        self.assertRaises(ValidationException, self.__validator.validate, prob3)
