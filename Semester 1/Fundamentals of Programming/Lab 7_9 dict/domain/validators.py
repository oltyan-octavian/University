from domain.entities import student
from domain.entities import problema


class StudentValidator:
    def validate(self, stud):
        errors = []
        if len(stud.getNume()) < 2:
            errors.append('Numele studentului trebuie sa aiba mai mult de 2 caractere.')
        if stud.getId() < 0:
            errors.append('Id-ul studentului trebuie sa fie mai mare de 0.')
        if stud.getGrup() < 0:
            errors.append('Grupul din care face parte studentul trebuie sa fie mai mare de 0.')

        if len(errors) > 0:
            errors_string = '\n'.join(errors)
            raise ValueError(errors_string)


def test_student_validator():
    test_validator = StudentValidator()
    stud1 = student(1234, 'Ionut Popescu', 215)
    test_validator.validate(stud1)
    stud2 = student(-2, '', 55)

    try:
        test_validator.validate(stud2)
        assert False
    except ValueError:
        assert True

    stud3 = student(123, 'Marian Ionescu', -55)
    try:
        test_validator.validate(stud3)
        assert False
    except ValueError:
        assert True


class ProblemaValidator:
    def validate(self, prob):
        errors = []
        if len(prob.getDescriere()) < 2:
            errors.append('Descrierea problemei trebuie sa aiba mai mult de 2 caractere.')
        if prob.getNrlab() < 0:
            errors.append('Numarul laboratorului trebuie sa fie mai mare de 0.')
        if prob.getNrprob() < 0:
            errors.append('Numarul problemei trebuie sa fie mai mare de 0.')
        if prob.getDeadline() < "01/01/0001":
            errors.append('Deadline-ul problemei trebuie sa fie o data valida.')

        if len(errors) > 0:
            errors_string = '\n'.join(errors)
            raise ValueError(errors_string)


def test_problema_validator():
    test_validator = ProblemaValidator()
    prob1 = problema(1, 1, 'Problema cu matrice', '14/11/2022')
    test_validator.validate(prob1)
    prob2 = problema(-2, 0, '', "15/12/2022")

    try:
        test_validator.validate(prob2)
        assert False
    except ValueError:
        assert True

    prob3 = problema(123, 12, 'Problema cu vectori', "00/00/2022")
    try:
        test_validator.validate(prob3)
        assert False
    except ValueError:
        assert True
