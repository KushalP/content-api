(ns content-api.data
  (:use [content-api.protocols :only [ApiResponse formatted-response]]
        [monger.conversion :only [from-db-object]])
  (:require [monger.collection :as mc]
            [monger.core :as mg]))

(mg/connect!)
(mg/set-db! (mg/get-db "govuk_content_development"))

(defrecord Tag [description parent_id tag_id tag_type title])

(defrecord Artefact [title format])

(extend-protocol ApiResponse
  Tag
  (formatted-response [this]
    {:title (:title this)
     :id nil
     :details {:description (:description this)
               :type (:tag_type this)}})
  Artefact
  (formatted-response [this]
    {:title (:title this)
     :format (:format this)}))

(defn- remove-bson-ids [data]
  (map #(dissoc % :_id) data))

(defn get-tags [& {:keys [type]}]
  (letfn [(to-tag-model [x]
            (Tag. (:description x) (:parent_id x) (:tag_id x) (:tag_type x) (:title x)))]
    (let [where (into {} (filter second {:tag_type type}))]
      (map #(to-tag-model %) (remove-bson-ids (mc/find-maps "tags" where))))))

(defn get-artefacts []
  (letfn [(to-tag-model [x]
            (Artefact. (:name x) (:kind x)))]
    (map #(to-tag-model %) (remove-bson-ids (mc/find-maps "artefacts")))))
