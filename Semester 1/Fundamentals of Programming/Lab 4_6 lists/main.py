from test import test
from ui import add_part
from ui import menu
from ui import show
from utils import calculate
from utils import filter
from utils import modify
from utils import sort
from utils import undo_act
from utils import cpy


def run():
    test.test_all()
    undo_list = []
    crt_list = []
    undo_act.add_undo(undo_list, crt_list)
    menu.print_menu()
    while True:
        opt = input("Optiunea aleasa este: ")
        if opt == '1':
            add_part.add_participant(crt_list)
            undo_act.add_undo(undo_list, crt_list)
        elif opt == '2':
            menu.print_2()
            x = input("Optiunea aleasa este: ")
            if x == '1':
                part = int(input("Participantul ales este: "))
                modify.delete_part(crt_list, part)
                undo_act.add_undo(undo_list, crt_list)
            elif x == '2':
                parti = int(input("Primul participant sters: "))
                partf = int(input("Ultimul participant sters: "))
                modify.delete_interval(crt_list, parti, partf)
                undo_act.add_undo(undo_list, crt_list)
            elif x == '3':
                part = int(input("Participantul ales este: "))
                modify.modify_part(crt_list, part)
                undo_act.add_undo(undo_list, crt_list)
            else:
                print("Nu exista aceasta optiune.")
        elif opt == '3':
            menu.print_3()
            x = input("Optiunea aleasa este: ")
            if x == '1':
                scor = float(input("Alegeti un scor: "))
                show.show_lessthan(crt_list, scor)
            elif x == '2':
                sort.sort(crt_list)
                print(crt_list)
            elif x == '3':
                lun = len(crt_list)
                scor = float(input("Alegeti un scor: "))
                for i in range(0, lun):
                    if crt_list[i][10] > scor:
                        print(i + 1)
            else:
                print("Nu exista aceasta optiune.")
        elif opt == '4':
            menu.print_4()
            x = input("Optiunea aleasa este: ")
            if x == '1':
                pp = int(input("Primul participant este: "))
                up = int(input("Ultimul participant este: "))
                avg = calculate.calculate_average(crt_list, pp, up)
                print("Media scorurilor este: ", avg)
            elif x == '2':
                pp = int(input("Primul participant este: "))
                up = int(input("Ultimul participant este: "))
                mn = calculate.calculate_minimum(crt_list, pp, up)
                print("Scorul cel mai mic din intervalul dat este: ", mn)
            elif x == '3':
                pp = int(input("Primul participant este: "))
                up = int(input("Ultimul participant este: "))
                show.show_multipleten(crt_list, pp, up)
            else:
                print("Nu exista aceasta optiune.")
        elif opt == '5':
            menu.print_5()
            x = input("Optiunea aleasa este: ")
            if x == '1':
                scor = float(input("Alegeti un scor: "))
                filter.filter_multiple(crt_list, scor)
                undo_act.add_undo(undo_list, crt_list)
            elif x == '2':
                scor = float(input("Alegeti un scor: "))
                filter.filter_lessthan(crt_list, scor)
                undo_act.add_undo(undo_list, crt_list)
            else:
                print("Nu exista aceasta optiune.")
        elif opt == '6':
            print(crt_list)
        elif opt == '7':
            crt_list = cpy.list_copy(undo_list[len(undo_list) - 2])
            undo_act.remove_undo(undo_list)
        elif opt == '8':
            lc = input("Introduceti comenzile separate prin ';': ")
            com = lc.split(";")
            for i in range(0, len(com)):
                s = com[i]
                comanda = s.split()
                if comanda[0] == 'adaugare':
                    l = []
                    suma = 0
                    for j in range(1, len(comanda)):
                        nr = int(comanda[j])
                        l.append(nr)
                        suma = suma + int(comanda[j])
                    l.append(suma)
                    crt_list.append(l)
                elif comanda[0] == 'media':
                    pp = int(comanda[1])
                    up = int(comanda[2])
                    avg = calculate.calculate_average(crt_list, pp, up)
                    print(avg)
                elif comanda[0] == 'sortare':
                    sort.sortare(crt_list)
        elif opt == '9':
            return
        else:
            print("Nu exista aceasta optiune.")


run()
