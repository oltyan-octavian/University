from utils import sort


def show_lessthan(crt_list: list, scor: float):
    """
    Tipărește participanții care au scor mai mic decât un scor dat.
    :param crt_list: Lista de participanti
    :type crt_list: list
    :param scor: Scorul maxim
    :type scor: float
    :return: Lista de participanti al caror scor este mai mic decat scorul dat.
    """
    lun = len(crt_list)
    for i in range(0, lun):
        if crt_list[i]["scortotal"] < scor:
            print(i + 1)


def show_morethan(crt_list: list, scor: float):
    """
    Tipărește participanții cu scor mai mare decât un scor dat, ordonat după scor.
    :param crt_list: Lista de participanti
    :type crt_list: list
    :param scor: Scorul minim
    :type scor: float
    :return: Lista (ordonata crescator) de participanti al caror scor este mai mare decat scorul dat
    """
    lun = len(crt_list)
    sort.sortare(crt_list)
    for i in range(0, lun):
        if crt_list[i]["scortotal"] > scor:
            print(i+1)


def show_multipleten(crt_list: list, pp: int, up: int):
    """
    Afiseaza participantii care au scorul multiplu de zece.
    :param crt_list: Lista de participanti
    :type crt_list: list
    :param pp: Primul participant
    :type pp: int
    :param up: Ultimul participant
    :type up: int
    :return:
    """
    for i in range(pp - 1, up):
        if crt_list[i]["scortotal"] % 10 == 0:
            print(i+1)
