(defproject zebra "0.1.0-SNAPSHOT"
            :main zebra.core
            :dependencies [[org.clojure/clojure "1.5.1"]
                           [midje "1.5.1"]]
            :profiles {:dev {:plugins      [[lein-midje "3.1.1"]]}})