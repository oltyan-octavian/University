class Console:
    def __init__(self, srvstud, srvprob, srvlink):
        """
        Initializeaza consola
        :type srvstud: ShowService
        """
        self.__srvstud = srvstud
        self.__srvprob = srvprob
        self.__srvlink = srvlink

    def __print_all_students(self):
        """
        Afiseaza toti studentii disponibili

        """
        stud_list = self.__srvstud.get_all_students()
        if len(stud_list) == 0:
            print('Nu exista studenti in lista.')
        else:
            print('Lista de studenti este:')
            for stud in stud_list:
                # print(show)
                print(
                    'Id student: ', stud.getId(), ' - Nume student: ', stud.getNume(), ' - Grupa: ', stud.getGrup())

    def __print_all_students_file(self):
        """
        Afiseaza toti studentii disponibili

        """
        stud_list = self.__srvstud.get_all_students_file()
        if len(stud_list) == 0:
            print('Nu exista studenti in lista.')
        else:
            print('Lista de studenti este:')
            for stud in stud_list:
                # print(show)
                print(
                    'Id student: ', stud.getId(), ' - Nume student: ', stud.getNume(), ' - Grupa: ', stud.getGrup())

    def __print_all_problems(self):
        """
        Afiseaza toti studentii disponibili

        """
        prob_list = self.__srvprob.get_all_problems()
        if len(prob_list) == 0:
            print('Nu exista probleme in lista.')
        else:
            print('Lista de probleme este:')
            for prob in prob_list:
                # print(show)
                print(
                    'Numar laborator: ', prob.getNrlab(), ' - Numar problema: ', prob.getNrprob(), ' - Descriere: ', prob.getDescriere(), ' - Deadline: ', prob.getDeadline())

    def __print_all_problems_file(self):
        """
        Afiseaza toti studentii disponibili

        """
        prob_list = self.__srvprob.get_all_problems_file()
        if len(prob_list) == 0:
            print('Nu exista probleme in lista.')
        else:
            print('Lista de probleme este:')
            for prob in prob_list:
                # print(show)
                print(
                    'Numar laborator: ', prob.getNrlab(), ' - Numar problema: ', prob.getNrprob(), ' - Descriere: ', prob.getDescriere(), ' - Deadline: ', prob.getDeadline())

    def __print_all_links(self):
        """
        Afiseaza toti studentii disponibili

        """
        link_list = self.__srvlink.get_all_links()
        if len(link_list) == 0:
            print('Nu exista asignari in lista.')
        else:
            print('Lista de asignari este:')
            for link in link_list:
                # print(show)
                print(
                    'Numar laborator: ', link.getNrlab(), ' - Numar problema: ', link.getNrprob(), ' - Id student: ', link.getIdstud(), ' - Nota: ', link.getNota())

    def __add_stud(self):
        """
        Adauga un serial cu datele citite de la tastatura
        """
        nume = input("Numele studentului:")
        try:
            idstud = int(input("Id-ul studentului:"))
            grupa = int(input("Grupa:"))
        except ValueError:
            print('Id-ul studentului si grupa trebuie sa fie numere.')
            return

        try:
            added_stud = self.__srvstud.add_student(idstud, nume, grupa)
            print('Studentul ' + added_stud.getNume() + ' (' + str(added_stud.getId()) + ') a fost adaugat cu succes.')
        except ValueError as ve:
            print(str(ve))

    def __add_stud_file(self):
        """
        Adauga un serial cu datele citite de la tastatura
        """
        nume = input("Numele studentului:")
        try:
            idstud = int(input("Id-ul studentului:"))
            grupa = int(input("Grupa:"))
        except ValueError:
            print('Id-ul studentului si grupa trebuie sa fie numere.')
            return

        try:
            added_stud = self.__srvstud.add_student_file(idstud, nume, grupa)
            print('Studentul ' + added_stud.getNume() + ' (' + str(added_stud.getId()) + ') a fost adaugat cu succes.')
        except ValueError as ve:
            print(str(ve))

    def __add_prob(self):
        """
        Adauga un serial cu datele citite de la tastatura
        """
        try:
            nrlab = int(input("Numarul laboratorului: "))
            nrprob = int(input("Numarul problemei: "))
        except ValueError:
            print('Numarul problemei si numarul laboratorului trebuie sa fie numere.')
            return
        descriere = input("Descrierea problemei: ")
        deadline = input("Deadline-ul (format: xx/xx/xxxx) este: ")

        try:
            added_prob = self.__srvprob.add_problems(nrlab, nrprob, descriere, deadline)
            print('Problema ' + added_prob.getDescriere() + ' (' + str(added_prob.getNrlab()) + ', ' + str(added_prob.getNrprob()) + ') a fost adaugata cu succes.')
        except ValueError as ve:
            print(str(ve))

    def __add_prob_file(self):
        try:
            nrlab = int(input("Numarul laboratorului: "))
            nrprob = int(input("Numarul problemei: "))
        except ValueError:
            print('Numarul problemei si numarul laboratorului trebuie sa fie numere.')
            return
        descriere = input("Descrierea problemei: ")
        deadline = input("Deadline-ul (format: xx/xx/xxxx) este: ")

        try:
            added_prob = self.__srvprob.add_problems_file(nrlab, nrprob, descriere, deadline)
            print('Problema ' + added_prob.getDescriere() + ' (' + str(added_prob.getNrlab()) + ', ' + str(added_prob.getNrprob()) + ') a fost adaugata cu succes.')
        except ValueError as ve:
            print(str(ve))

    def __add_link(self):
        """
        Adauga un link cu datele citite de la tastatura
        """
        try:
            nrlab = int(input("Numarul laboratorului: "))
            nrprob = int(input("Numarul problemei: "))
            idstud = int(input("Id-ul studentului: "))
        except ValueError:
            print('Numarul problemei, numarul laboratorului si id-ul studentului trebuie sa fie numere.')
            return

        try:
            added_link = self.__srvlink.add_links(nrlab, nrprob, idstud)
            print('Asignarea ' + str(added_link.getIdstud()) + ' (' + str(added_link.getNrlab()) + ', ' + str(
                added_link.getNrprob()) + ') a fost adaugata cu succes.')
        except ValueError as ve:
            print(str(ve))

    def __show_student(self):
        try:
            idstud = int(input("Introduceti id-ul studentului afisat: "))
        except ValueError:
            print('ID-ul studentului trebuie sa fie un numar.')
            return
        try:
            stud = self.__srvstud.search_student(idstud)
            print('Id student: ', stud.getId(), ' - Nume student: ', stud.getNume(), ' - Grupa: ', stud.getGrup())
        except ValueError as ve:
            print(str(ve))

    def __show_problem(self):
        try:
            nrlab = int(input("Introduceti numarul laboratorului problemei afisate: "))
            nrprob = int(input("Introduceti numarul problemei afisate: "))
        except ValueError:
            print('Numarul laboratorului si numarul problemei trebuie sa fie numere.')
            return
        try:
            prob = self.__srvprob.search_problem(nrlab, nrprob)
            print('Numar laborator: ', prob.getNrlab(), ' - Numar problema: ', prob.getNrprob(), ' - Descriere: ', prob.getDescriere(), ' - Deadline: ', prob.getDeadline())
        except ValueError as ve:
            print(str(ve))

    def __notare_link(self):
        try:
            nrlab = int(input("Numarul laboratorului: "))
            nrprob = int(input("Numarul problemei: "))
            idstud = int(input("Id-ul studentului: "))
            nota = int(input("Introduceti nota dorita: "))
        except ValueError:
            print('Numarul problemei, numarul laboratorului, id-ul studentului si nota trebuie sa fie numere.')
            return
        try:
            self.__srvlink.notare_link(nrlab, nrprob, idstud, nota)
        except ValueError as ve:
            print(str(ve))

    def __get_list_by_grade(self):
        try:
            nrlab = int(input("Numarul laboratorului: "))
            nrprob = int(input("Numarul problemei: "))
            sort = str(input("Introduceti tipul de sortare dorit (gnomesort, quicksort, bubblesort): "))
            rev = input("Doriti ca lista sa fie ordonata descrescator? (True/False): ")
        except ValueError:
            print('Numarul problemei si numarul laboratorului trebuie sa fie numere.')
            return
        try:
            if rev == "True":
                reverse = True
            else:
                reverse = False
            if sort == "gnomesort":
                problem_list = self.__srvlink.gnome_sort_by_grade(nrlab, nrprob, reverse)
            elif sort == "quicksort":
                problem_list = self.__srvlink.quick_sort_by_grade(nrlab, nrprob, reverse)
            if sort == "bubblesort":
                problem_list = self.__srvlink.bubble_sort_by_grade(nrlab, nrprob, reverse)
            for i in problem_list:
                print("Id student - " + str(i.getIdstud()) + " | Nota - " + str(i.getNota()))
        except ValueError as ve:
            print(str(ve))

    def __top_3_grades(self):
        try:
            nrlab = int(input("Numarul laboratorului: "))
            nrprob = int(input("Numarul problemei: "))
            reverse = bool(input("Doriti ca lista sa fie ordonata descrescator? (True/False): "))
        except ValueError:
            print('Numarul problemei si numarul laboratorului trebuie sa fie numere.')
            return
        try:
            problem_list = self.__srvlink.sort_by_grade(nrlab, nrprob, reverse)
            for i in range(len(problem_list) - 1, len(problem_list) - 4, -1):
                print("Id student - " + str(problem_list[i].getIdstud()) + " | Nota - " + str(problem_list[i].getNota()))
        except ValueError as ve:
            print(str(ve))

    def __get_list_by_name(self):
        try:
            nrlab = int(input("Numarul laboratorului: "))
            nrprob = int(input("Numarul problemei: "))
            reverse = bool(input("Doriti ca lista sa fie ordonata descrescator? (True/False): "))
        except ValueError:
            print('Numarul problemei si numarul laboratorului trebuie sa fie numere.')
            return
        try:
            problem_list = self.__srvlink.sort_by_name(nrlab, nrprob, reverse)
            for i in problem_list:
                print("Id student - " + str(i.getIdstud()) + " | Nume - " + str(self.__srvstud.get_stud_name(i.getIdstud())) + " | Nota - " + str(i.getNota()))
        except ValueError as ve:
            print(str(ve))

    def __failing_students(self):
        try:
            full_list = self.__srvlink.final_student_list()
            ok = 1
            for i in full_list:
                if i[1] < 5:
                    print("Nume student - " + str(i[0]) + " | Nota - " + str(i[1]))
                    ok = 0
            if ok == 1:
                print("Nu exista studenti cu media mai mica decat 5.")
        except ValueError as ve:
            print(str(ve))

    def __delete_student(self):
        try:
            idstud = int(input("Introduceti id-ul studentului sters: "))
        except ValueError:
            print("Id-ul trebuie sa fie un numar")
            return

        try:
            self.__srvstud.del_stud(idstud)
        except ValueError as ve:
            print(str(ve))

    def __delete_student_file(self):
        try:
            idstud = int(input("Introduceti id-ul studentului sters: "))
        except ValueError:
            print("Id-ul trebuie sa fie un numar")
            return

        try:
            self.__srvstud.del_stud_file(idstud)
        except ValueError as ve:
            print(str(ve))

    def __delete_problem(self):
        try:
            nrlab = int(input("Introduceti numarul laboratorului sters: "))
            nrprob = int(input("Introduceti numarul problemei sterse: "))
        except ValueError:
            print("Numarul laboratorului si numarul problemei trebuie sa fie numere")
            return

        try:
            self.__srvprob.del_prob(nrlab, nrprob)
        except ValueError as ve:
            print(str(ve))

    def __delete_problem_file(self):
        try:
            nrlab = int(input("Introduceti numarul laboratorului sters: "))
            nrprob = int(input("Introduceti numarul problemei sterse: "))
        except ValueError:
            print("Numarul laboratorului si numarul problemei trebuie sa fie numere")
            return

        try:
            self.__srvprob.del_prob_file(nrlab, nrprob)
        except ValueError as ve:
            print(str(ve))

    def __delete_link(self):
        try:
            nrlab = int(input("Introduceti numarul laboratorului sters: "))
            nrprob = int(input("Introduceti numarul problemei sterse: "))
            idstud = int(input("Introduceti id-ul studentului sters: "))
        except ValueError:
            print("Numarul laboratorului, numarul problemei si id-ul studentului trebuie sa fie numere")
            return

        try:
            self.__srvlink.del_link(nrlab, nrprob, idstud)
        except ValueError as ve:
            print(str(ve))

    def __random_student(self):
        try:
            lungnume = int(input("Introduceti lungimea numelui studentului: "))
        except ValueError:
            print("Lungimea trebuie sa fie un numar.")
            return

        try:
            added_stud = self.__srvstud.add_random_student(lungnume)
            print('Studentul ' + added_stud.getNume() + ' (' + str(added_stud.getId()) + ') a fost adaugat cu succes.')
        except ValueError as ve:
            print(str(ve))

    def show_ui(self):
        print('Comenzi disponibile: add_stud, add_stud_file, add_prob, add_prob_file, add_link, show_stud, show_stud_file, show_prob, show_prob_file, show_link, search_stud, search_prob, notare_link, sort_by_grade, top_3, sort_by_name, failing_students, del_stud, del_stud_file, del_prob, del_prob_file, del_link, random_stud, exit')
        cmd = input('Comanda este:')
        cmd = cmd.strip()
        if cmd == 'add_stud':
            self.__add_stud()
        elif cmd == 'add_stud_file':
            self.__add_stud_file()
        elif cmd == 'add_prob':
            self.__add_prob()
        elif cmd == 'add_prob_file':
            self.__add_prob_file()
        elif cmd == 'add_link':
            self.__add_link()
        elif cmd == 'show_stud':
            self.__print_all_students()
        elif cmd == 'show_stud_file':
            self.__print_all_students_file()
        elif cmd == 'show_prob_file':
            self.__print_all_problems_file()
        elif cmd == 'show_prob':
            self.__print_all_problems()
        elif cmd == 'show_link':
            self.__print_all_links()
        elif cmd == 'search_stud':
            self.__show_student()
        elif cmd == 'search_prob':
            self.__show_problem()
        elif cmd == 'notare_link':
            self.__notare_link()
        elif cmd == 'sort_by_grade':
            self.__get_list_by_grade()
        elif cmd == 'top_3':
            self.__top_3_grades()
        elif cmd == 'sort_by_name':
            self.__get_list_by_name()
        elif cmd == 'failing_students':
            self.__failing_students()
        elif cmd == 'del_stud':
            self.__delete_student()
        elif cmd == 'del_stud_file':
            self.__delete_student_file()
        elif cmd == 'del_prob':
            self.__delete_problem()
        elif cmd == 'del_prob_file':
            self.__delete_problem_file()
        elif cmd == 'del_link':
            self.__delete_link()
        elif cmd == 'random_stud':
            self.__random_student()
        elif cmd == 'exit':
            return
        else:
            print('Comanda invalida.')
        self.show_ui()
