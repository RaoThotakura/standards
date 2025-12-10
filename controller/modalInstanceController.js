"use strict";
/**
*Modal InstanceController
*/ 
autoDCDApp.controller('ModalInstanceController',['$scope','params', 'heading', '$uibModalInstance', '$uibModal', 'loginService',
    function($scope,params,heading,modalInstance,modal,loginService) {
       var ctrl = this;
        ctrl.vm = {
            params : [], 
            heading:'',
            activelog: {
                expanded: false, 
                asOf: '',
                jobld: '',
                client: { id: '', value: ''},
                project: { id: '', value: ''}
            }
        };

        init();
        ctrl.runajob = runajob;

        function init() {
            ctrl.vm.params = params;
            ctrl.vm.heading = heading;
        };

        function runajob(config) {
            var label= config.heading;
            var promise = loginService.runData(config.task);
            modalInstance.close(promise);
            modalInstance.result.then(function(result) {
                ctrl.vm.logs = result.map;
                var modalInstance = modal.open({
                    animation: true,
                    templateUrl: 'log_view_modal.html',
                    controller: 'ModalInstanceController', 
                    controllerAs: 'ctrl', 
                    size: 'sm',
                    resolve: {
                        params: function () { return ctrl.vm. logs; }, 
                        heading: function () { return label; }
                    }
            });
        }), (function (reason) { console.log("error data: " + reason); });
    }]);
 
