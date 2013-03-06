(ns content-api.core
  (:use [compojure.core]
        [content-api.data]
        [ring.adapter.jetty]
        [ring.middleware.json]
        [ring.util.response])
  (:require [compojure.handler :as handler])
  (:gen-class))

(defroutes main-routes
  (GET "/" [] (response {:total 0, :current_page 1, :pages 1}))
  (GET "/tags.json" []
       (let [tags (get-tags)]
         (response {:total (count tags)
                    :description "All tags"
                    :results (map formatted-response tags)}))))

(def app
  (handler/site (-> main-routes
                    wrap-json-params
                    wrap-json-response)))

(defn -main [& args]
  (let [port (read-string (get (System/getenv) "GOVUK_APP_PORT" "8080"))]
    (run-jetty app {:port port})))
