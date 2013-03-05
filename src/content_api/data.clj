(ns content-api.data
  (:use [monger.conversion :only [from-db-object]])
  (:require [monger.collection :as mc]
            [monger.core :as mg]))

(mg/connect!)
(mg/set-db! (mg/get-db "govuk_content_development"))

(defn- remove-ids [data]
  (map #(dissoc % :_id) data))

(defn get-tags []
  (remove-ids (mc/find-maps "tags")))
