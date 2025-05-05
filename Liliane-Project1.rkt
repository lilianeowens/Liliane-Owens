#lang racket
;;;; Liliane Owens

;;;;;;;;;Part 1

(cons 1 (cons 2 (cons 3 (cons 4 null))))
;;;;returns '(1 2 3 4)

(cons 1 (cons (cons 2 3) (cons 4 '())))
;;;;returns '(1 (2 . 3) 4)

(cons 1 (cons 2 (cons (cons 3 (cons (cons 4 5) '())) '())))
;;;;returns '(1 2 (3 (4 . 5)))

(cons 1 (cons 2 (cons (cons 3 (cons 4 (cons 5 '()))) '())))
;;;;returns '(1 2 (3 4 5))

(cons (cons 1 2) (cons (cons 3 4) 5))
;;;;returns '((1 . 2) (3 . 4) . 5)


;;;;;;;; Part 2

(cons 1 (cons 2 (cons 3 null)))
;;; returns '(1 2 3)

(cons 1 (cons 2 (cons 3 4)))
;;; returns '(1 2 3 . 4)

(cons 1 (cons 2 (cons (cons 3 4) (cons 5 6))))
;;; returns '(1 2 (3 . 4) 5 . 6)

(cons 1 (cons (cons null 2) (cons 3 null)))
;;;returns '(1 (() . 2) 3)
(cons (cons 2 3) (cons (cons 4 5) (cons 6 7)))
;;;returns '((2 . 3) (4 . 5) 6 . 7)


;;;;;;;; Part 3
(define avg3(lambda (x y z) (/ (+ x y z) 3)))

(avg3 61 25 79)
;;;;returns 55 after calculating the average of three number.
(define (third-item lst)
  (if (and (list? lst) (>= (length lst) 3))  ;;test if length is > 3

      (list-ref lst 2)  ; ;List indices are zero-based, so 2 is the third item
      (error "List must have at least 3 elements")))

(third-item (list 11 22 33 44)) ; This will return 33

