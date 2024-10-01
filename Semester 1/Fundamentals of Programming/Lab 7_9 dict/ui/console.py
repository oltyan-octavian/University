class Console:
    def __init__(self, srvstud, srvprob):
        """
        Initializeaza consola
        :type srvstud: ShowService
        """
        self.__srvstud = srvstud
        self.__srvprob = srvprob

    def __print_all_students(self):
        """
        Afiseaza toti studentii disponibili

        """
        stud_list = self.__srvstud.get_all_students()
        if len(stud_list) == 0:
            print('Nu exista studenti in lista.')
        else:
            print('Lista de studenti este:')
            for stud in stud_list.values():
                print('Id student: ', stud.getId(), ' - Nume student: ', stud.getNume(), ' - Grup: ', stud.getGrup())

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
                print('Numar laborator: ', prob.getNrlab(), ' - Numar problema: ', prob.getNrprob(), ' - Descriere: ', prob.getDescriere(), ' - Deadline: ', prob.getDeadline())

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

    def show_ui(self):
        while True:
            print('Comenzi disponibile: add_stud, add_prob, show_stud, show_prob, exit')
            cmd = input('Comanda este:')
            cmd = cmd.strip()
            if cmd == 'add_stud':
                self.__add_stud()
            elif cmd == 'add_prob':
                self.__add_prob()
            elif cmd == 'show_stud':
                self.__print_all_students()
            elif cmd == 'show_prob':
                self.__print_all_problems()
            elif cmd == 'exit':
                #this should not be here per principles of app organization
                #added just to showcase how the static method works
                return
            else:
                print('Comanda invalida.')
