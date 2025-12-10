"use strict";
/**
*LogViewController
*/
autoDCDApp.controller('LogViewController', ['expanded _log', 'loginService', 'Log',
    function(expanded_log,loginService, Log) {
        var ctrl = this;
        ctrl.vm = {
            logs: [],
            expanded: false
        };

        ctrl.init = init;
        ctrl.init;

        function init() {
            ctrl.vm.expanded=expanded_log.value;
            var promise = loginService.runData('expand_log');
            promise.then (function (result) {
                ctrl.vm.logs = result.map;
            }), (function (reason) { console.log("error data: " + reason); })
        };
    }]);

