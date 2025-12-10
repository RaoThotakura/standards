"use strict";
/**
*loginController
*/ 
autoDCDApp.controller('loginController',
    ['$rootScope','$scope', 'loginService', 'SuibModal', function($rootScope,scope,loginService,$uibModal) {

        var ctrl = this;
        ctrl.vm = {
            client: {id: '',value: '', setup:true}, 
            project: {id: '', value: '', setup:true},
            study: {id: '',value: '',setup:true}
        };
        ctrl.init = init;
        ctrl.init;
        ctrl.launchjobmodal = launchjobmodal;
        ctrl.launchhelpmodal = launchhelpmodal;
        ctrl.launchfilemodal = launchfilemodal;
        ctrl.launchactionmodal = launchactionmodal;
        ctrl.setaclient = setaclient;
        ctrl.setaproject = setaproject;
        ctrl.setastudy = setastudy;

        function init() {
            $rootScope.listConfig = {ShowForm : true};
            ctrl.vm.client.setup = true || $rootScope.listConfig.ShowForm;
            ctrl.vm.project.setup = true || $rootScope.listConfig.ShowForm;
            ctrl.vm.study.setup = true || $rootScope.listConfig.ShowForm;
        };

        function launchjobmodal(config) {

            var listId = config.listId;
            var label=config.heading;

            var modalInstance = $uibModal.open({
                animation: true, 
                templateUrl: config.filename+'_modal_1_0.html',
                controller: 'ModalInstanceController', 
                controllerAs: 'ctrl', 
                size: config.size, 
                resolve: {
                    params: function() { return listid; }, 
                    heading: function() { return label; } 
                }
            });
        };

        function launchactionmodal(config) {
            var modalInstance = $uibModal.open({
                animation: true, 
                templateUrl: config.filename+'_modal_1_0.html',
                controller: 'ActionModalController', 
                controllerAs: 'ctrl',
                size: config.size, 
                resolve: {
                    config: function () { return config; }
                }
            });
        };


        function launchhelpmodal(config) {
            var label=config.heading;
            var modalInstance = $uibModal.open({
                animation: true, 
                templateUrl: config.filename+'_help.html', 
                controller: 'HelpModalController',
                controllerAs: 'ctrl', 
                size: config.size,
                resolve: {
                    heading: function () { return label; }
                }
            });
        };

        function launchfilemodal(config) {
            var listId = config.listId;
            var label=config.heading;
            var modalInstance = $uibModal.open({
                animation: true, 
                templateUrl: config.filename+'_modal_1_0.html',
                controller: 'uploadController', 
                controllerAs: 'ctrl', 
                size: config.size, 
                resolve:
                    params: function () { return listid; }, 
                    heading: function () { return label; },
                    task: function () { return 'load a spec'; }
            });
        };
        function setaclient(task) {
            var promise = loginService.runData(task);

            promise.then(function(result) {
                ctrl.vm.client.value = result.map.client;
            }), (function (reason){
                console.log("error data: " + reason);
            });
            ctrl.vm.client.setup=false;
        };

        function setaproject(task) {
            var promise = loginService.runData(task);

            promise.then(function(result) {
                ctrl.vm.project.value = result.map.project;
            }),(function (reason){
                console.log ("error data: " + reason);
            });
            ctrl.vm.project.setup=false;
        };

        function setastudy (task) {
            var promise = loginService.runData(task);

            promise.then(function(result) {
                ctrl.vm.study.value = result.map.study;
            }),(function (reason) {
                    console.log("error data: " + reason);
            });
            ctrl.vm.study.setup=false;
        };
    }]);