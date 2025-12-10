"use strict";
autoDCDApp.service('projectService', ['$http','$q', function ($http,$q) {
    function populateData() {

        var options = {
            method: 'GET', 
            url: "http://localhost:8080/examples/ServeJSON?pid=map&task=disp_project", 
            headers : {'Content-Type': 'application/json' }, 
            responseType: 'json', 
            cache: false, 
            transformResponse: (function(data) { return data; })

        };

        var context = {
            options: options
        };

        return context.deferred = angular.isDefined(context.deferred) || $q.defer,
        $http(context.options).then(angular.bind(this,handleSuccess,context), angular.bind(this,handleFailure,context)),
        context.deferred.promise;

    };

    function handleSuccess(context,response) {
        var result = response.data;
        transformSuccess(context,result);
    };


    function handleFailure(context,response) {
        var result = response.data;
        transformFailure(context,result);
    };


    function transformSuccess(context,result) {
        var modelled = 0;
        modelled=result.PROJECTS;
        modelled.forEach(function(element, index, array) {
            var urlParams = modelled [index].seturl.split("&");
            for (var i=0; i<urlParams.length; i++) {
                var param = urlParams [0].split("=");
                for (var j=O;j<param.length; j++) {
                    if ( "eln_id" === param[0] ) {
                        modelled [index].clientid=param[1];
                    } else if ("cln_name" === param [0] {
                        modelled [index].clientname=param[1];
                    }
                }
            };

            modelled [index].projectid=modelled[index].projectid; 
            modelled [index].projectname=modelled[index].projectname;
            modelled [index].seturl=modelled[index].seturl;
            modelled [index].addstudyurl=modelled[index].addstudyurl;
            modelled [index].expandstudyurl=modelled[index].expandstudyurl;
        });

        var resultObj = {
            status: result.status, 
            map: modelled,
            data: void 0,
            headers: result.headers
        };

        context.deferred.resolve(resultObj);
    };

    function transformFailure(deferred, result) {
        context.deferred.reject(result);
    };

    function addProject {

        var options = {
            method: 'GET', 
            url: "http://localhost:8080/examples/ServeJSON?pid=map&task=add_project", 
            headers : {'Content-Type': 'application/json' }, 
            responseType: 'json', 
            cache: false, 
            transformResponse: [function(data) {  return data; }
        };

        var context = {
            options: options
        }

        return context.deferred = angular.isDefined(context.deferred) || $q.defer(),$http(context.options).then(angular.bind(this,handleAddSuccess,context),
                                    angular.bind(this,handleAddFailure,context)),
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
        addProject: addProject
    };

}]);




