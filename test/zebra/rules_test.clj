(ns zebra.rules-test
  (:use midje.sweet)
  (:require [zebra.rules :refer :all]))

(facts "The Englishman lives in the red house."
       (fact "legal"
             (the-englishman-must-live-in-the-red-house [{:color "red" :nationality "english"}]) => true)
       (fact "illegal - englishman lives in a blue house"
             (the-englishman-must-live-in-the-red-house [{:color "blue" :nationality "english"}]) => false)
       (fact "illegal - spaniard cannot live in the red house"
             (the-englishman-must-live-in-the-red-house [{:color "red" :nationality "spaniard"}]) => false))



(facts "The Spaniard owns the dog"
       (fact "legal"
             (the-spaniard-owns-the-dog [{:pet "dog" :nationality "spaniard"}]) => true)
       (fact "illegal - spaniard owns the fox"
             (the-spaniard-owns-the-dog [{:pet "fox" :nationality "spaniard"}]) => false)
       (fact "illegal - englishman cannot own the dog"
             (the-spaniard-owns-the-dog [{:pet "dog" :nationality "english"}]) => false))
