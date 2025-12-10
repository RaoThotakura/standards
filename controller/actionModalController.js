"use strict;"
/**
*ActionModal Controller
*/ 
autoDCDApp.controller('ActionModalController',['Sscope, SuibModalInstance', 'SuibModal',' loginService', 'config',
     function(scope,modalInstance, modal, loginService, actionConfig) { 
        var ctrl = this;
        ctrl.vm = {
            params: 0,
            heading: '',
            activelog: {
                expanded: false, 
                asOf: '',
                jobld: '',
                client: { id: '',value: ''}, 
                project: {id: '', value: ''},
                study: {id: '',value:''}
            }
        };

        init();

        ctrl.runtheaction = runtheaction;

        function init () {

            if (angular.isDefined(actionConfig.heading)) {
                ctrl.vm.heading = actionConfig.heading;
            }
            if (angular.isDefined(actionConfig.clientID)) { 
                ctrl.vm.client.id= actionConfig.clientID;
            }
            if (angular.isDefined(actionConfig.clientName)) {
                ctrl.vm.client.value= actionConfig.clientName;
            }
            if (angular.isDefined(actionConfig.projectID)) {
                ctrl.vm.project.id=actionConfig.projectID;
            }
            if (angular.isDefined(actionConfig.projectName)) {
                ctrl.vm.project.value=actionConfig.projectName;
            }
            if (angular.isDefined(actionConfig.studyID)) {
                ctrl.vm.study.id=actionConfig.studyID;
            }
            if (angular.isDefined(actionConfig.studyName)) {
                ctrl.vm.study.value=actionConfig.studyName;
            }
        };

        function runtheaction(config) {
            var label= config.heading;
            var promise = loginService.runData(config.task);
            modalInstance.close(promise);
            modalInstance.result.then (function(result) {
                ctrl.vm.logs = result.map;

                var modalinstance = modal.open({
                    animation: true,
                    templateUrl: 'log_view_modal.html',
                    controller: 'ModalInstanceController', 
                    controllerAs: 'ctrl', 
                    size: 'sm',
                    resolve: {
                        params: function () { return ctrl.vm.logs; }, 
                        heading: function () { return label; }
                    }
                });
            }), (function (reason) {
                console.log("error data : " + reason); 
            });

        };
     }]);

