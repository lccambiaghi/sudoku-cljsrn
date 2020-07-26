(ns app.core
  (:require [react]
            [reagent.core :as r]
            [react-native :as rn]
            [reagent.react-native :as rrn]
            [app.sudoku :as sudoku]
            ))

(def <> react/createElement)

(def styles
  {:container {:flex 1 :align-items "center" :justify-content "center"}
   :title     {:fontWeight "bold"
               :fontSize   24
               :color      "blue"}
   :number    {:color "black" :textAlign "center" :fontSize 19}
   :row       {:flex-direction "row"}})

(defn cell-style [index]
  (let [style {:borderRightColor  "black" :borderRightWidth  0.5
               :borderLeftColor   "black" :borderLeftWidth   0.5
               :borderTopColor    "black" :borderTopWidth    0.5
               :borderBottomColor "black" :borderBottomWidth 0.5
               :width             40      :height            40}]
    style))

(def numbers (into [] (for [n (range 1 (inc 81))] {:key n})))

(def grid (sudoku/grid))

(defn render-number [item index separators]
  (<> rn/View
      (clj->js
       ;; {:stile (:container styles)}
       {:style (cell-style index)}
       )
      (<> rn/Text
          (clj->js {:style (:number styles)})
          (.-key (.-item item)))
      ))

(defn page []
  [rrn/safe-area-view {:style (:container styles)}
   [rrn/text {:style (:title styles)} "Sudoku"]
   [rrn/flat-list
    {:data numbers
     :render-item render-number
     :num-columns 9
     ;; :column-wrapper-style {:style {:flex 1 :justify-content "space-around"}}
     }]
   ]
  )

(defn renderfn [props]
  (r/as-element [page])
  )

(defn figwheel-rn-root []
  (renderfn {}))
