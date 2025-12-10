/**
*StudyController
*/ 
"use strict";
autoDCDApp.controller('studyController', ['$scope', '$rootScope', 'studyService', 'expanded_study','client','project', '$uibModal',
     function ($scope, $rootScope,studyService, expanded_study, client, project,$uibModal) {
        var ctrl = this;
        ctrl.vm = {
            studies: [],
            client: {id: '',value: ''},
            project: {id: '', value: ''},
            expanded : false
        };

        init();

        ctrl.setupstudy = setupstudy;
        ctrl.addstudy = addstudy;

        function init () {
            var studies = [];
            ctrl.vm.expanded = expanded_study.value;
            ctrl.vm.client.id = client.id;
            ctrl.vm.client.value = client.value;
            ctrl.vm.project.id = project.id;
            ctrl.vm.project.value = project.value;
            var promise = studyService.populateData(disp_study);
            promise.then(function(result) {
                ctrl.vm.studies = result.map;
            }), (function (reason){
                console.log("error data: " + reason);
            });
        };
        function setupstudy(config) {
            $rootScope.studyConfig = config;
        };
        function addstudy(task) {
            var label='';
            var promise = studyService.addStudy(task);
            promise.then(function(result) {
                ctrl.vm.logs = result.map;
                var modalInstance = $uibModal.open({
                    animation: true, 
                    templateUrl: 'log_view_modal.html',
                    controller: 'ModalInstanceController', 
                    controllerAs: 'ctrl',
                })
            }), (function (reason) {
                console.log("error data: " + reason);
            });  
        
        };
     }]);


