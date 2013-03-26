(ns content-api.protocols)

(defprotocol ApiResponse
  (formatted-response [this]
    "Returns the JSON-like structure that we expect to return in the API response"))
