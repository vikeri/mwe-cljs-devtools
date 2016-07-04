(ns mwe.ios.core
  (:import [goog.date Date DateTime UtcDateTime])
  (:require [reagent.core :as r :refer [atom]]
            [cljs.reader :refer [register-tag-parser!]]
            [devtools.core :as devtools]))


(defn extend-dates
  "Adds print instructions to goog.date types"
  []
  (extend-protocol IPrintWithWriter
    goog.date.Date
    (-pr-writer [obj writer _opts]
      (write-all writer
                 "#gdate "
                 [(.getYear obj)
                  (.getMonth obj)
                  (.getDate obj)]))))


(defn register-tag-parsers! []
  (extend-dates)
  (register-tag-parser! 'gdate
                        (fn [[year month date]]
                          (goog.date.Date. year month date))))


(register-tag-parsers!)

(devtools/install!)

;; Does not work properly
(.log js/console {:date (goog.date.Date.)})

;; Sort of works but not really
(.log js/console (goog.date.Date.))

;; Works but no cljs-devtools sugar
(println (goog.date.Date.))

(def ReactNative (js/require "react-native"))

(def app-registry (.-AppRegistry ReactNative))
(def text (r/adapt-react-class (.-Text ReactNative)))
(def view (r/adapt-react-class (.-View ReactNative)))


(defn app-root []
  (fn []
    [view {:style {:flex-direction "column" :margin 100 :align-items "center"}}
     [text {:style {:font-size 30 :font-weight "100" :margin-bottom 20 :text-align "center"}} "Hello"]]))

(defn init []
      (.registerComponent app-registry "mwe" #(r/reactify-component app-root)))
