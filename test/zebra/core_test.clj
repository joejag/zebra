(ns zebra.core-test
  (:use midje.sweet)
  (:require [zebra.core :refer :all]
            [zebra.rules :refer :all]))

(fact "is legal when simple rule says so"
      (legal? anything [(fn [_] true)]) => true)

(fact "is illegal when simple rule says so"
      (legal? anything [(fn [_] false)]) => false)