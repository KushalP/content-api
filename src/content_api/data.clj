(ns content-api.data
  (:use [monger.conversion :only [from-db-object]])
  (:require [monger.collection :as mc]
            [monger.core :as mg]))

(mg/connect!)
(mg/set-db! (mg/get-db "govuk_content_development"))

(defn- remove-ids [data]
  (map #(dissoc % :_id) data))

(defprotocol ApiResponse
  (formatted-response [_]))

(defrecord Tag [description parent_id tag_id tag_type title]
  ApiResponse
  (formatted-response [_] {:title title
                           :id nil
                           :details {:description description
                                     :type tag_type}}))

(defn get-tags []
  (letfn [(to-tag-model [x]
            (Tag. (:description x) (:parent_id x)
                  (:tag_id x) (:tag_type x) (:title x)))]
    (map #(to-tag-model %) (remove-ids (mc/find-maps "tags")))))
