(ns modern-cljs.remotes

  (:require [modern-cljs.core :refer [handler]]
            [compojure.handler :refer [site]]
            [shoreleave.middleware.rpc :refer [defremote wrap-rpc]]
            [modern-cljs.login :refer [validate-email validate-pwd]]
            ))
(defremote decorate-logon [email]

  (-> (if (and (validate-email email) (validate-pwd password))
(str email " and " password "passed validation"))))

(def app (-> (var handler)

             (wrap-rpc)
             (site)))