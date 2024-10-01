import unittest

from domain.entities import student
from domain.validators import StudentValidator
from exceptions.exceptions import ValidationException


class TestCaseStudentDomain(unittest.TestCase):
    def setUp(self) -> None:
        self.__validator = StudentValidator()

    def test_create_student(self):
        stud1 = student(1234, "Ionut Popescu", 215)
        self.assertEqual(stud1.getId(), 1234)
        self.assertEqual(stud1.getNume(), "Ionut Popescu")
        self.assertEqual(stud1.getGrup(), 215)

        stud1.setId(12345)
        stud1.setNume("Andrei Ionescu")
        stud1.setGrup(12)

        self.assertEqual(stud1.getId(), 12345)
        self.assertEqual(stud1.getNume(), "Andrei Ionescu")
        self.assertEqual(stud1.getGrup(), 12)

    def test_equals_student(self):
        stud1 = student(1234, "Ionut Popescu", 215)
        stud2 = student(1234, "Ionut Popescu", 215)

        self.assertEqual(stud1, stud2)

        stud3 = student(12345, "Ionut Popescu", 215)
        self.assertNotEqual(stud1, stud3)

    def test_student_validator(self):
        stud1 = student(1234, "Ionut Popescu", 215)
        self.__validator.validate(stud1)
        stud2 = student(-2, "Ionut Popescu", 215)
        stud3 = student(1234, "Ionut Popescu", 0)

        self.assertRaises(ValidationException, self.__validator.validate, stud2)
        self.assertRaises(ValidationException, self.__validator.validate, stud3)
