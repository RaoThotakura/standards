"use strict";
autoDCDApp.service('jobstatusService', ['$http','$q', '$filter', function ($http,$q,$filter) {

    function populateData() {
        var options = {
            method: 'GET', 
            url: 'http://localhost:8080/examples/ServeJSON?pid=map&task=disp_job_status', 
            headers: {'Content-Type': 'application/json'}, 
            responseType: 'json',
            cache: false,
            transformResponse: [function(data) { return data }]
        };

        var context = {
            options: options
        };

        return context.deferred = angular.isDefined(context.deferred) || $q.defer(),
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
        var modelled = [];
        modelled=result.STATUS;

        modelled.forEach(function(element, index, array) {
            modelled [index].statld= modelled[index].statld;
            modelled [index].jobld= modelled[index].jobld;
            modelled[index].domain= modelled[index].domain;
            modelled [index].action= modelled[index].action;
            modelled [index].status= modelled[index].status;
            modelled[index].remark= modelled[index].remark;
            modelled[index].outPath= modelled[index].outPath;

            modelled[index].outMessage= modelled[index].outMessage;
            modelled[index].numRows= modelled[index].numRows;
            modelled [index].dbUser= modelled[index].dbUser;
            modelled[index].osUser= modelled[index].osUser;
            modelled [index].appUser= modelled[index].appUser;
            modelled [index].startTime=$filter('date') (modelled[index].startTime, 'MM/dd/yyyy : hh:mm');
            modelled [index].endTime=$filter('date') (modelled [index].endTime, 'MM/dd/yyy');
                        
        });

        var resultObj = {
            status: result.status, 
            map: modelled, 
            data: void 0,
            headers: result.headers
        };

        context.deferred.resolve(resultObj);
    };

    function transformFailure(deferred,result) { 
        context.deferred.reject(result);
    };

    return {
        populateData: populateData
    };
}]);
