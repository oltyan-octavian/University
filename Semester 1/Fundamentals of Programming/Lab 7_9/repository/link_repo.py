class LinkingRepository:
    def __init__(self):
        self.__links = []

    def store(self, link):
        """
        Adauga un link in lista
        :return: -; lista de link-uri se modifica prin adaugarea link-ului dat
        :rtype:
        """
        self.__links.append(link)

    def search_link(self, nrlab, nrprob, idstud):
        for i in self.__links:
            if i.getIdstud() == idstud and i.getNrlab() == nrlab and i.getNrprob() == nrprob:
                return i

    def notare_link(self, nrlab, nrprob, idstud, nota):
        for i in self.__links:
            if i.getNrprob() == nrprob and i.getNrlab() == nrlab and i.getIdstud() == idstud:
                i.setNota(nota)

    def delete_link(self, link):
        self.__links.remove(link)

    def delete_all(self):
        self.__links.clear()

    def get_all_links(self):
        """
        Returneaza o lista cu toate problemele existente
        :rtype: list of objects de tip problema
        """
        return self.__links

