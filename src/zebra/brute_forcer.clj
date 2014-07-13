(ns zebra.brute-forcer
  (:require [zebra.seq-helpers :refer :all]))

; next-pos
(defn- find-next-updateable-value [map]
  (first (find-first has-nil-value? map)))

(defn- find-next-possibility [current possibilities]
  (let [index (.indexOf possibilities current)]
    (get possibilities (inc index))))

(defn next-possibility [state possibilities]
  (let [item-to-change (find-next-updateable-value state)]
    (assoc state item-to-change (first (item-to-change possibilities)))))

; change answer
(defn- available-to-update? [[k v] possibities]
  (and (not (nil? v)) (not= v (last (k possibities)))))

(defn- find-last-updateable-value [state possibities]
  (last (filter (fn [v] (available-to-update? v possibities)) state)))

(defn change-answer [state possibilities]
  (let [[last-key last-value] (find-last-updateable-value state possibilities)
        next-value (find-next-possibility last-value (last-key possibilities))
        keys-to-blank-out (items-after-element last-key (keys-as-list state))
        cleared-state (update-to-nil state keys-to-blank-out)]
    (assoc cleared-state last-key next-value)))

; over arching
(defn next-possibility-list [state possibilities]
  (let [[index active-list] (find-first (fn [[_ coll]] (some has-nil-value? coll)) (map-indexed vector state))]
    (assoc state index (next-possibility active-list possibilities))))

(defn find-last-updateable-list [state possibilities]
  (find-first (fn [[_ coll]] (find-last-updateable-value coll possibilities)) (reverse (map-indexed vector state))))

(defn change-answer-list [state possibilities]
  (let [[index active-list] (find-last-updateable-list state possibilities)]
    (let [cleared-state (clear-next-map-in-list state index)]
      (assoc cleared-state index (change-answer active-list possibilities)))))

; brute force

(defn brute-force-progressor [state legal-checker possibilities]
  (if (legal-checker state)
    (next-possibility-list state possibilities)
    (change-answer-list state possibilities)))

(defn finished? [state legal-checker]
  (and (legal-checker state) (every? (fn [coll] (every? (fn [[_ v]] (not (nil? v))) coll)) state)))

(defn impossible? [state legal-checker possibilities]
  (if-not (legal-checker state)
    (nil? (find-last-updateable-list state possibilities))
    false))