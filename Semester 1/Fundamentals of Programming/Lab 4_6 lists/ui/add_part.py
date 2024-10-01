def add_participant(crt_list: list):
    """
    Adaugă scor pentru un nou participant.
    :param crt_list: Lista de participanti
    :return: Lista initiala la care a fost adaugat scorul ultimului participant
    """
    l = []
    print("Introduceti scorurile participantului:")
    s = int(input("Scor 1: "))
    l.append(s)
    s = int(input("Scor 2: "))
    l.append(s)
    s = int(input("Scor 3: "))
    l.append(s)
    s = int(input("Scor 4: "))
    l.append(s)
    s = int(input("Scor 5: "))
    l.append(s)
    s = int(input("Scor 6: "))
    l.append(s)
    s = int(input("Scor 7: "))
    l.append(s)
    s = int(input("Scor 8: "))
    l.append(s)
    s = int(input("Scor 9: "))
    l.append(s)
    s = int(input("Scor 10: "))
    l.append(s)
    s = l[0] + l[1] + l[2] + l[3] + l[4] + l[5] + l[6] + l[7] + l[8] + l[9]
    l.append(s)
    crt_list.append(l)
