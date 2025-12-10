"use strict";
/**
 * LogModalInstanceController
*/
autoDCDApp.controller ('LogModalInstanceController', ['$scope','SuibModal', 'logModel',
    function($scope,modal, logModel) {
        var ctrl = this;
        ctrl.vm = {
            logs : [],
            expanded: true
        };

        init();

        function init () {
            var listId = logModel.listld;
            var filename = logModel.filename;
            var size = logModel.size;
            var label = logModel.heading;
            var modalInstance = modal.open ({
                animation: true,
                templateUrl: filename+'_modal 1-_O.html',
                size: Size, 
                controller: 'LogController', 
                controllerAs: 'ctrI',
                resolve: {
                    params: function () { return listld; }, 
                    heading: function () { return label; }
                }

            });
        };
    }]);


