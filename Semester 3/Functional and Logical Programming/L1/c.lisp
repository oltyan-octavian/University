(defun myMapcar (function list)
    (cond
        ((null list) nil)
        (T (cons (funcall function (car list))
            (myMapcar function (cdr list)))
        )  
    )
)


(defun subliste (l)
    (cond   
        ((atom l) nil)
        (T (apply 'append (list l) (myMapcar 'subliste l)))
    )
)