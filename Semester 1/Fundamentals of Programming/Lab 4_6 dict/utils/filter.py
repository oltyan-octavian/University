def filter_multiple(crt_list: list, scor: float):
    """
    Elimina participantii care nu au scorul egal cu un multiplu al unui scor dat.
    :param crt_list: Lista de participanti
    :type crt_list: list
    :param scor: Scor dat
    :type scor: float
    :return: Lista fara participantii care nu au scorul egal cu un multiplu al unui scor dat.
    """
    lun = len(crt_list)
    new_list = []
    for i in range(0, lun):
        if crt_list[i]["scortotal"] % scor == 0:
            new_list.append(crt_list[i])
    return new_list


def filter_lessthan(crt_list: list, scor: float):
    """
    Elimina participantii care au scorul mai mic decat un scor dat.
    :param crt_list: Lista de participanti
    :type crt_list: list
    :param scor: Scor dat
    :type scor: float
    :return: Lista fara participantii care au scorul mai mic decat un scor dat.
    """
    lun = len(crt_list)
    new_list = []
    for i in range(0, lun):
        if crt_list[i]["scortotal"] >= scor:
            new_list.append(crt_list[i])
    return new_list
