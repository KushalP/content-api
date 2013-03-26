(ns content-api.core
  (:use [compojure.core]
        [ring.adapter.jetty]
        [ring.middleware.json]
        [ring.util.response])
  (:require [compojure.handler :as handler]
            [content-api.data :as data]
            [content-api.protocols :as protocols])
  (:gen-class))

(defroutes main-routes
  (GET "/" [] (response {:total 0, :current_page 1, :pages 1}))
  (GET "/artefacts.json" []
       (let [artefacts (data/get-artefacts)]
         (response {:total (count artefacts)
                    :results (map #(protocols/formatted-response %) artefacts)})))
  (GET "/tags.json" {{type :type} :params}
       (let [tags (data/get-tags :type type)]
         (response {:total (count tags)
                    :description "All tags"
                    :results (map #(protocols/formatted-response %) tags)}))))

(def app
  (handler/site (-> main-routes
                    wrap-json-params
                    wrap-json-response)))

(defn -main [& args]
  (let [port (read-string (get (System/getenv) "GOVUK_APP_PORT" "8080"))]
    (run-jetty app {:port port})))
