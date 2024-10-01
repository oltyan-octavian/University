def sortare(crt_list: list):
    """
    Sorteaza participantii dintr-o anumita lista.
    :param crt_list: Lista de participanti
    :type crt_list: list
    :return: Lista ordonata crescator
    """
    lun = len(crt_list)
    for i in range(0, lun):
        for j in range(i + 1, lun):
            if crt_list[i]["scortotal"] > crt_list[j]["scortotal"]:
                crt_list[i], crt_list[j] = crt_list[j], crt_list[i]
