(ns app.core
  (:require [react]
            [reagent.core :as r]
            ;; [react-native :as rn]
            [reagent.react-native :as rrn]
            [app.sudoku :as sudoku]
            [cljs.pprint :refer [pprint]]))

;; (enable-console-print!)

;; (def <> react/createElement)

(def styles
  {:container {:flex 1 :align-items "center" :justify-content "center"}
   :title     {:fontWeight "bold"
               :fontSize   24
               :color      "blue"}
   :number    {:color "black" :textAlign "center" :fontSize 19}
   :row       {:flex-direction "row"}})

(defn top-width [item]
  (let [index (inc (.-index item))]
    (if (or (and (<= 28 index) (<= index 36))
            (and (<= 55 index) (<= index 63)))
      3
      0.5)))

(defn right-width [item]
  (let [index (inc (rem (.-index item) 9))]
    (if (or (= 3 index) (= 6 index))
      3
      0.5)))

(defn cell-style [item]
  (let [style {:borderRightColor  "black" :borderRightWidth  (right-width item)
               :borderLeftColor   "black" :borderLeftWidth   0.5
               :borderTopColor    "black" :borderTopWidth    (top-width item)
               :borderBottomColor "black" :borderBottomWidth 0.5
               :width             40      :height            40}]
    style))

(def numbers (into [] (for [n (range 1 (inc 81))] {:key n})))

(defonce grid (->> (complete-grid) flatten (sort-by :index) (into [])))

(def state (r/atom grid))
;; (assoc-in @grid [1 :key] 4)
;; (filter #(= (% :index) 81) @grid)
;; (swap! grid assoc-in [1 :key] 4)

(defn render-number [item]
  (r/as-element
   [rrn/view {:style (cell-style item)}
    (let [number (.-key (.-item item))
          index  (.-index (.-item item))]
      (if number
        [rrn/text {:style (:number styles)} (.-key (.-item item))]
        [rrn/text-input {:style (:number styles)
                         ;; :placeholder (str index)
                         ;; :on-change-text #(swap! grid assoc-in [(dec index) :key] %)
                         ;; :default-value "!!"
                         }]))]))

(defn page []
  [rrn/safe-area-view {:style (:container styles)}
   [rrn/text {:style (:title styles)} "Sudoku"]
   [rrn/flat-list
    {:data        @state
     ;; :data        numbers
     :render-item render-number
     :num-columns 9}]])

(defn renderfn [props]
  (r/as-element [page]))

(defn figwheel-rn-root []
  (renderfn {}))
