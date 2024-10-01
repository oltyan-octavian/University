(defun multime (l)
    (cond
        ((null l) nil)
        (T (cons (car l) (multime (stergeDubluri (cdr l) (car l)))))
    )
) 

(defun stergeDubluri (l e)
    (cond
        ((null l) nil)
        ((= (car l) e) (stergeDubluri (cdr l) e))
        (T (cons (car l) (stergeDubluri (cdr l) e)))
    )
)

; (print (multime '(1 1 1 2 3 4 5 5 4 3 2 7 8 9 6)))