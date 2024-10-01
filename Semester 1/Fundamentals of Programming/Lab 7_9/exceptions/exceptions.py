class ManagerException(Exception):
    pass


class ValidationException(ManagerException):
    def __init__(self, msgs):
        """
        :param msgs: lista de mesaje de eroare
        :type msgs: msgs
        """
        self.__err_msgs = msgs

    def getMessages(self):
        return self.__err_msgs

    def __str__(self):
        return 'Validation Exception: ' + str(self.__err_msgs)


class RepositoryException(ManagerException):
    def __init__(self, msg):
        self.__msg = msg

    def getMessage(self):
        return self.__msg

    def __str__(self):
        return 'Repository Exception: ' + str(self.__msg)


class DuplicateIDException(RepositoryException):
    def __init__(self):
        RepositoryException.__init__(self, "ID duplicat.")


class GradeAlreadyAssignedException(RepositoryException):
    def __init__(self):
        RepositoryException.__init__(self, "Nota existenta deja pentru studentul si problema data.")


class ProblemNotFoundException(RepositoryException):
    def __init__(self):
        RepositoryException.__init__(self, "Problema nu a fost gasita.")


class GradeNotFoundException(RepositoryException):
    def __init__(self):
        RepositoryException.__init__(self, "Nota nu a fost gasita.")


class StudentNotFoundException(RepositoryException):
    def __init__(self):
        RepositoryException.__init__(self, "Studentul nu a fost gasit.")


class NotEnoughGradesException(ManagerException):
    def __init__(self):
        pass


class CorruptedFileException(ManagerException):
    def __init__(self):
        pass
