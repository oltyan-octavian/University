from repository.stud_repo import StudentRepository
from repository.stud_file_repo import StudentFileRepository
from repository.prob_repo import ProblemRepository
from repository.prob_file_repo import ProblemFileRepository
from repository.link_repo import LinkingRepository
from domain.validators import StudentValidator
from domain.validators import ProblemaValidator
from domain.validators import LinkingValidator
from service.service import StudentService
from service.service import ProblemaService
from service.service import LinkingService
from ui.console import Console


def run():
    studfilerepo = StudentFileRepository()
    studrepo = StudentRepository()
    studval = StudentValidator()
    probfilerepo = ProblemFileRepository()
    probrepo = ProblemRepository()
    probval = ProblemaValidator()
    linkrepo = LinkingRepository()
    linkval = LinkingValidator()
    studsrv = StudentService(studfilerepo, studrepo, studval)
    probsrv = ProblemaService(probfilerepo, probrepo, probval)
    linksrv = LinkingService(linkrepo, linkval, studrepo)
    ui = Console(studsrv, probsrv, linksrv)
    ui.show_ui()


run()
