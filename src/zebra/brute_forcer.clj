(ns zebra.brute-forcer)

(defn- find-first-key-with-nil-value [map]
  (first (first (filter (fn [[_ v]] (nil? v)) map))))

(defn next-possibility [state possibilities]
  (let [item-to-change (find-first-key-with-nil-value state)]
    (assoc state item-to-change (first (item-to-change possibilities)))))