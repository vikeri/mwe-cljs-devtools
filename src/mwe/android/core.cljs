(ns mwe.ios.core
  (:require [reagent.core :as r :refer [atom]]))


(def ReactNative (js/require "react-native"))

(def app-registry (.-AppRegistry ReactNative))
(def text (r/adapt-react-class (.-Text ReactNative)))
(def view (r/adapt-react-class (.-View ReactNative)))


(defn app-root []
  (fn []
    [view {:style {:flex-direction "column" :margin 40 :align-items "center"}}
     [text {:style {:font-size 30 :font-weight "100" :margin-bottom 20 :text-align "center"}} "Hello bro"]]))

(defn init []
  (.registerComponent app-registry "mwe" #(r/reactify-component app-root)))
