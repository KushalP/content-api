(ns content-api.protocols)

(defprotocol ApiResponse
  (formatted-response [this]))

(defn formatted-response [x] (.formatted-response x))
