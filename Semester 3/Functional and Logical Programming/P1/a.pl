listaToMultime([], []).
listaToMultime([H|T], Rezultat) :-
    listaToMultime(T, Temporar),
    (member(H, Temporar) -> Rezultat = Temporar ; Rezultat = [H|Temporar]).

