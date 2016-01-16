var app = angular.module("task9",[]);

app.controller("UserCtrl", function($scope, $http, $rootScope) {
	$scope.ages = [];
	for (var i = 1; i < 100; i++){
		$scope.ages.push(i);
	}
	$scope.employments = ['Student', 'Builder', 'Office Worker', 'Architect', 'Athlete', 'Artist', 'Engineer', 'Accountant', 'Business Person'];
	$scope.countries = ['Austria', 'Germany', 'Italia', 'Spain', 'France', 'Croatia'];
	$scope.baseUrl = "http://localhost:8080/task9/res/api/";
	console.log($scope.baseUrl);
	
	$scope.reg = function(user){
	$http.post($scope.baseUrl + "person", JSON.stringify(user))
		.then(function success(response) {
			console.log(response.data);
		});
	};
});


