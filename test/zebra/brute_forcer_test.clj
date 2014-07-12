(ns zebra.brute-forcer-test
  (:use midje.sweet)
  (:require [zebra.brute-forcer :refer :all]))

; test data
(def possibilities {:a [1 2 3] :b [4 5 6]})

(facts "next possibility can set the next possible state for checking"
       (fact "it suggests the first possibilty possible"
             (next-possibility (sorted-map :a nil :b nil) possibilities) => {:a 1 :b nil})
       (fact "it suggests the first possibilty possible for later keys"
             (next-possibility (sorted-map :a 1 :b nil) possibilities) => {:a 1 :b 4}))

(facts "handle changing the answer"
       (fact "it can choose the next option"
             (change-answer (sorted-map :a 1 :b 4) possibilities) => {:a 1 :b 5})
       (fact "it can choose the next option again"
             (change-answer (sorted-map :a 1 :b 5) possibilities) => {:a 1 :b 6})
       (fact "it can backtrack then the possibilties are exhusted"
             (change-answer (sorted-map :a 1 :b 6) possibilities) => {:a 2 :b nil}))

(facts "overarching runner - next possibility"
       (fact "it can handle an array of hashes - updates first"
             (next-possibility-list [(sorted-map :a nil :b nil) (sorted-map :a nil :b nil)] possibilities)
             => [{:a 1 :b nil} {:a nil :b nil}])
       (fact "it can handle an array of hashes - updates second"
             (next-possibility-list [(sorted-map :a 1 :b 1) (sorted-map :a nil :b nil)] possibilities)
             => [{:a 1 :b 1} {:a 1 :b nil}]))



(facts "overarching runner - handle changing the answer"
       (fact "it can choose the next option - updates first"
             (change-answer-list [(sorted-map :a 1 :b 4) (sorted-map :a nil :b nil)] possibilities)
             => [{:a 1 :b 5} {:a nil :b nil}])
       (fact "it can choose the next option - updates second"
             (change-answer-list [(sorted-map :a 1 :b 4) (sorted-map :a 1 :b nil)] possibilities)
             => [{:a 1 :b 4} {:a 2 :b nil}])
       (fact "it can choose the next option - backtracks"
             (change-answer-list [(sorted-map :a 1 :b 4) (sorted-map :a 3 :b nil)] possibilities)
             => [{:a 1 :b 5} {:a nil :b nil}])
       )

(declare legal?)
(facts "brute forcer progressor can work out the answer"
       (fact "can go forward"
             (brute-force-progressor anything legal? possibilities) => ...result...
             (provided
               (legal? anything) => true
               (next-possibility-list anything possibilities) => ...result...))
       (fact "can go backward"
             (brute-force-progressor anything legal? possibilities) => ...result...
             (provided
               (legal? anything) => false
               (change-answer-list anything possibilities) => ...result...)))

(facts "knows when it is finished"
       (fact "when all values are filled and legal - we are finished"
             (finished? [{:a 1} {:b 2}] legal?) => true
             (provided
               (legal? anything) => true))
       (fact "when not all values are filled and legal - we are not finished"
             (finished? [{:a 1} {:b nil}] legal?) => false
             (provided
               (legal? anything) => true)))