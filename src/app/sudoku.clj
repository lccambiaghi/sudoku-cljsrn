(ns app.sudoku)

(defn random-row []
  (let [row   (rand-int 9)
        cells (for [col  (range 9)
                    :let [index (+ (* row 9) col)
                          block [(quot row 3) (quot col 3)]]]
                {:index index :row row :col col :number (inc (rand-int 9)) :block block})]
    (take 3 (shuffle cells))))

(defn valid-row? [row]
  (->> (map :number row) (apply distinct?)))

(defn nine-rows []
  (->> (repeatedly random-row)
       (filter valid-row?)
       (take 9)))

(defn valid-rows? [rows]
  (let [cells (flatten rows)
        rows  (-> (group-by :row cells) vals)]
    (->> (map valid-row? rows) (every? true?))))

(defn valid-columns? [rows]
  (let [cells (flatten rows)
        cols  (-> (group-by :col cells) vals)]
    (->> (map valid-row? cols) (every? true?))))

(defn valid-blocks? [rows]
  (let [cells  (flatten rows)
        blocks (-> (group-by :block cells) vals)]
    (->> (map valid-row? blocks) (every? true?))))

(defn sudoku-grid []
  (->> (repeatedly nine-rows)
       (filter valid-rows?)
       (filter valid-columns?)
       (filter valid-blocks?)
       first
       flatten))

(defn all-elements []
  (for [ix   (range 81)
        :let [row (quot ix 9)
              col (rem ix 9)
              block [(quot row 3) (quot col 3)]]]
    {:index ix :row row :col col :block block}))

(defn complete-grid []
  (let [grid     (sudoku-grid)
        all      (all-elements)
        existing (set (map :index grid))
        missing  (remove #(contains? existing (:index %)) all)]
    (->> (concat grid missing) (sort-by :index))))

(defmacro grid []
  (complete-grid))

(comment
  (map :number grid)

  (filter #(= (% :index) 4) grid)

  (count (complete-grid))
  )
