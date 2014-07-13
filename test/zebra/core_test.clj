(ns zebra.core-test
  (:use midje.sweet)
  (:require [zebra.core :refer :all]
            [zebra.rules :refer :all]))

;{:color nil :nationality nil :pet nil :drinks nil :smokes nil, :location nil}



(fact "is legal when simple rule says so"
      (legal? anything [(fn [_] true)]) => true)

(fact "is illegal when simple rule says so"
      (legal? anything [(fn [_] false)]) => false)

(facts "legality"
       (fact "legal game"
             (legal? [{:nationality "english" :color "red" :pet "cat"}
                      {:nationality "spaniard" :color "blue" :pet "dog"}]
                     [
                       the-englishman-must-live-in-the-red-house
                       the-spaniard-owns-the-dog
                       ]
                     ) => true)
       (fact "illegal game"
             (legal? [{:nationality "english" :color "red" :pet "dog"}
                      {:nationality "spaniard" :color nil :pet "dog"}]
                     [
                       the-englishman-must-live-in-the-red-house
                       the-spaniard-owns-the-dog
                       ]
                     ) => false))
