(defproject content-api "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.0"]
                 [org.clojure/data.json "0.1.2"]
                 [compojure "1.1.5"]
                 [com.novemberain/monger "1.2.0"]
                 [ring/ring-jetty-adapter "1.1.5"]
                 [ring/ring-json "0.1.2"]]
  :profiles {:dev {:dependencies [[ring-mock "0.1.3"]]}}
  :main content-api.core
  :test-selectors {:default (constantly true)})
