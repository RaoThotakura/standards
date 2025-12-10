"use strict";
autoDCDApp.service('clientService', ['$http','$q', function ($http,$q) {
    var clientConfig= {};
    function populateData () {
        var options = {
            method: 'GET',
            url: 'http://localhost:8080/examples/ServeJSON?pid=map&task=disp_client', 
            headers : {'Content-Type': 'application/json'},
            responseType: 'json', 
            cache: false, 
            transformResponse: [function(data) { return data;}]
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
        var modelled = 0;
        modelled=result. CLIENTS;
        for (var i=0; i< modelled.length; i++) {
            modelled [i].clientid=modelled [i].clientid; 
            modelled [i].clientname=modelled [i].clientname;
            modelled [i].seturl=modelled[i].seturl;
            modelled [i].addprojecturl=modelled [i].addprojecturl;
            modelled [i].expandprojecturl=modelled[i].expandprojecturl;
        };

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

    function setClientInfo(config) {
        clientConfig = config;
    };

    function getClientInfo {
        return clientConfig;
    };

    return {
        populateData: populateData, 
        setClientinfo: setClientinfo,
        getClientinfo: getClientInfo
    };

}]);