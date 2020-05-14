;;; http.clj -- Profile verification of HTTP ontology
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
   [clojure.java.io :as io])
  (:import
   [org.semanticweb.owlapi
    profiles.Profiles
    model.IRI
    apibinding.OWLManager]))

(defn create [f]
  (let [uri (.toURI (io/file f))
        m (OWLManager/createOWLOntologyManager)]
    (.loadOntology m (IRI/create uri))))

(defn check [o]
  (let [report (.checkOntology Profiles/OWL2_DL o)
        violations (.getViolations report)]
    (map str violations)))

(defn -main [& args]
  (let [onto (create "http.ttl")
        violations (check onto)]
    (if (empty? violations)
      (System/exit 0)
      (do
        (run! println violations)
        (System/exit 1)))))
