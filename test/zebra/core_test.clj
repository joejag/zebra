(ns zebra.core-test
  (:use midje.sweet)
  (:require [zebra.core :as subject]))

(def nationalities ["english" "spanish"])
(def pets          ["dog" "cat"])
(def colors        ["blue" "red"])

(def known-facts
  [{:nationality "english" :pet   "dog"}
   {:pet         "cat"     :color "red"}])

(defn possibilties [constraint]
  {:nationality (if (:nationality constraint) [(:nationality constraint)] nationalities)
   :pet        (if (:pet constraint) [(:pet constraint)] pets)
   :color      (if (:color constraint) [(:color constraint)] colors)
   })


(fact "possibilites exist outside some known constraints"
      (possibilties {:nationality "english" :pet "dog"}) => {:nationality ["english"] :pet ["dog"] :color ["blue" "red"]})

(defn remove-possibilties
  [start known-facts]
  start
  )

(fact "possibilties can be removed by other known facts"
      (remove-possibilties {:nationality ["english"] :pet ["dog"] :color ["blue" "red"]} [{:pet "cat" :color "red"}]) =>
      {:nationality ["english"] :pet ["dog"] :color ["blue"]})