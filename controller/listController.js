/**
* ListController
*/ 
"use strict";
autoDCDApp.controller('listController',['$rootScope', '$scope', 'listService', 'expanded _list','client', 'project','study', '$uibModal',
    function($rootScope,$scope, listService, expanded_list,client,project,study,SuibModal) {
        var ctrl = this;
        ctrl.vm = {
            lists : [],
            client: {id: '', value: ''}, 
            project: {id: '', value: ''}, 
            study: {id: '', value: ''}, 
            expanded: false
        };

        init();

        ctrl.setuplist = setuplist;
        ctrl.addlist = addlist;

        function init () {
            var lists = 0;
            ctrl.vm.expanded = expanded_list.value;
            ctrl.vm.client.id = client.id;
            ctrl.vm.client.value = client.value;
            ctrl.vm.project.id = project.id;
            ctrl.vm.project.value = project.value;
            ctrl.vm.study.id = study.id;
            ctrl.vm.study.value = study.value;
            var promise = listService.populateData('disp_list');
            promise.then(function(result){
                ctrl.vm.lists = result.map;
            }), (function (reason){ console.log("error data: " + reason); });
        };


        function setuplist(config) {
            $rootScope.listConfig = config;
        };

        function addlist(task) {
            var label="";
            var promise = listService.addList(task);
            promise.then(function(result) { 
                ctrl.vm.logs = result.map;

                var modalInstance = $uibModal.open({
                    animation: true, 
                    templateUrl: 'log_view_modal.html',
                    controller: 'ModalInstanceController', 
                    controllerAs: 'ctrl', 
                    size: 'sm',
                    resolve: {
                        params: function () { return ctrl.vm.logs; },
                        heading: function () { return label = "Add a List log"; }
                    
                    },
                });
            }),(function (reason) {
                console.log("error data: " + reason);
            });              
        };
        
    }]);


