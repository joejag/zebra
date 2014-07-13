(ns zebra.core
  (:require [zebra.rules :as rules]
            [zebra.brute-forcer :as solver]))

(def possibilties-full
  {:color       ["red" "green" "ivory" "yellow", "blue"]
   :nationality ["englishman" "spaniard" "ukrainian" "norweigian" "japenese"]
   :pet         ["dog" "snails" "fox" "horse" "zebra"]
   :drinks      ["coffee" "tea" "milk" "orange" "water"]
   :smokes      ["old gold" "kools" "chesterfields" "lucky strike" "parliaments"]
   :locations   ["first" "middle" "right to the ivory house" "next to the house with the fox"
                 "next to the house with the horse" "next to the blue house"]})

(def possibilties-reducded
  {:color       ["red" "green"]
   :nationality ["english" "spaniard"]
   :pet         ["dog" "zebra"]})

(def starting-state
  [{:color nil :nationality nil :pet nil}
   {:color nil :nationality nil :pet nil}])

(defn legal? [suggestion rules]
  (every? (fn [rule] (rule suggestion)) rules))

(defn zebra-legal? [suggestion]
  (legal? suggestion
          [rules/the-englishman-must-live-in-the-red-house
           rules/the-spaniard-owns-the-dog
           rules/no-dupes]))

(defn go [state]
  (if (solver/finished? state zebra-legal?)
    (do (println "SOLVED " state) state)
    (if (solver/impossible? state zebra-legal? possibilties-reducded)
      (println "IMPOSSIBLE! " state)
      (recur (solver/brute-force-progressor state zebra-legal? possibilties-reducded)))))

(defn -main [& _]
  (go starting-state))