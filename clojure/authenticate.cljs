(ns modern-cljs.login)

(def ^:dynamic password-re #"^(?=.*d).{4,8}$")
(def ^:dynamic email-re #"^[_a-z0-9-]+(.[_a-z0-9-]+)*@[a-z0-9-]+(.[a-z0-9-]+)*(.[a-z]{2,4})$")

(declare validate-email validate-pwd)

(defn authenticate-user [email password]
(if (or (empty? email) (empty? password))
(str "Complete the login form") (if (and (validate-email email) (validate-pwd password))

(str email " and " password "passed validation"))))
(defn validate-email [email]
(if (re-matches email-re email) true))
(defn validate-pwd [password]
(if (re-matches password-re password) true))