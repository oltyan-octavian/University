from repository.stud_repo import StudentRepository
from repository.prob_repo import ProblemRepository
from domain.validators import StudentValidator
from domain.validators import ProblemaValidator
from service.service import StudentService
from service.service import ProblemaService
from ui.console import Console


def run():
    studrepo = StudentRepository()
    studval = StudentValidator()
    studsrv = StudentService(studrepo, studval)
    probrepo = ProblemRepository()
    probval = ProblemaValidator()
    probsrv = ProblemaService(probrepo, probval)
    ui = Console(studsrv, probsrv)
    ui.show_ui()


run()
