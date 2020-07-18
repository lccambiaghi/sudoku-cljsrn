(ns awesome.main
  (:require [react]
            [reagent.core :as r]
            [react-native :as rn]
            [reagent.react-native :as rrn]
            ))


(def styles
  {:content {:padding 20
             :border-bottom-color "#ccc"
             :border-bottom-width 0.5}
   :container {:flex 1 :align-items "center" :justify-content "center"}
   :text {:font-size 30
          :color     "black"
          :font-weight "bold"
          :textAlign "center"}
   :row {:flex-direction "row"}})

(defn hello []
  [rrn/view {:style {:flex 1 :align-items "center" :justify-content "center"}}
   [rrn/text {:style {:font-size 50}} "Hello Krell!"]])

(defn cat-text [props]
  [rrn/text {:stile (:text styles)} (str "hello I am " props)])

(defn cat-image []
  [rrn/image {:source {:uri "https://reactnative.dev/docs/assets/p_cat1.png"}
              :style {:width 200 :height 200}}])

(def state (r/atom {:is-hungry true
                    :text nil}))

(defn cat-button []
  [rrn/button
   {:title (str  "I am " (if (:is-hungry  @state)
                           "hungry"
                           "full"))
    :on-press #(swap! state assoc :is-hungry (not (:is-hungry  @state)))
    :disabled (not (:is-hungry  @state))}])

(defn input-translator []
  [rrn/view {:style {:padding 10}}
   [rrn/text-input {:style {:height 40}
                    :placeholder "Type here to translate"
                    :on-change-text #(swap! state assoc :text %)
                    :default-value (:text @state)}]
   [rrn/text (->> (clojure.string/split (:text @state) " ")
                  (map (fn [] "🍕")))]])

(defn scroll-logos []
  [rrn/scroll-view
   [rrn/text {:style {:font-size 50}} "Scroll down"]
   (repeat 20 [rrn/image {:source {:uri "https://reactnative.dev/img/tiny_logo.png"
                                   :width 64
                                   :height 64}}])])

(def <> react/createElement)

(defn flat-list []
  [rrn/flat-list
   {:data [{:key "Devin"}
           {:key "Devn"}
           {:key "Den"}
           {:key "Jay"}]
    :render-item #(<> rn/Text
                      #js {:style #js {:color     "black"
                                       :textAlign "center"}}
                      (str %))}])

(defn section-list []
  [rrn/section-list
   {:sections [{:title "D" :data ["Devin", "Dan", "Dominic"]}
               {:title "J" :data ["Jay", "Jackson"]}]
    :render-item (fn [item] (<> rn/Text
                                #js {:style #js {:color     "black"
                                                 :textAlign "center"}}
                                (str "Item")
                                ))
    :render-section-header (fn [sec] (<> rn/Text
                                         #js {:style #js {:color     "black"
                                                          :textAlign "center"}}
                                         (str "Section")
                                         ))
    :key-extractor (fn [item ix] ix)}
   ]
  )

(section-list)

;; (defn section-list
;;   (<> rn/View
;;       #js {:style #js {:backgroundColor "#FFFFFF"
;;                        :flex            1
;;                        :justifyContent  "center"}}
;;       (<> rn/Text
;;           #js {:style #js {:color     "black"
;;                            :textAlign "center"}}
;;           (str "HELLO WORLD!!"))))

(defn cats []
  [rrn/view {:style (:container styles)}
   ;; (cat-text "Mary")
   ;; (cat-image)
   ;; (cat-button)
   ;; (input-translator)
   ;; (scroll-logos)
   (flat-list)
   ;; (section-list)
   ])

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

;; (def ReactNative (js/require "react-native"))
;; (def text (r/adapt-react-class (.-Text ReactNative)))
;; (def view (r/adapt-react-class (.-View ReactNative)))
;; (def view (r/adapt-react-class (rn/View)))
;; (def text (r/adapt-react-class rn/Text))
