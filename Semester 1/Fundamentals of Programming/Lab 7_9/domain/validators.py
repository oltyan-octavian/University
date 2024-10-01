from exceptions.exceptions import ValidationException

class StudentValidator:
    def validate(self, stud):
        errors = []
        if len(stud.getNume()) <= 2:
            errors.append('Numele studentului trebuie sa aiba mai mult de 2 caractere.')
        if stud.getId() <= 0:
            errors.append('Id-ul studentului trebuie sa fie mai mare de 0.')
        if stud.getGrup() <= 0:
            errors.append('Grupul din care face parte studentul trebuie sa fie mai mare de 0.')

        if len(errors) > 0:
            errors_string = '\n'.join(errors)
            raise ValidationException(errors_string)


class ProblemaValidator:
    def validate(self, prob):
        errors = []
        if len(prob.getDescriere()) <= 2:
            errors.append('Descrierea problemei trebuie sa aiba mai mult de 2 caractere.')
        if prob.getNrlab() <= 0:
            errors.append('Numarul laboratorului trebuie sa fie mai mare de 0.')
        if prob.getNrprob() <= 0:
            errors.append('Numarul problemei trebuie sa fie mai mare de 0.')
        if prob.getDeadline() <= "01/01/0001":
            errors.append('Deadline-ul problemei trebuie sa fie o data valida.')

        if len(errors) > 0:
            errors_string = '\n'.join(errors)
            raise ValidationException(errors_string)


class LinkingValidator:
    def validate(self, link):
        errors = []
        if link.getNrlab() <= 0:
            errors.append('Numarul laboratorului trebuie sa fie mai mare de 0.')
        if link.getNrprob() <= 0:
            errors.append('Numarul problemei trebuie sa fie mai mare de 0.')
        if link.getIdstud() < 1:
            errors.append('Id-ul studentului trebuie sa fie mai mare sau egal cu 1.')
        if link.getNota() < 1 or link.getNota() > 10:
            errors.append('Nota trebuie sa fie mai mare sau egala cu 1 si mai mic sau egala cu 10.')

        if len(errors) > 0:
            errors_string = '\n'.join(errors)
            raise ValidationException(errors_string)

