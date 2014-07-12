(ns zebra.seq-helpers)

(defn find-first [pred coll]
  (first (filter pred coll)))

(defn keys-as-list [coll]
  (vec (map (fn [[k _]] k) coll)))

(defn items-after-element [item coll]
  (subvec coll (.indexOf coll item)))

(defn update-to-nil [map keys-to-update]
  (reduce #(update-in % [%2] (fn [_] nil)) map keys-to-update))

(defn has-nil-value? [[_ v]]
  (nil? v))

(defn index-greater-than-coll [index coll]
  (<= (inc index) (count coll)))

(defn clear-next-map-in-list
  "if a map exists after the index, clear out the values of the map"
  [coll index]
  (let [next-index (inc index)]
    (if (index-greater-than-coll next-index coll)
      (assoc coll next-index (update-to-nil (get coll next-index) (keys-as-list (first coll))))
      coll)))