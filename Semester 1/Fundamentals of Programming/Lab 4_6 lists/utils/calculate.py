def calculate_average(crt_list: list, pp: int, up: int):
    """
    Calculeaza media scorurilor participantilor dintr-un interval dat.
    :param crt_list: Lista de participanti
    :type crt_list: list
    :param pp: Primul participant
    :type pp: int
    :param up: Ultimul participant
    :type up: int
    :return: Media scorurilor participantilor dintr-un interval dat.
    """
    avg = 0
    np = up - pp + 1
    for i in range(pp - 1, up):
        avg = avg + crt_list[i][10]
    avg = avg / np
    return avg


def calculate_minimum(crt_list: list, pp: int, up: int):
    """
    Calculeaza scorul minim pentru un interval de participanti dat.
    :param crt_list: Lista de participanti
    :type crt_list: list
    :param pp: Primul participant
    :type pp: int
    :param up: Ultimul participant
    :type up: int
    :return: Cel mai mic scor dintr-un interval de participanti dat.
    """
    mn = 100
    for i in range(pp - 1, up):
        if crt_list[i][10] < mn:
            mn = crt_list[i][10]
    return mn
