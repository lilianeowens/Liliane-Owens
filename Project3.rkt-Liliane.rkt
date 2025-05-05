#lang racket
;;;;Liliane Owens
;;Define faces and suits globally
(define faces '(2 3 4 5 6 7 8 9 10 J Q K A))  ; define the faces
(define suits '(♣ ♦ ♥ ♠))                     ;define the suits

;make-deck: Creates a standard 52-card deck.
(define (make-deck)
  (apply append
         (map (lambda (suit)
            (map (lambda (face)
               (cons face suit))   ; Create each card as a cons pair of face and suit
              faces))             ; For each suit, create a list of cards with faces
              suits)))                   ; For each suit, repeat for all suits

;eval-hand: Evaluates the hand’s value,
;adjusting for Aces being worth either 1 or 11.
(define (eval-hand hand)
  (define (card-value card)
    (let ((face (car card)))
      (cond
        [(number? face) face]            ; Numeric cards take their face value
        [(memv face '(J Q K)) 10]       ; Face cards (J, Q, K) are worth 10 points
        [(eq? face 'A) 11])))           ; Ace can be worth 11 initially
  (let* ((values (map card-value hand)) ; Calculate the values of the card
         (sum (apply + values))         ; Sum up all card value
         (ace-count (length (filter (lambda (card)
                                      (eq? (car card) 'A)) hand))))
    (if (> sum 21)                       ; If sum exceeds 21, adjust the value of aces
        (foldl (lambda (current-sum _)
                 (if (> current-sum 21)
                     (- current-sum 10) current-sum)) ; Subtract 10 for each Ace
               sum
               (make-list ace-count 1))      ; Aces start with 11 but can be reduced by 10 if needed
        sum)))                                ; If sum <= 21, return the sum

;deal!: Deals two cards from the deck to start the hand.
(define (deal! deck)
  (let ((hand (list (car deck) (cadr deck))))   ; Create a hand with the first two cards
    (set! deck (drop deck 2))                       ; Remove dealt cards from the deck
    hand))                                          ; Return the new hand

;hit!: Adds a card from the deck to the player or dealer's hand.
(define (hit! deck hand)
  (set! hand (append hand (list (car deck))))  ; Add the top card to hand
  (set! deck (cdr deck)))                       ; Remove the card from the deck

;show-hand: Displays a player's or dealer's hand.
(define (show-hand hand how description)
  (display description)                         ; Display the description
  (display ": ")                                ; Display a colon
  (cond
    [(eq? how 'Full)                            ; If showing full hand, display each card
     (for-each (lambda (card) (display card)
             (display " ")) hand)]
    [(eq? how 'Part)                            ; If showing partial hand, 
     (display " (*****) ")                      ; Hide the first card
     (for-each (lambda (card) (display card)
                 (display " ")) (cdr hand))]) ; Show the rest
  (newline))                                   ; Move to the next line after displaying the hand

;shuffle-deck: Shuffles the deck at the start of the game
(define (shuffle-deck deck)
  (shuffle deck))

;player-turn: Handles the player's decision to "hit" or "stand."
(define (player-turn deck player-hand)
  (let loop ((player-hand player-hand)
             (deck deck))
    (show-hand player-hand 'Full "Your hand")
    (display "Would you like to hit or stand? (h/s): ")
    (let ((response (read)))
      (cond
        [(equal? response 'h)
         (hit! deck player-hand)
         (if (<= (eval-hand player-hand) 21)
             (loop player-hand deck)
             (display "You busted!"))]
        [(equal? response 's)
         (display "You chose to stand.")]
        [else
         (begin
         (displayln "this is not one of our option"))]))))

;dealer-turn: The dealer follows the standard Blackjack rules,
;hitting on 16 or less, and standing on 17 or higher
(define (dealer-turn deck dealer-hand)
  (show-hand dealer-hand 'Part "The dealer has ")
  (let loop ((dealer-hand dealer-hand)
             (deck deck))
    (let ((dealer-value (eval-hand dealer-hand)))
      (cond
        [(>= dealer-value 17)
         (display "Dealer stands with: ")
         (show-hand dealer-hand 'Full "Dealer's hand")]
        [else
         (hit! deck dealer-hand)
         (loop dealer-hand deck)]))))

;determine-winner: Compares the player's and dealer's hands
;to determine the winner based on the rules of Blackjack.

(define (determine-winner player-hand dealer-hand)
  (let ((player-value (eval-hand player-hand))
        (dealer-value (eval-hand dealer-hand)))
    (cond
      [(> player-value 21) (display "You busted! Dealer wins.")]
      [(> dealer-value 21) (display "Dealer busted! You win.")]
      [(> player-value dealer-value) (display "You win!")]
      [(< player-value dealer-value) (display "Dealer wins!")]
      [else (display "It's a tie!")])))

;blackjack-game: Main function to start the game,
;allowing interaction and playthrough.
(define (blackjack-game)
  (define deck (shuffle-deck (make-deck)))  ; Create and shuffle the deck
  (define player-hand (deal! deck))         ; Deal two cards to the player
  (define dealer-hand (deal! deck))         ; Deal two cards to the dealer

  ;; Player's turn
  (player-turn deck player-hand)

  ;; Dealer's turn (only if player didn't bust)
  (when (<= (eval-hand player-hand) 21)
    (dealer-turn deck dealer-hand))

  ;; Determine the winner
  (determine-winner player-hand dealer-hand))

(blackjack-game)                           ;Call blackjack game
