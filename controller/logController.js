"use strict";
/**
*LogController
*/
autoDCDApp.controller('LogController',
    ['$scope','loginService',function($scope,loginService) {
        var ctrl = this;
        ctrl.vm = {
            logs : [], 
            params: '',
            pageHeading:'',
            expanded:true
        };

        ctrl.init = init();
        ctrl.init;

        function init {
            var promise = loginService.runData('display_logfiles');
            promise.then(function(result) {
                ctrl.vm.logs = result.map;
            }), (function (reason){
                console.log("error data: " + reason);
            });
        }

    }]);


