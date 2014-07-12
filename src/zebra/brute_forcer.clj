(ns zebra.brute-forcer)

(defn- find-next-updateable-value [map]
  (first (first (filter (fn [[_ v]] (nil? v)) map))))

(defn- find-next-possiblity [current possibilities]
  (let [index (.indexOf possibilities current)]
    (get possibilities (inc index))))

(defn next-possibility [state possibilities]
  (let [item-to-change (find-next-updateable-value state)]
    (assoc state item-to-change (first (item-to-change possibilities)))))




(defn- available-to-update? [[k v] possibities]
  (and (not (nil? v)) (not= v (last (k possibities)))))

(defn- find-last-updateable-value [state possibities]
  (last (filter (fn [v] (available-to-update? v possibities)) state)))

(defn- items-after-element [item coll]
  (subvec coll (.indexOf coll item)))

(defn- keys-list [coll]
  (vec (map (fn [[k _]] k) coll)))

(defn update-vals [map vals f]
  (reduce #(update-in % [%2] f) map vals))

(defn change-answer [state possibilities]
  (let [[last-key last-value] (find-last-updateable-value state possibilities)
        next-value (find-next-possiblity last-value (last-key possibilities))
        keys-to-blank-out (items-after-element last-key (keys-list state))
        cleared-state (update-vals state keys-to-blank-out (fn[_] nil))]
    (assoc cleared-state last-key next-value)))