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

