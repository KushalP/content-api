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
        (is (= "All tags" (:description body))))
      (testing "structure of a single tag result"
        (is (= {:title "Business Link", :id nil,
                :details {:description nil, :type "legacy_source"}}
               (first (:results body)))))))
  (testing "GET /tags.json?type=?"
    (testing "type is 'section'"
      (let [response (app (request :get "/tags.json?type=section"))
            body (from-json (:body response))]
        (testing "responds with status OK"
          (is (= 200 (:status response))))
        (testing "result count is 109"
          (is (= 109 (count (:results body)))))
        (testing "the 'section' type will be provided"
          (is (= "section" (-> (:results body)
                               first
                               :details
                               :type))))))
    (testing "type is 'legacy_source'"
      (let [response (app (request :get "/tags.json?type=legacy_source"))
            body (from-json (:body response))]
        (testing "responds with status OK"
          (is (= 200 (:status response))))
        (testing "the 'legacy_source' type will be provided"
          (is (= "legacy_source" (-> (:results body)
                               first
                               :details
                               :type))))))))

(deftest artefacts
  (testing "GET /artefacts.json"
    (let [response (app (request :get "/artefacts.json"))
          body (from-json (:body response))]
      (testing "response with status OK"
        (is (= 200 (:status response))))
      (testing "count is 3210"
        (is (= 3210 (:total body))))
      (testing "artefact has a title and a format"
        (is (= {:title "Bereavement Allowance", :format "programme"}
               (first (:results body))))))))
