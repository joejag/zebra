(ns zebra.brute-forcer
  (:require [zebra.seq-helpers :refer :all]))

; next-pos
(defn- find-next-updateable-value [map]
  (first (find-first has-nil-value? map)))

(defn next-possibility [house possibilities]
  (let [item-to-change (find-next-updateable-value house)]
    (assoc house item-to-change (first (item-to-change possibilities)))))

; change answer
(defn- available-to-update? [[k v] possibities]
  (and (not (nil? v)) (not= v (last (k possibities)))))

(defn- find-next-possibility [current possibilities]
  (let [index (.indexOf possibilities current)]
    (get possibilities (inc index))))

(defn- find-last-updateable-value [house possibities]
  (last (filter (fn [v] (available-to-update? v possibities)) house)))

(defn change-answer [house possibilities]
  (let [[last-updateable-key last-updateable-value] (find-last-updateable-value house possibilities)
        new-value (find-next-possibility last-updateable-value (last-updateable-key possibilities))
        keys-to-blank-out (items-after-element last-updateable-key (keys-as-list house))
        house-without-subsequent-elements (update-to-nil house keys-to-blank-out)]
    (assoc house-without-subsequent-elements last-updateable-key new-value)))

; over arching
(defn next-possibility-list [houses possibilities]
  (let [[index active-list] (find-first (fn [[_ coll]] (some has-nil-value? coll)) (map-indexed vector houses))]
    (assoc houses index (next-possibility active-list possibilities))))

(defn find-last-updateable-list [houses possibilities]
  (find-first (fn [[_ coll]] (find-last-updateable-value coll possibilities)) (reverse (map-indexed vector houses))))

(defn change-answer-list [houses possibilities]
  (let [[index house] (find-last-updateable-list houses possibilities)
        houses-with-subsequent-house-nilled (clear-next-map-in-list houses index)]
    (assoc houses-with-subsequent-house-nilled index (change-answer house possibilities))))

; brute force
(defn brute-force-progressor [houses legalality-checker possibilities]
  (if (legalality-checker houses)
    (next-possibility-list houses possibilities)
    (change-answer-list houses possibilities)))

(defn finished? [houses legalality-checker]
  (and (legalality-checker houses) (every? (fn [coll] (not-any? has-nil-value? coll)) houses)))

(defn impossible? [houses legalality-checker possibilities]
  (if-not (legalality-checker houses)
    (nil? (find-last-updateable-list houses possibilities))
    false))