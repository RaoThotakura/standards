autoDCDApp.service('fileUploadService', ['$http', '$q', function ($http, $q) {
    function prefillCommons() {
        var spec = {
            pid: 'map', 
            sel_snl : 'dis1',
            task : 'upload_file',
            no_dispform: '1',
            guid: '4774:sthotakura:20160512.084736', 
            study_id : '1312',
            list_id : '1595'
        };
        return spec;
    };
    function uploadFileToUrl(file, dest,uploadUrl){
        var specForm = new FormData();
        specForm.append('file', file);
        specForm.append('destination',dest);
        var options = {
            transformResponse: [function(data) {    return data; }]
        };
        var context = {
            options: options
        };
        return context.deferred = angular.isDefined(context.deferred) || $q.defer(),
        $http.post(uploadUrl, specForm, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined },
        }, context.options).then(angular.bind(this,handleSuccess,context), 
                                angular.bind(this,handleFailure,context)),
                                context.deferred.promise;
    };
    function handleSuccess(context,response) {
        var result = response.data;
        var modelled = [];
        modelled=result.LOGS;
        var resultObj = {
            status: result.status, 
            map: modelled, 
            data: void 0,
            headers: result.headers
        };
        context.deferred.resolve(resultObj);
    };
    function handleFailure(context,response) {
        var result = response.data;
        context.deferred.reject(result);
    };
    return {
        prefillCommons: prefillCommons, 
        uploadFileToUrl: uploadFileToUrl
    };

}]);