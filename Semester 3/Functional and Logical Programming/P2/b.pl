interclasare([],[],[]).
interclasare([],B,B).
interclasare(A,[],A).
interclasare([H1 | T1],[H2 | T2],[H1 | R]):- H1 < H2, interclasare(T1, [H2 | T2], R).
interclasare([H1 | T1],[H2 | T2],[H2 | R]):- H1 > H2, interclasare([H1 | T1], T2, R).
interclasare([H1 | T1],[H2 | T2], R):- H1 =:= H2, interclasare([H1 | T1], T2, R).


listeEterogene([],[]).
listeEterogene([H | T],R):- is_list(H),
    !,
    listeEterogene(T,RH),
    interclasare(H,RH,R).
listeEterogene([_ | T], R):- listeEterogene(T, R).
