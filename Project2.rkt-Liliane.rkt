#lang racket
;;; Liliane Owens
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(define (set-equal? set1 set2)
  (cond
    [(empty? set1) (empty? set2)]                 ; Base case: if set1 is empty, check if set2 is also empty
    [(member (car set1) set2)                   ; If the first element of set1 is in set2
     (set-equal? (cdr set1) (remove (car set1) set2))] ; Recursively check the rest, removing the matched element
    [else #f]))                                   ; If the first element of set1 is not in set2, return false
(set-equal? '(1 2 3 4) '(4 2 1 3))                ; returns #t

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(define (nested-set-equal? set1 set2)
  (cond
    [(and (empty? set1) (empty? set2)) #t]       ; Base case: both sets are empty, return #t
    [(or (empty? set1) (empty? set2)) #f]        ; If one is empty and the other is not, return #f
    [else
     (let ([x (car set1)])                    ; Get the first element from set1
       (if (member-nested? x set2)              ; Check if it is in set2 (considering nested lists)
           (nested-set-equal? (cdr set1)       ; Recursively check the rest of set1 and set2
                             (remove-nested x set2))
           #f))]))                              ; If the element is not in set2, return #f

;; Helper function to check if an element is in a set (supports nested lists)
(define (member-nested? x set)
  (cond
    [(empty? set) #f]                           ; If set is empty, return #f
    [(and (list? x) (list? (car set)))        ; If both x and the first element of the set are lists
     (or (nested-set-equal? x (car set))      ; Recursively compare them for equality
         (member-nested? x (cdr set)))]        ; Otherwise, continue checking in the rest of the set
    [else (or (equal? x (car set))            ; If x is equal to the first element
              (member-nested? x (cdr set)))])) ; Or continue checking in the rest of the set

;; Helper function to remove the first occurrence of an element from a set (supports nested lists)
(define (remove-nested x set)
  (cond
    [(empty? set) '()]                          ; If the set is empty, return an empty list
    [(and (list? x) (list? (first set)))        ; If both x and the first element are lists
     (if (nested-set-equal? x (car set))      ; If they are equal
         (cdr set)                             ; Remove the first element
         (cons (car set) (remove-nested x (rest set))))] ; Otherwise, continue checking
    [else (if (equal? x (car set))            ; If x equals the first element
              (cdr set)                        ; Remove the first element
              (cons (car set) (remove-nested x (cdr set))))])) ; Continue checking
(nested-set-equal? '(1 2 (3 4 5)) '(2 (4 3 5) 1))              ; retuns #t

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(define (union set1 set2)
  (cond
    [(empty? set1) set2]                           ; Base case: if set1 is empty, return set2
    [(member (first set1) set2)                    ; If the first element of set1 is already in set2
     (union (rest set1) set2)]                     ; Skip it and continue with the rest of set1
    [else (cons (first set1) (union (rest set1) set2))])) ; Otherwise, add it to the result and continue
(union '(1 2 3 4) '(2 3 4 5))                             ; returns '(1 2 3 4 5)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(define (intersection set1 set2)
  (cond
    [(empty? set1) '()]                             ; Base case: if set1 is empty, return an empty list
    [(member (car set1) set2)                      ; If the first element of set1 is in set2
     (cons (car set1) (intersection (cdr set1) set2))] ; Include it and continue recursion
    [else (intersection (cdr set1) set2)]))         ; Otherwise, skip it and continue recursion
(intersection '(1 2 3 4) '(2 3 4 5))                ; returns '(2 3 4)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(define (mergesort lst)
  (cond
    [(or (empty? lst) (empty? (rest lst))) lst]  ; Base case: If list is empty or has one element, it's already sorted
    [else
     (letrec ([half (quotient (length lst) 2)]     ; Split the list into two halves
            [left (take lst half)]
            [right (drop lst half)])
       (merge (mergesort left) (mergesort right)))])) ; Recursively sort and merge the two halves

;; Helper function to merge two sorted lists
(define (merge left right)
  (cond
    [(empty? left) right]                       ; If left list is empty, return right
    [(empty? right) left]                       ; If right list is empty, return left
    [(<= (first left) (first right))            ; If first element of left <= first of right
     (cons (first left) (merge (rest left) right))]  ; Include it and merge the rest
    [else
     (cons (first right) (merge left (rest right)))])) ; Otherwise, include first of right and merge
(mergesort '(3 1 2 7 9))                               ; return '(1 2 3 7 9)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(define (powerset lst)
  (if (null? lst)                                   ; When the input list is empty, the function returns a list containing the empty list: '(()).
      '(())
      (letrec ((rest-powerset (powerset (cdr lst))) ;computes the powerset of the rest of the list: (powerset (cdr lst)).
             (with-first (map (lambda (subset) (cons (car lst) subset)) rest-powerset)))  ;Using map, it adds the first element (car lst) to each subset in the smaller powerset.
        (append rest-powerset with-first))))                                              ;The function combines the subsets without the first element and the subsets with the first element added using append.
(powerset '(1 3 5))                                                                      ;return '(() (5) (3) (3 5) (1) (1 5) (1 3) (1 3 5))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(define (nested-reduce lst)
  (define (helper lst seen)
    (cond
      [(empty? lst) '()]                                     ;Base case: If the list is empty, return an empty list
      [(list? (car lst))                                     ; If the first element is a list
       (letrec ([reduced-sublist (helper (car lst) '())])    ;If the first element is a sublist, we recursively process this sublist
         (if (member reduced-sublist seen)                   ; If the first element is an integer, we check if it has already been encountered in the list.
             (helper (cdr lst) seen)                         ;We utilize a helper function to manage seen elements and sublists effectively.
             (cons reduced-sublist (helper (cdr lst) (cons reduced-sublist seen)))))]  ; Check if it's already seen
  [else                                          
       (letrec ([current (car lst)])                         ; Include it if not seen
         (if (member current seen)
             (helper (cdr lst) seen)                                             ; Continue with the rest of the list
           
             (cons current (helper (cdr lst) (cons current seen)))))]))          ; Update seen with the new integer
  (helper lst '()))

(nested-reduce '(1 3 (2 5) (2 5) (2 5 (2 5) (2 5)) 3 7 1))    ; return '(1 3 (2 5) (2 5 (2 5)) 7)
