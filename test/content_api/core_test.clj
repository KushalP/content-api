(ns content-api.core-test
  (:use clojure.test
        content-api.core
        ring.mock.request))

(deftest restful-endpoints
  (testing "GET / (homepage)"
    (is (= 200 (:status (app (request :get "/")))))))
