"use strict";
/**
*LogViewController
*/
autoDCDApp.controller('LogMsgSqlViewController',['expanded _log', 'loginService', 'Log',
    function(expanded_log, loginService,Log) {
        var ctrl = this;
        ctrl.vm = {
            logs: [], 
            expanded: false,
            logText:'',
        };
        ctrl.init = init();
        ctrl.init;

        function init() {
            ctrl.vm.expanded=expanded_log.value;
            ctrl.vm.logText=Log.value;
            var promise = loginService.runData('expand_logmesgSql');
            promise.then(function(result){
            ctrl.vm.logs = result.map;
            }), (function (reason) { console.log("error data:" + reason); });
        };

    }]);


