/**
var credits = [
{'code' : 'EMATH', 'name' : 'Engineering Mathematics'},
{'code' : 'EPHY', 'name' : 'Engineering Physics'},
{'code' : 'ECHEM', 'name' : 'Engineering Chemistry'},
{'code' : 'EME', 'name' : 'Elements of Mechanical Engineering'}, 
{'code' : 'ECE', 'name' : 'Elements of Civil Engineering'},
{'code' : 'EEE', 'name' : 'Elements of Electrical Engineering'},
{'code' : 'EMC', 'name' : 'Engineering Mechanics'},
{'code' : 'EDW', 'name' : 'Engineering Drawing'},
{'code' : 'MPR', 'name' : 'Mathematics, Probability & Random Processes'},
{'code' : 'DMS', 'name' : 'Discrete Mathematics Structures'},
{'code' : 'CONM', 'name' : 'Computer Oriented Numerical Methods'},
{'code' : 'DIG', 'name' : 'Introduction to Digital Computer Design'},
{'code' : 'EDC', 'name' : 'Electronics Devices and Circuits'},
{'code' : 'PASC', 'name' : 'Introduction to Programming in Pascal'},
{'code' : 'DSC', 'name' : 'Data Structures'},
{'code' : 'NFW', 'name' : 'Networks and Fields'},
{'code' : 'PPL', 'name' : 'Principles of Programming Languages'},
{'code' : 'COR', 'name' : 'Computer Organization'},
{'code' : 'LP', 'name' : 'Language Processors (System Programming)'},
{'code' : 'MuP', 'name' : 'Introduction to Microprocessors'},
{'code' : 'LCT', 'name' : 'Linear Control Theory'},
{'code' : 'EC2', 'name' : 'Electronics Circuits-II'},
{'code' : 'DPFS', 'name' : 'Data Processing, File Structures in COBOL'},
{'code' : 'CTT', 'name' : 'Communication Theory and Techniques'},
{'code' : 'OS', 'name' : 'Operating Systems'},
{'code' : 'TCS', 'name' : 'Theory of Computer Science'},
{'code' : 'CNW', 'name' : 'Computer Networks'},
{'code' : 'DBMS', 'name' : 'Database Management Systems'},
{'code' : 'SDIC', 'name' : 'System Design Using Integrated Circuits'},
{'code' : 'AI', 'name' : 'Artificial Intelligence'},
{'code' : 'CG', 'name' : 'Computer Graphics'},
{'code' : 'SIM', 'name' : 'Design and Simulation of Analog Circuits'},
{'code' : 'EIM', 'name' : 'Engineering Economics & Industrial Management'}
];
*/

angular.module('creditListFilters', []).filter('checkmark', function() {
	return function(input) {
		return input.indexOf("Eng") ? '../../images/sais.min.png' : '../../images/rao.min.png';
	};
});

var creditServices = angular.module('creditServices', ['ngResource']);

creditServices.factory('CREDIT', ['$resource',
  function($resource) {
	  return $resource('fetch', {}, { 
	   query: {method: 'GET', params: {query:'credits.json'}, isArray:true}
	   });
  }]);

/**
var creditListApp = angular.module('creditListApp', ['creditListFilters']);

creditListApp.controller('CreditListController',
	function ($scope, $http) {
		$http.get('fetch?query=credits.json').success(function(data) { 
			$scope.credits = data.splice(0,10);
	});
	$scope.orderProp='';
});


var creditListApp = angular.module('creditListApp', ['creditListFilters','creditServices']);

creditListApp.controller('CreditListControllerII',  ['$scope','CREDIT',
	 function ($scope, CREDIT) {
		$scope.credits = CREDIT.query(); 
		$scope.orderProp = 'code';
}]);


angular.module('creditListApp', ['creditListFilters','creditServices'])
 .controller('CreditListControllerII',  ['$scope','CREDIT',function ($scope, CREDIT) {
		 $scope.credits = CREDIT.query(); 
		 $scope.orderProp = 'code';
	 }]);

*/


angular.module('creditListApp', ['creditListFilters','creditServices'])
.controller('CreditListController',
	 function ($scope, $http) {
		 $http.get('fetch?query=credits.json').success(function(data) { 
		   $scope.credits = data.splice(0,10);
		   });
		 $scope.orderProp='';
	 })
.controller('CreditListControllerII',  ['$scope','CREDIT',
	 function ($scope, CREDIT) {
		$scope.credits = CREDIT.query(); 
		$scope.orderProp = 'code';
	 }])
