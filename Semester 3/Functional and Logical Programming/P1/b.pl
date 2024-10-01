divizorComun(X, X, X).

divizorComun(X, Y, D) :- X < Y, Y1 is Y - X, divizorComun(X, Y1, D).
divizorComun(X, Y, D) :- X > Y, divizorComun(Y, X, D).

lista([X], X).

lista([H | T], DC) :- lista(T, DC1), divizorComun(H, DC1, DC).
