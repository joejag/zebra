(ns zebra.rules
  (:require [zebra.seq-helpers :refer [find-first]]))

(defn no-dupes [houses]
  (let [merged-list (remove (fn [[_ v]] (nil? v)) (apply concat (map vec houses)))]
    (= (count (set merged-list)) (count merged-list))))

(defn co-exist?
  "if two values are both set, and one matches one rule, both rules must match"
  [values pred1 pred2]
  (if (not-any? nil? values)
    (if (or pred1 pred2)
      (and pred1 pred2) true)
    true))

(defn the-englishman-must-live-in-the-red-house [houses]
  (every?
    (fn [{nationality :nationality color :color}]
      (co-exist? [nationality color] (= nationality "englishman") (= color "red"))) houses))

(defn the-spaniard-owns-the-dog [houses]
  (every? (fn [{nationality :nationality pet :pet}]
            (co-exist? [nationality pet] (= pet "dog") (= nationality "spaniard"))) houses))

(defn coffee-is-drunk-in-the-green-house [houses]
  (every? (fn [{drinks :drinks color :color}]
            (co-exist? [drinks color] (= drinks "coffee") (= color "green"))) houses))

(defn the-ukrainian-drinks-tea [houses]
  (every? (fn [{drinks :drinks nationality :nationality}]
            (co-exist? [drinks nationality] (= drinks "tea") (= nationality "ukrainian"))) houses))

(defn the-old-gold-smoker-own-snails [houses]
  (every? (fn [{smokes :smokes pet :pet}]
            (co-exist? [smokes pet] (= smokes "old gold") (= pet "snails"))) houses))

(defn kools-are-smoked-in-the-yellow-house [houses]
  (every? (fn [{smokes :smokes color :color}]
            (co-exist? [smokes color] (= smokes "kools") (= color "yellow"))) houses))

(defn the-lucky-strike-smoker-drinks-orange-juice [houses]
  (every? (fn [{smokes :smokes drinks :drinks}]
            (co-exist? [smokes drinks] (= smokes "lucky strike") (= drinks "orange"))) houses))

(defn the-japenese-smokes-parliaments [houses]
  (every? (fn [{smokes :smokes nationality :nationality}]
            (co-exist? [smokes nationality] (= smokes "parliaments") (= nationality "japanese"))) houses))

(defn- hash-containing-value [coll attribute expected]
  (find-first (fn [[_ coll]] (= expected (attribute coll))) (map-indexed vector coll)))

(defn the-green-house-is-immediately-to-the-right-of-the-ivory-house [houses]
  (let [[green-index _] (hash-containing-value houses :color "green")
        [ivory-index _] (hash-containing-value houses :color "ivory")]
    (if (not-any? nil? [green-index ivory-index])
      (= (dec green-index) ivory-index)
      true)))

(defn milk-is-drunk-in-the-middle-house [houses]
  (let [[milk-index _] (hash-containing-value houses :drinks "milk")]
    (if (not (nil? milk-index))
      (= 2 milk-index) true)))

(defn the-norwegian-lives-in-the-first-house [houses]
  (let [[norwegian-index _] (hash-containing-value houses :nationality "norwegian")]
    (if (not (nil? norwegian-index))
      (= 0 norwegian-index) true)))

(defn the-man-who-smokes-chesterfields-lives-in-the-house-next-to-the-man-with-the-fox [houses]
  (let [[chesterfields-index _] (hash-containing-value houses :smokes "chesterfields")
        [fox-index _] (hash-containing-value houses :pet "fox")]
    (if (not-any? nil? [chesterfields-index fox-index])
      (or (= (dec chesterfields-index) fox-index) (= (inc chesterfields-index) fox-index))
      true)))

(defn kools-are-smoked-in-the-house-next-to-the-houses-where-the-horse-is-kept [houses]
  (let [[kools-index _] (hash-containing-value houses :smokes "kools")
        [horse-index _] (hash-containing-value houses :pet "horse")]
    (if (not-any? nil? [kools-index horse-index])
      (or (= (dec kools-index) horse-index) (= (inc kools-index) horse-index))
      true)))


(defn the-norwegian-lives-next-to-the-blue-house [houses]
  (let [[nationality-index _] (hash-containing-value houses :nationality "norwegian")
        [color-index _] (hash-containing-value houses :color "blue")]
    (if (not-any? nil? [nationality-index color-index])
      (or (= (dec nationality-index) color-index) (= (inc nationality-index) color-index))
      true)))
