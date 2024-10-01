(defun parcurg_st(arb nv nm)
 (cond
 ((null arb) nil)
 ((= nv (+ 1 nm)) nil)
 (t (cons (car arb) (cons (cadr arb) (parcurg_st (cddr arb) (+ 1 nv) (+ (cadr arb) nm)))))
 )
)

(defun stang(arb)
 (parcurg_st (cddr arb) 0 0)
)

(defun parcurg_dr (arb nv nm)
  (cond
    ((null arb) nil)
    ((= nv (+ 1 nm)) arb)
    (t (parcurg_dr (cddr arb) (+ 1 nv) (+ (cadr arb) nm)))
  )
)

(defun drept(arb)
  (parcurg_dr (cddr arb) 0 0)
)

(defun convertForm (arbore)
  (cond ((null arbore) nil)
        ((numberp (cadr arbore)) (cond 
               ((= (cadr arbore) 0) (list (car arbore)))
               ((= (cadr arbore) 1) (list (car arbore) (convertForm (cddr arbore))))
               (t (list (car arbore) (convertForm (stang arbore)) (convertForm (drept arbore))))
         ))
  )
)


(print (convertForm '(A 2 B 1 H 1 I 1 J 0 C 2 D 2 F 1 K 0 G 0 E 0)))