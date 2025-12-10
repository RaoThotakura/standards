/**
* FileUploadController
*/
autoDCDApp.controller('uploadController', ['$scope', 'params', 'heading','task','fileUploadService', 'loginService', '$uibModalInstance', '$uibModal', 
    function ($scope, params,heading,task, fileUploadService, loginService,modallnstance,modal) {

        var ctrl = this;
        ctrl.vm = {
            spec: {
                pid : 'map',
                task : 'upload _file',
                no_dispform : '1',
                guid : '4774:sthotakura:20160512.084736',
                study_id : '1312',
                list_id : '1595'
            },
            params : [],
            logs : [],
            heading : ''
        };
  
        ctrl.init = init();
        ctrl.init;
        ctrl.uploadFile = uploadFile;

        function init() {
            ctrl.vm.spec = fileUploadService.prefillCommons();
            ctrl.vm.params = params;
            ctrl.vm.heading = heading;
        };

        function uploadFile(config) {
            var file = $scope.spec_file_name;
            var dest = $scope.destination;
            var uploadUrl = "http://localhost:8080/examples/FileUploadServlet/upload";
            var label= config.heading;
            var promise = fileUploadService.uploadFileToUrl(file,dest,uploadUrl);
            modalInstance.close(promise);
            modalInstance.result.then(function(result) {
                    ctrl.vm.logs = result.map;
                    var modalInstance = modal.open({
                        animation: true, 
                        templateUrl: 'log_view_modal.html',
                        controller: 'ModalInstanceController', 
                        controllerAs: 'ctrl', 
                        size: 'md',
                        resolve: {
                            params: function () { return ctrl.vm.logs; }, 
                            heading: function () { return label; }
                        }
                    });
                }),(function (reason){
                    console.log("error data: " + reason;
                });
        };

    }]);



