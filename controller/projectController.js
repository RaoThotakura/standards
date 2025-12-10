"use strict";
autoDCDApp.controller('projectController', ['$scope', '$rootScope', 'projectService', '$filter', 'expanded_project', 'Client', '$uibModal',
     function($scope, $rootScope, projectService,$filter,expanded_project, Client,$uibModal) {

        var ctrl = this;
        ctrl.vm = {
            projccts: [],
            client: {id: '',value: '' }, 
            expanded : false, 
            logs : []
        };

        ctrl.addproject = addproject;
        ctrl.setupproject = setupproject;

        function init() {
            var projects = [];
            ctrl.vm.expanded = expanded_project.value;
            ctrl.vm.client.id = Client.id;
            ctrl.vm.client.value = Client.value;
            var promise = projectService.populateData();
            promise.then(function(result) {
                ctrl.vm.projects = result.map;
            }), (function (reason) {
                console.log("error data: " + reason);
            });

        };

        function setupproject(config) {
            $rootScope.projectConfig = config;
        };

        function addproject() {
            var label= '';
            var promise = projectService.addProject();
            promise.then(function(result){
                ctrl.vm.logs = result.map;
                var modalInstance = $uibModal.open({
                    animation: true, 
                    templateUrl: 'log_view_modal.html',
                    controller: 'ModalInstanceController', 
                    controllerAs: 'ctrl', 
                    size: 'sm', 
                    resolve: {
                        params: function() { return ctrl.vm.logs; },
                        heading: function () { return label = "Add a project log"; }            
                    }
                })
            }), (function (reason) {})
        };

     }]);
    