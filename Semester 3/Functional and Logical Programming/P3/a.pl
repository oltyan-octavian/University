coliniare(X,_,X,_,X,_):-!.
coliniare(_,Y,_,Y,_,Y):-!.
coliniare(X1,Y1,X2,Y2,X3,Y3):-
    X1 =\= X2,X2 =\= X3,X1 =\= X3,
    Y1 =\= Y2,Y2 =\= Y3,Y1 =\= Y3,
    P1 is (Y2-Y1)/(X2-X1),
    P2 is (Y3-Y2)/(X3-X2),
    P1 =:= P2.

subsiruri([],[]).
subsiruri([[A,B],[A1,B1],[A2,B2]|T],[[[A,B],[A1,B1],[A2,B2]]|R]):-
    coliniare(A,B,A1,B1,A2,B2), !,
    subsiruri([[A1,B1],[A2,B2]|T],R).
subsiruri([_|T],R):-subsiruri(T,R).

solutii(L, R) :-
    findall(RPartial, subsiruri(L, RPartial), R).
