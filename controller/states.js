"use strict";
autoDCDApp.config(function routeConfig($stateProvider, $urlRouterProvider) {

    $urlRouterProvider.otherwise('/home');

    $stateProvider
        .state('showclient', { url: '/showclient', templateUrl: 'show_client_view.html' })
        .state('addclient', { url: '/addclient', templateUrl: 'add_client_view.html' })
        .state('disphelp', { url: '/disphelp', templateUrl: 'display_help_view.html' })

        .state('expandclient_1_0', {
            url: '/expandelient', 
            templateUrl: 'expand_client_view_1_0.html', 
            controller: 'clientController', 
            controllerAs: 'ctrl', 
            resolve: {
                expanded_client : function () { return { value: true }; } 
            }
        })

        .state('setupclient', {
            parent: 'expandclient_1_0',
            url: '/setupclient/:ClientID/:ClientName', 
            templateUrl: 'login_inside_1_0.html', 
            controller: 'loginController', 
            controllerAs: 'ctrl',
            resolve: {
                set_client : function () {
                    return { value: true };
                },
                Client: ['$stateParams', function($stateParams) {
                    return { id: $stateParams.ClientID,value: $stateParams.ClientName };
                }],
                setproject : function () { return { value: false } }, 

                project: ['$stateParams', function($stateParams) {
                    return { id: $stateParams.projectID, value: $stateParams.projectName };  
                }],
                set_study : function () { return { value: false } }
            }
        })
        .state('displogview_1_0',{
            url: '/displogmodal',
            templateUrl: 'expand_joblog_view_1_0.html',
            controller: 'LogController',
            controllerAs: 'ctrl',
            resolve: {
                expanded_log: function() {
                    return { value: true }
                }
            }
        })
        .state('expandjob_1_0', {
            parent: 'expandlist_1_0', 
            url: '/expandjob/:clientID/:clientName/:projectID/:projectName/:studyID/:studyName/listID',
            templateUrl: 'expand_job_view_1_0.html', 
            controller: 'jobController', 
            controllerAs: 'ctrl', 
            resolve: {
                expanded_job: function() {  return { value: true};  },
                client: ['$stateParams', function($stateParams) { return { id: $stateParams.clientID,value: $stateParams.clientName }}],
                project: ['$stateParams', function($stateParams) { return { id: $stateParams.projectID,value: $stateParams.projectName }}],
                study: ['$stateParams', function($stateParams) { return { id: $stateParams.studyID,value: $stateParams.studyName }; }],
                list: ['$stateParams', function($stateParams) { return { id: $stateParams.listID}; }]
            }
        })
        .state('checkjobstatus_1_0', {
            parent: 'expandjob_1_0', 
            url: '/checkjobstatus_1_0/:jobID'
        })
        .state('addstudy_1_0', {
            parent: 'expandproject_1_0', 
            url: 'addstudy/:clientID/:clientName/:projectID/:projectName',
            templateUrl: 'add_study_module_1_0.html',
            controller: 'studyController', 
            controllerAs: 'ctrl',
            resolve: {
                expanded_study: function () { return { value: true }; },
                client: ['$stateParams', function($stateParams) { return { id: $stateParams.clientID,value: $stateParams.clientName }; }],
                project: ['$stateParams', function($stateParams) { return { id: $stateParams.projectID,value: $stateParams.projectName }; }]
            }
        })
        .state('expandlist_1_0', {
            parent: 'expandstudy_1_0',
            url: '/expandlist/:clientID/:clientName/:projectID/:projectName/:studyID/:studyName', 
            templateUrl: 'expand_list_view_1_0.html', 
            controller: 'listController', 
            controllerAs: 'ctrl', 
            resolve: {
                expanded_list: function () { return { value: true }; },
                client: ['$stateParams', function($stateParams) {
                    return { 
                        id: $stateParams.clientID,
                        value: $stateParams.clientName 
                    }
                    }],
                project: ['$stateParams', function($stateParams) {
                        return { 
                            id: $stateParams.projectID,
                            value: $stateParams.projectName 
                        }
                    }],
                study: ['$stateParams', function($stateParams) {
                        return { 
                            id: $stateParams.studyID, 
                            value: $stateParams.studyName 
                        }
                    }]
                }
            })          
            .state('addlist_1_0', {
                parent: 'expandstudy_1_0', 
                url: '/addlist/:clientID/:clientName/:projectID/:projectName/:studyID/:studyName', 
                templateUrl: 'add_list_module_1_0.html', 
                controller: 'listController',
                controllerAs: 'ctrl', 
                resolve: {
                    expanded_list: function () { return { value: true }; },
                    client: ['$stateParams', function($stateParam) {
                        return {
                            id: $stateParams.clientID,
                            value: $stateParams.clientName
                        }
                    }],
                    project: ['$stateParams', function($stateParams) {
                        return { 
                            id: $stateParams.projectID, 
                            value: $stateParams.projectName 
                        };
                    }],
                    study: ['$stateParams', function($stateParams){
                        return { 
                            id: $stateParams.studyID,
                            value: $stateParams.studyName 
                        };
                    }]
                }
            })
            .state('expandproject_1_0', {
                parent: 'expandclient_1_0',
                url: 'expandproject/:ClientID/:ClientName', 
                templateUrl: 'expand_project_view_1_0.html', 
                controller: 'projectController', 
                controllerAs: 'ctrl',
                resolve: {
                    expanded_project : function () { return { value: true } },
                    Client: ['$stateParams', function($stateParams) {
                        return {
                                id: $stateParams.ClientID,
                                value: $stateParams.ClientName
                            }
                    }]
                }
            })
            .state('addproject_1_0',{
                parent: 'expandclient_1_0', 
                url: '/addproject/:ClientID/:ClientName', 
                templateUrl: 'add_project_module_1_0.html', 
                controller: 'projectController', 
                controllerAs: 'ctrl',
                resolve: {
                    expanded_project: function () {return { value: true}; },
                    Client: ['$stateParams', function($stateParams) {
                        return {
                            id: $stateParams.ClientID, 
                            value: $stateParams.ClientName 
                        };
                    }]
                }
            })
            .state('expandstudy_1_0', {
                parent: 'expandproject_1_0',
                url: '/expandstudy/:clientID/:clientName/:projectID/:projectName',
                templateUrl: 'expand_study_view_1_0.html', 
                controller: 'studyController', 
                controllerAs: 'ctrl',
                resolve: {
                    expanded_study: function() {return { value: true };  },
                    client: ['$stateParams', function($stateParams) {  return { id: $stateParams.clientID,value: $stateParams.clientName } }],
                    project: ['$stateParams', function($stateParams) { return { id: $stateParams.projectID,value: SstateParams.projectName } }]
                }
            })
            .state('expandlog_1_0', {
                parent: 'displogview_1_0',
                url: '/expandlog',
                templateUrl: 'expand_log_view_1_0.html',
                controller: 'LogViewController',
                controllerAs: 'ctrl', 
                resolve: {
                    expanded_log: function() {
                        return { value: true }
                    },
                    Log: ['$stateParams', function($stateParams) {
                        return { id: $stateParams.JobID, value: $stateParams.JobName }
                    }]
                }
            })
            .state('showlog_1_0',{
                parent: 'expandlog_1_0', 
                url: '/showlogHtml/:logText',
                templateUrl: 'expand_logmesg_view_1_0.html',
                controller: 'LogMsgViewController', 
                controllerAs: 'ctrl',
                resolve: {
                    expanded_log: function () { return { value: true }; }, 
                    Log: ['$stateParams', function($stateParams) {  
                        return { value: §stateParams.logText }; 
                    }]
                }
            })
            .state('showlogSq_1_0',{
                parent: 'expandlog_1_0', 
                url: '/showlogSql/:logText',
                templateUrl: 'expand_logmesgSql_view_1_0.html',
                controller: 'LogMsgSqlViewController', 
                controllerAs: 'ctrl',
                resolve: {
                    expanded_log: function () { return { value: true } },
                    Log: ['$stateParams', function($stateParams) {
                        return { value: $stateParams.logText };
                    }]
                }
            })
            .state('addjob_1_0', {
                parent: 'expandlist_1_0',
                url: '/addjob/:clientID/:clientName/:projectID/:projectName/:studyID/:studyName/:listID',
                templateUrl: 'add_job_module_1_0.html', 
                controller: 'jobController', 
                controllerAs: 'ctrl',
                resolve: {
                    expanded_job: function () { return { value: true }; },
                    client: ['$stateParams', function($stateParams) {  return { id: $stateParams.clientID, value: $stateParams.clientName }; }],
                    project: ['$stateParams', function($stateParams) {  return { id: $stateParams.projectID, value: $stateParams.projectName }; }],
                    study: ['$stateParams', function($stateParams) {  return { id: $stateParams.studyID, value: $stateParams.studyName };}],
                    list: ['$stateParams', function($stateParams) {    return { id: $stateParams. listID }; }]
                }
            })               
});