(ns content-api.core-test
  (:use [clojure.test]
        [content-api.core]
        [content-api.data]
        [ring.mock.request])
  (:require [cheshire.core :refer :all]))

(defn- from-json [json]
  (parse-string json true))

(deftest restful-endpoints
  (testing "GET / (homepage)"
    (is (= 200 (:status (app (request :get "/")))))
    (is (= {:current_page 1, :total 0, :pages 1}
           (from-json (:body (app (request :get "/"))))))))

(deftest tags
  (testing "GET /tags.json"
    (let [response (app (request :get "/tags.json"))
          body (from-json (:body response))]
      (testing "responds with status OK"
        (is (= 200 (:status response))))
      (testing "has a total"
        (is (= 111 (:total body))))
      (testing "has a description"
        (is (= "All tags" (:description body)))))))
