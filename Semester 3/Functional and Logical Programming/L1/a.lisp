(defun element (l n pos)
    (cond
        ((null l) nil)
        ((= n pos) (car l))
        (T (element (cdr l) n (+ pos 1)))
    )
)

(defun elementMain (l n)
    (element l n 1)
)

; (print (elementMain '(1 2 3 4 5 6 7 8 9 10) '5))