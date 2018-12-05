(ns zenathark.breakout.core
  (:require [zenathark.breakout.clophaz :as ph]))

(def main-config {:type ph/auto
                  :width 800
                  :height 600
                  :physics {:default :arcade
                            :arcade {:gravity {:y 200}}}
                  :scene {:preload (fn [this]
                                     (doto (.. this -load)
                                           (.setBaseURL "http://labs.phaser.io")
                                           (.image "sky" "assets/skies/space3.png")
                                           (.image "logo" "assets/sprites/phaser3-logo.png")
                                           (.image "red" "assets/particles/red.png")))
                          :create (fn [this]
                                    (.. this -add (image 400 300 "sky"))
                                    (let [particles (.. this -add (particles "red"))
                                          emitter (.createEmitter particles (clj->js {:speed 100
                                                                                      :scale {:start 1
                                                                                              :end 0}
                                                                                      :blendMode "ADD"}))
                                          logo (.. this -physics -add (image 400 100 "logo"))]
                                      (.setVelocity logo 100 200)
                                      (.setBounce logo 1 1)
                                      (.setCollideWorldBounds logo true)
                                      (.startFollow emitter logo)))}})

(defn init
  "Main function, intented to be called by phaser"
  []
  (let [game (ph/create-game main-config)]
    (println "Game Begins")))
