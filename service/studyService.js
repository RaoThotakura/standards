"use strict";
autoDCDApp.service('studyService', ['$http','$q', function ($http,$q) {
    function populateData(task) {
        var options = {
            method: 'GET', 
            url: "http://localhost:8080/examples/ServeJSON?pid=map&task="+task, 
            headers : { 'Content-Type': 'application/json' }, 
            responseType: 'json', 
            cache: false,
            transformResponse: [function(data) {
                return data; 
            }]
        };
        var context = {
            options: options
        };
        return context.deferred = angular.isDefined(context.deferred) || $q.defer(),

        $http(context.options).then(angular.bind(this.handleSuccess,context), angular.bind(this,handleFailure,context)),
        context.deferred.promise;
    }

    function handleSuccess(context,response) {
        var result = response.data;
        transformSuccess(context,result);
    }

    function handleFailure(context,response) {
        var result = response.data;
        transformFailure(context,result);
    }

    function transformSuccess(context,result) {
        var modelled = [];
        modelled=result.STUDIES;
        modelled.forEach(function(element, index, array) {
            var urlParams = modelled[index].seturl.split("&");
            for (var i=0; i<urlParams.length; i++) {
                var param = urlParams[i].split("=");
                for (var j=0;j<param.length; j++) {
                    if ( "cln_id" === param[0]) {
                        modelled[index].clientid = param[1];
                    } else if ("cln_name" === param [0] ) {
                        modelled[index].clientname = param[1];
                    } else if ("pri_id" === param[0]) { 
                        modelled[index].prj_id = param[1];
                    } else if ("pri_name" === param[0] ) {
                        modelled [index].prj_name = param[1];
                    };
                }
            };

            modelled [index].studyid=modelled[index].studyid;
            modelled [index].studyname=modelled[index].studyname; 
            modelled [index].seturl=modelled[index].seturl;
            modelled [index].addlisturl=modelled[index].addlisturl;
            modelled [index].expandlisturl=modelled[index].expandlisturl;
                
        });

        var resultObj = {
            status: result.status,
            map: modelled, 
            data: void (),
            headers: result.headers
        };

        context.deferred.resolve(resultObj);
    };

    function transformFailure(deferred,result) {
        context.deferred.reject(result);
    };
    
    function addStudy(task) {
        var options = {
            method: 'GET', 
            url: "http://localhost:8080/examples/ServeJSON?pid=map&task="+task, 
            headers : {'Content-Type': 'application/json' }, 
            responseType: 'json', 
            cache: false,
            transformResponse: [function(data) {
                return data;
            }]
        };

        var context = {
            options: options
        };

        return context.deferred = angular.isDefined(context.deferred) || $q.defer(),

        $http(context.options).then(angular.bind(this,handleAddSuccess,context),
        angular.bind(this,handleAddFailure, context)),

        context.deferred.promise;
    };


    function handleAddSuccess(context,response) {
        var result = response.data;
        var modelled = [];
        modelled=result.LOGS;
        var resultObj = {
            status: result.status, 
            map: modelled,
            data: void 0,
            headers: result.headers
        };

        context.deferred.resolve(resultObj);
    };

    function handleAddFailure(context,response) {
        var result = response.data; 
        transformFailure(context,result);
    };

    return {
        populateData: populateData, 
        addStudy: addStudy
    };


}]);