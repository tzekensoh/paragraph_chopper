#!/usr/bin/env bb

(require '[org.httpkit.server :as server]
         '[clojure.string :as str]
         '[ring.util.codec :as codec]
         '[babashka.fs :as fs])

(defn remove-punctuation [s]
  (str/replace s #"[[:punct:]\u3000-\u303F\uFF00-\uFFEF\s\r\n\t]" ""))

(defn utf8-chunks [s n]
  (->> (vec (seq s))
       (partition-all n)
       (map #(apply str %))))

(defn parse-body [req]
  (let [raw-body (slurp (:body req))
        decoded (codec/form-decode raw-body)]
    (get decoded "text" "")))

(defn serve-form []
  (let [path "public/form.html"]
    (if (fs/exists? path)
      {:status 200
       :headers {"Content-Type" "text/html; charset=UTF-8"}
       :body (slurp path)}
      {:status 404 :body "Form not found."})))

(defn result-html [chunks]
  (str "<!DOCTYPE html><html><head><meta charset='UTF-8'><title>Results</title>"
       "<style>input[type='text']{width:60%;margin:5px;}button{margin-left:10px;}</style>"
       "</head><body><h2>Chunks</h2><form method='POST'>"
       (apply str (map (fn [c]
                         (str "<input type='text' value='" c "' readonly>"
                              "<button onclick='copy(this)' type='button'>Copy</button><br>"))
                       chunks))
       "<br><a href='/'>Back</a>"
       "</form><script>
        function copy(btn){
          const input = btn.previousElementSibling;
          input.select();
          document.execCommand('copy');
          alert('Copied: ' + input.value);
        }
        </script></body></html>"))

(defn handler [req]
  (case (:request-method req)
    :get (serve-form)
    :post (let [text (parse-body req)
                cleaned (remove-punctuation text)
                chunks (utf8-chunks cleaned 10)]
            {:status 200
             :headers {"Content-Type" "text/html; charset=UTF-8"}
             :body (result-html chunks)})
    {:status 405 :body "Method Not Allowed"}))

(println "Server running at http://localhost:8080/")
(server/run-server handler {:port 8080})
@(promise)
