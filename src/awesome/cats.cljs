(ns awesome.cats
  (:require [react]
            [reagent.core :as r]
            [react-native :as rn]
            [reagent.react-native :as rrn]
            ))

(def styles
  {:content   {:padding             20
               :border-bottom-color "#ccc"
               :border-bottom-width 0.5}
   :container {:flex 1 :align-items "center" :justify-content "center"}
   :title     {:fontWeight "bold"
               :fontSize   24
               :color      "blue"}
   :text      {:font-size   30
               :color       "black"
               :font-weight "bold"
               :textAlign   "center"}
   :row       {:flex-direction "row"}})

(defn hello []
  [rrn/view {:style {:flex 1 :align-items "center" :justify-content "center"}}
   [rrn/text {:style {:font-size 50}} "Hello Krell!"]])

(defn cat-text [props]
  [rrn/text {:style (:text styles)} (str "hello I am " props)])

(defn cat-image []
  [rrn/image {:source {:uri "https://reactnative.dev/docs/assets/p_cat1.png"}
              :style  {:width 200 :height 200}}])

(def state (r/atom {:is-hungry true
                    :text      nil}))

(defn cat-button []
  [rrn/button
   {:title    (str  "I am " (if (:is-hungry  @state)
                           "hungry"
                           "full"))
    :on-press #(swap! state assoc :is-hungry (not (:is-hungry  @state)))
    :disabled (not (:is-hungry  @state))}])

(defn input-translator []
  [rrn/view {:style {:padding 10}}
   [rrn/text-input {:style          {:height 40}
                    :placeholder    "Type here to translate"
                    :on-change-text #(swap! state assoc :text %)
                    :default-value  (:text @state)}]
   [rrn/text (->> (clojure.string/split (:text @state) " ")
                  (map (fn [] "🍕")))]])

(defn scroll-logos []
  [rrn/scroll-view
   [rrn/text {:style {:font-size 50}} "Scroll down"]
   (repeat 20 [rrn/image {:source {:uri    "https://reactnative.dev/img/tiny_logo.png"
                                   :width  64
                                   :height 64}}])])

(def <> react/createElement)

(defn flat-list []
  [rrn/flat-list
   {:data        [{:key "Devin"}
                  {:key "Devn"}
                  {:key "Den"}
                  {:key "Jay"}]
    :render-item #(<> rn/Text
                      #js {:style #js {:color     "black"
                                       :textAlign "center"}}
                      (.-key (.-item %)))}])

(defn section-list []
  [rrn/section-list
   {:sections              [{:title "D" :data ["Devin", "Dan", "Dominic"]}
                            {:title "J" :data ["Jay", "Jackson"]}]
    :render-item           (fn [item] (<> rn/Text
                                          #js {:style #js {:color     "black"
                                                           :textAlign "center"}}
                                          (.-item item)))
    :render-section-header (fn [section] (<> rn/Text
                                             #js {:style #js {:color     "black"
                                                              :textAlign "center"}}
                                             (.-title (.-section section))))
    :key-extractor         (fn [item ix] ix)}])

(defn cats []
  [rrn/view {:style (:container styles)}
   (cat-text "Mary")
   (cat-image)
   (cat-button)
   (input-translator)
   (scroll-logos)
   (flat-list)
   (section-list)
   ])
