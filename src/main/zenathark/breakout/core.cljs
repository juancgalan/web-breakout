(ns zenathark.breakout.core
  [:require [phaser :refer [Game AUTO]]])

(def ^:private preload-fn (atom (fn [] 1)))
(def ^:private create-fn (atom (fn [] 1)))

(defn ^:export preload
  []
  (@preload-fn))

(defn ^:export create
  []
  (@create-fn))

(defn fn->js
  "Translates a clojure anonymous function to an js invocable symbol"
  [func]
  (cond
    (fn? func) (type func)
    :else func))

(defn config->js
  "Takes a clojure map and returns a valid js phaser configuration"
  [config]
  (let [preload (:preload config)
        create (:create config)
        jsconfig (clj->js config)]
    (aset jsconfig "preload" (fn->js preload))
    (aset jsconfig "create" (fn->js create))
    jsconfig))

(defn create-game
  "Creates a phazer game object"
  [config]
  (Game. (config->js config)))
