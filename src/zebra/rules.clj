(ns zebra.rules)

(defn the-englishman-must-live-in-the-red-house [houses]
  (every? (fn [{nationality :nationality color :color}]
            (if (or (= "english" nationality) (= "red" color))
              (and (= "english" nationality) (= "red" color))
              true)) houses))

(defn the-spaniard-owns-the-dog [houses]
  (every? (fn [{nationality :nationality pet :pet}]
            (if (or (= "spaniard" nationality) (= "dog" pet))
              (and (= "spaniard" nationality) (= "dog" pet))
              true)) houses))