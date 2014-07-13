(ns zebra.rules)

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