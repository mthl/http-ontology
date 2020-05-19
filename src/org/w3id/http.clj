;;; http.clj -- HTTP ontology tooling
;;; Copyright © 2020 Mathieu Lirzin <mathieu.lirzin@nereide.fr>
;;;
;;; Licensed under the Apache License, Version 2.0 (the "License");
;;; you may not use this file except in compliance with the License.
;;; You may obtain a copy of the License at
;;;
;;;     http://www.apache.org/licenses/LICENSE-2.0
;;;
;;; Unless required by applicable law or agreed to in writing, software
;;; distributed under the License is distributed on an "AS IS" BASIS,
;;; WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
;;; See the License for the specific language governing permissions and
;;; limitations under the License.

(ns org.w3id.http
  (:gen-class)
  (:require
   [clojure.java.io :as io]
   [clojure.string :as str]
   [clojure.tools.cli :as cli])
  (:import
   [java.nio.file LinkOption attribute.FileAttribute Files]
   [org.semanticweb.owlapi profiles.Profiles model.IRI apibinding.OWLManager]
   widoco.gui.GuiController))

(defn create [f]
  (let [uri (.toURI (io/file f))
        m (OWLManager/createOWLOntologyManager)]
    (.loadOntology m (IRI/create uri))))

(defn check [o]
  (let [report (.checkOntology Profiles/OWL2_DL o)
        violations (.getViolations report)]
    (map str violations)))

(defn verify []
  (let [onto (create "http.ttl")
        violations (check onto)]
    (if (empty? violations)
      (System/exit 0)
      (do
        (run! println violations)
        (System/exit 1)))))

(defn strln
  "Join strings by inserting a newline character between them."
  [& strings]
  (str/join \newline strings))

(defn help
  "Return the string representation of --help output from   "
  [summary]
  (strln
   "Usage: http-onto [OPTION]... {COMMAND}"
   ""
   summary
   ""
   "COMMAND must be one of the sub-commands listed below:"
   "  gendoc   Generate documentation with Widoco"
   "  check    Check ontology with OWL API"
   ""
   "Report bugs to: <https://labs.nereide.fr/mthl/http/issues>."
   "HTTP Ontology home page: <https://labs.nereide.fr/mthl/http>."))

(def version
  (strln
   "http-onto (HTTP Ontology) 0.1.0-SNAPSHOT"
   "Copyright © 2020 the HTTP Ontology authors."
   "License Apache-2.0: <http://www.apache.org/licenses/LICENSE-2.0>"
   "This is free software: you are free to change and redistribute it."
   "There is NO WARRANTY, to the extent permitted by law."))

(def cli-options
  [["-h" "--help" "display this help and exit"]
   ["-V" "--version" "output version information and exit"]])

(defn exit [status msg]
  (println msg)
  (System/exit status))

(def widoco-args
  ["-ontFile" "http.ttl"
   "-outFolder" "doc"
   "-getOntologyMetadata"
   "-lang" "en"
   "-confFile" "doc.properties"
   "-webVowl"
   "-displayDirectImportsOnly"
   "-excludeIntroduction"
   "-rewriteAll"])

(defn -main [& args]
  (let [opts (cli/parse-opts args cli-options)
        {:keys [options arguments errors summary]} opts
        [command & _] arguments]
    (cond
      (:help options) (exit 0 (help summary))
      (:version options) (exit 0 version)
      (some? errors) (exit 1 (apply strln errors))
      :else
      (case command
        "check" (verify)
        "gendoc" (let [doc (.toPath (io/file "doc"))
                       idx (.toPath (io/file "doc" "index.html"))
                       idx-en (.toPath (io/file "index-en.html"))
                       attrs (make-array FileAttribute 0)
                       linkopts (make-array LinkOption 0)]
                   (or (Files/isDirectory doc linkopts)
                       (Files/createDirectory doc attrs))
                   (GuiController/main (into-array String widoco-args))
                   (or (Files/isSymbolicLink idx)
                       (Files/createSymbolicLink idx idx-en attrs)))
        nil (exit 1 (help summary))
        (exit 1 (str "No command corresponds to '" command "'"))))))
