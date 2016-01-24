var app = angular.module("Finanz", ['ngCookies']);

app.controller("LoginCtrl", function($scope, $http, $rootScope, $cookieStore){
	$scope.facebookLogin = function(){
		$http.get('http://localhost:8080/finanz/api/resource/facebook/login')
		.then(function success(response){
			location.href=response.data.url;
		});
	}	
});

app.controller("OverviewCtrl", function($scope, $http, $rootScope, $cookieStore){
});

app.controller("TransactionCtrl", function($scope, $http, $rootScope, $cookieStore){
});

app.controller("AccountCtrl", function($scope, $http, $rootScope, $cookieStore){
	var baseUrl = 'http://localhost:8080/finanz/api/resource/account';
	$scope.accounts = null;
	$scope.account_tpyes = ["Giro", "Credit"];
	$http.get(baseUrl)
	.then(function success(response){
		console.log("get");
		$scope.accounts = response.data;
	});
	
	$scope.createAccount = function(account){
		console.log(JSON.stringify(account));
		$http.post(baseUrl, JSON.stringify(account))
		.then(function success(response){
			console.log(response.data);
		});
	}
});


