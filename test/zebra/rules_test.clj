(ns zebra.rules-test
  (:use midje.sweet)
  (:require [zebra.rules :refer :all]))

(facts "no duplicates are allowed"
       (fact "legal - no dupes"
             (no-dupes [{:pet "dog"} {:pet "cat"}]) => true)
       (fact "legal - no dupes with nils"
             (no-dupes [{:pet "dog" :nationality nil} {:pet nil :nationality nil}]) => true)
       (fact "illegal - dupes"
             (no-dupes [{:pet "dog"} {:pet "dog"}]) => false))

(facts "co-existance rule"
       (fact "legal - all values not nil and conditions met"
             (co-exist? [anything anything] true true) => true)
       (fact "legal - one value nil - so always return true"
             (co-exist? [anything nil] true false) => true)
       (fact "illegal - one predicate not met"
             (co-exist? [anything anything] true false) => false))

(facts "The Englishman lives in the red house."
       (fact "legal"
             (the-englishman-must-live-in-the-red-house [{:color "red" :nationality "englishman"}]) => true)
       (fact "illegal"
             (the-englishman-must-live-in-the-red-house [{:color "blue" :nationality "englishman"}]) => false))

(facts "The Spaniard owns the dog"
       (fact "legal"
             (the-spaniard-owns-the-dog [{:pet "dog" :nationality "spaniard"}]) => true)
       (fact "illegal"
             (the-spaniard-owns-the-dog [{:pet "fox" :nationality "spaniard"}]) => false))

(facts "Coffee is drunk in the green house."
       (fact "legal"
             (coffee-is-drunk-in-the-green-house [{:color "green" :drinks "coffee"}]) => true)
       (fact "illegal"
             (coffee-is-drunk-in-the-green-house [{:color "red" :drinks "coffee"}]) => false))

(facts "The Ukrainian drinks tea"
       (fact "legal"
             (the-ukrainian-drinks-tea [{:nationality "ukrainian" :drinks "tea"}]) => true)
       (fact "illegal"
             (the-ukrainian-drinks-tea [{:nationality "englishman" :drinks "tea"}]) => false))

(facts "The Old Gold smoker owns snails"
       (fact "legal"
             (the-old-gold-smoker-own-snails [{:smokes "old gold" :pet "snails"}]) => true)
       (fact "illegal"
             (the-old-gold-smoker-own-snails [{:smokes "old gold" :pet "dog"}]) => false))

(facts "Kools are smoked in the yellow house"
       (fact "legal"
             (kools-are-smoked-in-the-yellow-house [{:smokes "kools" :color "yellow"}]) => true)
       (fact "illegal"
             (kools-are-smoked-in-the-yellow-house [{:smokes "kools" :color "red"}]) => false))

(facts "The Lucky Strike smoker drinks orange juice."
       (fact "legal"
             (the-lucky-strike-smoker-drinks-orange-juice [{:smokes "lucky strike" :drinks "orange"}]) => true)
       (fact "illegal"
             (the-lucky-strike-smoker-drinks-orange-juice [{:smokes "lucky strike" :drinks "tea"}]) => false))

(facts "The Japanese smokes Parliaments."
       (fact "legal"
             (the-japenese-smokes-parliaments [{:smokes "parliaments" :nationality "japanese"}]) => true)
       (fact "illegal"
             (the-japenese-smokes-parliaments [{:smokes "parliaments" :nationality "spaniard"}]) => false))


(facts "The green house is immediately to the right of the ivory house."
       (fact "legal"
             (the-green-house-is-immediately-to-the-right-of-the-ivory-house
               [{:color "ivory"} {:color "green"}]) => true)
       (fact "illegal"
             (the-green-house-is-immediately-to-the-right-of-the-ivory-house
               [{:color "green"} {:color "ivory"}]) => false))

(facts "Milk is drunk in the middle house."
       (fact "legal"
             (milk-is-drunk-in-the-middle-house
               [{} {} {:drinks "milk"} {} {}]) => true)
       (fact "illegal"
             (milk-is-drunk-in-the-middle-house
               [{} {} {} {:drinks "milk"} {}]) => false)
       (fact "legal"
             (milk-is-drunk-in-the-middle-house
               [{} {} {} {} {}]) => true))






