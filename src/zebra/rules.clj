(ns zebra.rules)

(defn the-englishman-must-live-in-the-red-house [houses]
  (every? (fn [{nationality :nationality color :color}]
            (if (and nationality color (or (= "englishman" nationality) (= "red" color)))
              (and (= "englishman" nationality) (= "red" color))
              true)) houses))

(defn the-spaniard-owns-the-dog [houses]
  (every? (fn [{nationality :nationality pet :pet}]
            (if (and nationality pet (or (= "spaniard" nationality) (= "dog" pet)))
              (and (= "spaniard" nationality) (= "dog" pet))
              true)) houses))

(defn no-dupes [houses]
  (let [merged-list (remove (fn [[_ v]] (nil? v)) (apply concat (map vec houses)))]
    (= (count (set merged-list)) (count merged-list))))