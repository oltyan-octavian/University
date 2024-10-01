def delete_part(crt_list: list, part: int):
    """
    Șterge scorul pentru un participant dat.
    :param crt_list: Lista de participanti
    :type crt_list: list
    :param part: Numarul participantului eliminat
    :type part: integer
    :return: Lista fara participantul eliminat
    """
    del crt_list[part - 1]


def delete_interval(crt_list: list, parti: int, partf: int):
    """
    Șterge scorul pentru un interval de participanți.
    :param crt_list: Lista de participanti
    :type crt_list: list
    :param parti: Primul participant eliminat
    :type parti: integer
    :param partf: Ultimul participant eliminat
    :type partf: integer
    :return: Lista fara participantii eliminati
    """
    del crt_list[parti - 1:partf]


def modify_part(crt_list: list, part: int):
    """
    Inlocuiește scorul de la un participant.
    :param crt_list: Lista de participanti
    :type crt_list: list
    :param part: Participantul al carui scor este modificat
    :type part: integer
    :return: Lista de participanti modificata
    """
    part = part - 1
    sf = 0
    print("Introduceti noile scoruri ale participantului:")
    s = int(input("Scor 1: "))
    crt_list[part]["scor1"] = s
    sf = sf + s
    s = int(input("Scor 2: "))
    crt_list[part]["scor2"] = s
    sf = sf + s
    s = int(input("Scor 3: "))
    crt_list[part]["scor3"] = s
    sf = sf + s
    s = int(input("Scor 4: "))
    crt_list[part]["scor4"] = s
    sf = sf + s
    s = int(input("Scor 5: "))
    crt_list[part]["scor5"] = s
    sf = sf + s
    s = int(input("Scor 6: "))
    crt_list[part]["scor6"] = s
    sf = sf + s
    s = int(input("Scor 7: "))
    crt_list[part]["scor7"] = s
    sf = sf + s
    s = int(input("Scor 8: "))
    crt_list[part]["scor8"] = s
    sf = sf + s
    s = int(input("Scor 9: "))
    crt_list[part]["scor9"] = s
    sf = sf + s
    s = int(input("Scor 10: "))
    crt_list[part]["scor10"] = s
    sf = sf + s
    crt_list[part]["scortotal"] = sf
