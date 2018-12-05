(ns zenathark.breakout.clophaz
  (:require [phaser :refer [Game AUTO]]))

(def auto AUTO)

(defn fn->js
  "Translates a clojure anonymous function to an js invocable symbol"
  [func]
  (let [f func]
    (fn [] (this-as this
             (f this)))))

(defn config->js
  "Takes a clojure map and returns a valid js phaser configuration"
  [config]
  (let [preload (:preload (:scene config))
        create (:create (:scene config))
        jsconfig (clj->js config)]
    (aset (aget jsconfig "scene") "preload" (fn->js preload))
    (aset (aget jsconfig "scene") "create" (fn->js create))
    jsconfig))

(defn image
  [name url]
  {:type :image :name name :url url})

(defn create-game
  "Creates a phazer game object"
  [config]
  (Game. (config->js config)))
