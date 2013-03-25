(ns content-api.data
  (:use [content-api.protocols :only [ApiResponse]]
        [monger.conversion :only [from-db-object]])
  (:require [monger.collection :as mc]
            [monger.core :as mg]))

(mg/connect!)
(mg/set-db! (mg/get-db "govuk_content_development"))

(defn- remove-ids [data]
  (map #(dissoc % :_id) data))

(defrecord Tag [description parent_id tag_id tag_type title]
  ApiResponse
  (formatted-response [this]
    {:title title
     :id nil
     :details {:description description
               :type tag_type}}))

(defn get-tags [& {:keys [type]}]
  (letfn [(to-tag-model [x]
            (Tag. (:description x) (:parent_id x) (:tag_id x) (:tag_type x) (:title x)))]
    (let [where (into {} (filter second {:tag_type type}))]
      (map #(to-tag-model %) (remove-ids (mc/find-maps "tags" where))))))
