(ns awesome.main
  (:require [react]
            [reagent.core :as r]
            [react-native :as rn]
            [reagent.react-native :as rrn]
            ))

(def <> react/createElement)

(defn example-component []
  (<> rn/View
      #js {:style #js {:backgroundColor "#FFFFFF"
                       :flex            1
                       :justifyContent  "center"}}
      (<> rn/Text
          #js {:style #js {:color     "black"
                           :textAlign "center"}}
          (str "hello WORLD!!"))))

(defn hello []
  [rrn/view {:style {:flex 1 :align-items "center" :justify-content "center"}}
   [rrn/text {:style {:font-size 50}} "Hello Krell!"]])

(defn cat-component [props]
  [rrn/text (str "hello I am " props)])

(defn cats []
  [rrn/view
   (cat-component "Mary")
   (cat-component "Joseph")])

(defn renderfn [props]
  (r/as-element [cats])
  )

;; the function figwheel-rn-root MUST be provided. It will be called by
;; by the react-native-figwheel-bridge to render your application.
(defn figwheel-rn-root []
  (renderfn {}))

;; (def styles
;;   ^js (-> {:container
;;            {:flex 1
;;             :backgroundColor "#fff"
;;             :alignItems "center"
;;             :justifyContent "center"}
;;            :title
;;            {:fontWeight "bold"
;;             :fontSize 24
;;             :color "blue"}}
;;           (clj->js)
;;           (rn/StyleSheet.create)))
;;
;; (def style
;;   {:content {:padding 20
;;              :border-bottom-color "#ccc"
;;              :border-bottom-width 0.5}
;;    :title {:font-size 20
;;            :font-weight "bold"
;;            :color "#666"}
;;    :desc {:font-size 18
;;           :font-weight "300"
;;           :color "#333"
;;           :letter-spacing 0}
;;    :row {:flex-direction "row"}})

;; (def ReactNative (js/require "react-native"))
;; (def text (r/adapt-react-class (.-Text ReactNative)))
;; (def view (r/adapt-react-class (.-View ReactNative)))
;; (def view (r/adapt-react-class (rn/View)))
;; (def text (r/adapt-react-class rn/Text))
