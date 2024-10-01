def add_participant(crt_list: list):
    """
    Adaugă scor pentru un nou participant.
    :param crt_list: Lista de participanti
    :return: Lista initiala la care a fost adaugat scorul ultimului participant
    """
    l = {}
    print("Introduceti scorurile participantului:")
    s = int(input("Scor 1: "))
    l.update({"scor1": s})
    s = int(input("Scor 2: "))
    l.update({"scor2": s})
    s = int(input("Scor 3: "))
    l.update({"scor3": s})
    s = int(input("Scor 4: "))
    l.update({"scor4": s})
    s = int(input("Scor 5: "))
    l.update({"scor5": s})
    s = int(input("Scor 6: "))
    l.update({"scor6": s})
    s = int(input("Scor 7: "))
    l.update({"scor7": s})
    s = int(input("Scor 8: "))
    l.update({"scor8": s})
    s = int(input("Scor 9: "))
    l.update({"scor9": s})
    s = int(input("Scor 10: "))
    l.update({"scor10": s})
    s = l["scor1"] + l["scor2"] + l["scor3"] + l["scor4"] + l["scor5"] + l["scor6"] + l["scor7"] + l["scor8"] + l["scor9"] + l["scor10"]
    l.update({"scortotal": s})
    crt_list.append(l)
