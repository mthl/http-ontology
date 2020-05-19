(defproject http-onto "0.1.0-SNAPSHOT"
  :description "HTTP Ontology"
  :url "http://labs.nereide.fr/mthl/http"
  :license {:name "Apache-2.0"
            :url "http://www.apache.org/licenses/LICENSE-2.0"}
  :dependencies [[com.github.dgarijo/Widoco "v1.4.13"]
                 [net.sourceforge.owlapi/owlapi-distribution "5.1.14"]
                 [org.clojure/clojure "1.10.1"]
                 [org.clojure/tools.cli "1.0.194"]
                 [org.slf4j/slf4j-simple "1.7.28"]]
  :repositories [["jitpack" "https://jitpack.io"]
                 ["spring-ext" "https://repo.spring.io/ext-release-local/"]
                 ["spring-milestones" "https://repo.spring.io/milestone"]
                 ["spring-snapshots" "https://repo.spring.io/snapshot"]
                 ["maven-restlet" "https://maven.restlet.org"]]
  :main ^:skip-aot org.w3id.http
  :plugins [[lein-simpleton "1.3.0"]]
  :aliases {"serve" ["simpleton" "8000" "file" :from "doc"]}
  :profiles {:uberjar {:aot :all}
             :dev {:dependencies [[org.clojure/test.check "0.10.0"]]}})
