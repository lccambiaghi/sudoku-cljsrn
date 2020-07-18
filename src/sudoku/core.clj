(ns sudoku.core)

(defn row []
  "A row is a map, up to 9 elements long. Keys are row indices and values are numbers filling that index. Only 4 elements are filled"
  (let [numbers (for [_ (range 5)]
                  {(inc (rand-int 9)) (rand-int 10)})]
    (into {} numbers)))

(defn valid-row? [row]
  "A valid row contains only one number in each location and cannot contain the same number twice"
  (and (apply distinct? (keys row)) (apply distinct? (vals row))))

(defn nine-rows []
  (let [rows          (repeatedly row)
        legal-rows    (filter valid-row? rows)]
    (take 9 legal-rows)))

(defn unique? [coll]
  (if (< (count coll) 2)
    true
    (apply distinct? coll)))

(defn valid-columns? [rows]
  "Given 9 rows, the same number cannot appear in each row index"
  (every? true?
          (for [row-index (range 1 10)
                :let      [column (remove nil? (map #(get % row-index) rows))]]
            (unique? column))))

(defn three-rows->blocks [rows]
  (for [row-indices [[1 2 3] [4 5 6] [7 8 9]]]
    (->> (map #(select-keys % row-indices) rows)
         (map vals)
         (remove nil?)
         (flatten))))

(defn valid-blocks? [rows]
  "The first block is defined by numbers in the first 3 rows and first 3 columns. A number cannot appear twice in a block."
  (let [triplets   (partition 3 rows)
        blocks     (apply concat (map three-rows->blocks triplets))]
    (every? true? (map unique? blocks))))

(defn grid []
  "Creates a sudoku grid, 9x9"
  (let [legal-rows  (repeatedly nine-rows)
        sudoku-rows (filter #(and (valid-columns? %) (valid-blocks? %)) legal-rows)]
    (first sudoku-rows)))
