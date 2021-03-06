(ns zebra.core
  (:require [clojure.pprint :refer [pprint]]
            [zebra.rules :as rules]
            [zebra.brute-forcer :as solver]))

(def possibilties
  {:color       ["red" "green" "ivory" "yellow", "blue"]
   :nationality ["englishman" "spaniard" "ukrainian" "norwegian" "japanese"]
   :pet         ["dog" "snails" "fox" "horse" "zebra"]
   :drinks      ["coffee" "tea" "milk" "orange" "water"]
   :smokes      ["old gold" "kools" "chesterfields" "lucky strike" "parliaments"]
   :location    ["first" "middle" "right to the ivory house" "next to the house with the fox"
                 "next to the house with the horse" "next to the blue house"]})

(def starting-state
  [{:color nil :nationality nil :pet nil :drinks nil :smokes nil}
   {:color nil :nationality nil :pet nil :drinks nil :smokes nil}
   {:color nil :nationality nil :pet nil :drinks nil :smokes nil}
   {:color nil :nationality nil :pet nil :drinks nil :smokes nil}
   {:color nil :nationality nil :pet nil :drinks nil :smokes nil}])

(defn legal? [suggestion rules]
  (every? (fn [rule] (rule suggestion)) rules))

(defn zebra-legal? [suggestion]
  (legal? suggestion
          [rules/no-dupes
           rules/the-englishman-must-live-in-the-red-house
           rules/the-spaniard-owns-the-dog
           rules/coffee-is-drunk-in-the-green-house
           rules/the-ukrainian-drinks-tea
           rules/the-old-gold-smoker-own-snails
           rules/kools-are-smoked-in-the-yellow-house
           rules/the-lucky-strike-smoker-drinks-orange-juice
           rules/the-japenese-smokes-parliaments
           rules/the-green-house-is-immediately-to-the-right-of-the-ivory-house
           rules/milk-is-drunk-in-the-middle-house
           rules/the-norwegian-lives-in-the-first-house
           rules/the-man-who-smokes-chesterfields-lives-in-the-house-next-to-the-man-with-the-fox
           rules/kools-are-smoked-in-the-house-next-to-the-houses-where-the-horse-is-kept
           rules/the-norwegian-lives-next-to-the-blue-house]))

(defn go [state]
  (if (solver/finished? state zebra-legal?)
    (do (println "SOLVED!") state)
    (if (solver/impossible? state zebra-legal? possibilties)
      (println "IMPOSSIBLE!")
      (recur (solver/brute-force-progressor state zebra-legal? possibilties)))))

(defn -main [& _]
  (pprint (go starting-state)))