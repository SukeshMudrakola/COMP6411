;(use 'clojure.java.io)
;(require '[clojure.string :as str])

;(def rows (-> "cust.txt" slurp str/split-lines))

;(println rows)

(def customerIDNameMap {})

(def fetchedName {})
(def customerInfoMap {})
(def productIDNameMap {})
(def productNameIDMap {})
(def fetSSS {})

(def customerNameIDMap {})

(def productIDCostMap {})
(def salesIDtoCustomerID {})
(def salesIDtoProductID {})
(def salesIDtoQuantity {} )
(def customerIDtoSalesID {})

(def fetSalesCust {})
(def fetSalesPro {})





(use 'clojure.java.io)
(require '[clojure.string :as str])



(with-open [rdr (reader "cust.txt")]
     (doseq [line (line-seq rdr)]
       (def temps (str/split line #"\|"))
       ;(println temps)
       (def   customerIDNameMap (assoc customerIDNameMap (get temps 0) (get temps 1)))
       (def   customerNameIDMap (assoc customerNameIDMap (get temps 1) (get temps 0)))

       (def customerInfo (str "--{" (get temps 1) "," (get temps 2) "," (get temps 3) "}"))
       (def customerInfoMap (assoc customerInfoMap (get temps 0) customerInfo))

       ;  (def custT (conj custT temps))
       )
     )

(with-open [rdr (reader "prod.txt")]
  (doseq [line (line-seq rdr)]
    (def temps (str/split line #"\|"))
    ;(println temps)
    (def productIDNameMap (assoc productIDNameMap (get temps 0) (get temps 1)))
    (def productNameIDMap (assoc productNameIDMap (get temps 1) (get temps 0)))
    ; (def produtIDQuantityMap (assoc produtIDQuantityMap (get temps 0) (get temps 0)))


    ; (def productNameIDMap (assoc productNameIDMap (get temps 1) (get temps 0)))


    (def productIDCostMap (assoc productIDCostMap (get temps 0) (read-string (get temps 2))))

    ; (def prodT (conj prodT temps))
    )

  )

(with-open [rdr (reader "sales.txt")]
  (doseq [line (line-seq rdr)]
    (def temps (str/split line #"\|"))
    ;  (println temps)
    (def salesIDtoCustomerID (assoc salesIDtoCustomerID (get temps 0) (get temps 1)))
    (def customerIDtoSalesID (assoc customerIDtoSalesID (get temps 1) (get temps 0)))

    (def salesIDtoProductID (assoc salesIDtoProductID (get temps 0) (get temps 2)))
    (def salesIDtoQuantity (assoc salesIDtoQuantity (get temps 0) (get temps 3)))


    ;(println type salesIDtoCustomerID)
    ;(print salesIDtoCustomerID)


    ;(print salesIDtoProductID)
    ;(print salesIDtoQuantity)


    ; (def prodT (conj prodT temps))
    )

  )


;(println cust_ID_Name_Map )
;(println (get customerIDNameMap "1"))

;(if (= "John Smith" (get customerIDNameMap "1")) (println "present") (println "notpresent"))






(defn printCustomerTable []
  (def sorted_customerIDNameMap (into (sorted-map) customerIDNameMap))
  (doseq [[k v] (map vector (keys sorted_customerIDNameMap) (vals sorted_customerIDNameMap))]
    (println (str k (get customerInfoMap k)))
    )
  )

(defn printProductTable []
  (def sorted_productIDNameMap (into (sorted-map) productIDNameMap))
  (doseq [[k v] (map vector (keys sorted_productIDNameMap) (vals sorted_productIDNameMap))]
    (println (str k "--{" v "," (get productIDCostMap k) "}"))
    )
  )

(defn printSalesTable []
  (def salesIDtoCustomerID (into (sorted-map) salesIDtoCustomerID))
  (def salesIDtoProductID (into (sorted-map) salesIDtoProductID))

  ; (println salesIDtoProductID)
  (doseq [[k v] (map vector (keys salesIDtoCustomerID) (vals salesIDtoCustomerID))]

    (def productID (get salesIDtoProductID k))
    (def productName (get productIDNameMap productID ))
    (def displaySalesTab (str k "--{" (get customerIDNameMap v) "," productName "," (read-string  (get salesIDtoQuantity k)) "}"))
    (println displaySalesTab)

    )
  )





;(def b (hash-map :a "d", b "sukesh"))

;(println b)



(defn totalSalesCustomer []
  (println "enter the customer name")
  (def customerName (read-line))

  (def custValues (vals customerIDNameMap) )

  (def fetC (get customerNameIDMap customerName))
  ; (println salesIDtoCustomerID)

  ;salesIDtoCustomerID is the map and fetC is the value
  ;I want to store all the resulting keys into fetS

  (def fetSalesCust (into {} (filter #(= fetC (second %)) salesIDtoCustomerID)))

  ;salesIDtoProductID

  ;salesIDtoQuantity

  ; productIDCostMap

  ;(println fetS)
(def totalC 0)
  (doseq [[k v] (map vector (keys fetSalesCust) (vals fetSalesCust))]

    (def pID (get salesIDtoProductID k))
    (def price (get productIDCostMap pID))
    (def q (get salesIDtoQuantity k))
    ; (println (type q) )
    ;(println (type price) )

    (def totalC (+ totalC (* price (Double/parseDouble q))))


  )
  ; (println totalC)
  (if (contains? (set custValues) customerName)
    (do (println (str customerName " spent a total of: $" totalC))
        )
    (do (println "Incorrect customer name. Please try again")
        (totalSalesCustomer))
    )
  )


(defn totalSalesProduct []
  (println "enter the product name")
  (def productName (read-line))
  (def prodValues (vals productIDNameMap) )


  (def fetP (get productNameIDMap productName))

  (def fetSalesPro (into {} (filter #(= fetP (second %)) salesIDtoProductID)))



    (def gotVal (keys fetSalesPro) )



  (def totalP 0)
  (doseq [[k v] (map vector (keys fetSalesPro) (vals fetSalesPro))]
    (def Pquantity (get salesIDtoQuantity k))

    ; (println Pquantity)
(def totalP (+ totalP (Integer/parseInt Pquantity)))
    )
  ; (def proCount ((reduce + (intVal))))
  ;(println proCount)

  ; (println totalP)
    (if (contains? (set prodValues) productName)
    (do (println (str "Total sum of " productName  " ordered is: " totalP))
        )
    (do (println "Incorrect product name. Please try again")
        (totalSalesProduct))
    )

  ;(println fetchedName)
  ; (println (contains? customerIDNameMap customerName))

  )


(defn errorInput []

  (println "Invalid input. Please choose from options [1-6]")
  )

(defn exitOption []

  (println "GoodBye!!")
  (System/exit 0)
  )


(defn n [dfe]
  (def v dfe)
  (println v)

  )




(defn userMenu []
  (println "*** Sales Menu ***\n------------------\n1. Display Customer Table\n2. Display Product Table\n3. Display Sales Table\n4. Total Sales for Customer\n5. Total Count for Product\n6. Exit\nEnter an option?")
  (def userInput (read-line))

  (case userInput
    "1" ((printCustomerTable) (userMenu))
    "2" ((printProductTable) (userMenu))
    "3" ((printSalesTable) (userMenu))
    "4" ((totalSalesCustomer) (userMenu))
    "5" ((totalSalesProduct) (userMenu))
    "6" ((exitOption)(userMenu))
    ((println "Invalid input. Please choose from options [1-6]") (userMenu))

    )

  )

(userMenu)

