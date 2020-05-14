(defproject http-onto "0.1.0-SNAPSHOT"
  :description "HTTP Ontology"
  :url "http://labs.nereide.fr/mthl/http"
  :license {:name "Apache-2.0"
            :url "http://www.apache.org/licenses/LICENSE-2.0"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [net.sourceforge.owlapi/owlapi-distribution "5.1.14"]
                 [org.slf4j/slf4j-simple "1.7.28"]]
  :main ^:skip-aot org.w3id.http
  :profiles {:uberjar {:aot :all}
             :dev {:dependencies [[org.clojure/test.check "0.10.0"]]}})
