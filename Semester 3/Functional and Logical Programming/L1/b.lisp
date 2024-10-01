(defun esteAtom (l e)
    (cond 
        ((null l) nil)
        ((and (atom (car l)) (equal (car l) e)) t)
        ((atom (car l)) (esteAtom (cdr l) e))
        ((listp (car l)) (or (esteAtom (car l) e) (esteAtom (cdr l) e)))
    )
)

; (print (esteAtom '(1 2 (3 (a 5) (6 7)) 8 (9 10)) 'a))