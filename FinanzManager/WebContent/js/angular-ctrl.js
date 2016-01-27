var app = angular.module("Finanz", ['ngCookies']);

app.controller("LoginCtrl", function($scope, $http, $rootScope, $cookieStore, $location, $anchorScroll){
	var baseUrl = "http://localhost:8080/finanz/api/resource/graph";
	$scope.loggedIn = false;
	// after facebook login
	if ($location.absUrl().indexOf("code=") > 0){
		var code = $location.absUrl().split("code=")[1];
		$http.get(baseUrl + "/" +code)
		.then(function success(response){
			$cookieStore.put("user", response.data.name);
			$cookieStore.put("user_id", response.data.id);
			$cookieStore.put("code", code);
			$scope.loggedIn = true;
			location.href = $location.absUrl().split("?code")[0];
		});
	} 
	// validate login
	else if ($cookieStore.get("user") != undefined){
		var code = $cookieStore.get('code');
		// check code
		if (code != undefined){
			console.log("check");
			$http.get(baseUrl + "/" +code)
			.then(function success(response){
				if (!response.data.success){
					$cookieStore.remove('code');
					$cookieStore.remove('user');
					$cookieStore.remove("user_id");
					$('#current_user').html('');
				} else {
					$('#current_user').append($cookieStore.get('user'));
					$scope.loggedIn = true;
				}
			});
		}
		
	}
	
	$scope.logout = function(){
		$cookieStore.remove('user');
		$cookieStore.remove('code');
		$('#current_user').html('');
		$scope.loggedIn = false;
		$rootScope.$emit("reset", {});
	}
	
	$scope.facebookLogin = function(){
		$http.get('http://localhost:8080/finanz/api/resource/facebook/login')
		.then(function success(response){
			location.href=response.data.url;
		});
	}
	
	$scope.toAnchor = function(pos){
			$location.hash(pos);
	}
	
});

app.controller("FinanceCtrl", function($scope, $http, $rootScope, $cookieStore){
	var baseUrlTrans = "http://localhost:8080/finanz/api/resource/transaction";
	var baseUrlAcc = 'http://localhost:8080/finanz/api/resource/account';
	
	// get accounts
	$scope.getAccounts = function(){
		$scope.accounts = null;
		if ($cookieStore.get('code') != undefined){
			$scope.account_tpyes = ["Giro", "Credit"];
			var reqUrlAcc = baseUrlAcc + "/" + $cookieStore.get('code');
			$http.get(reqUrlAcc)
			.then(function success(response){
				$scope.accounts = response.data;
				$scope.sum = 0;
				var summe = 0;
				for (var i = 0; i < $scope.accounts.length; i++){
					summe = summe + $scope.accounts[i].balance;
					console.log(summe);
				}
				$scope.sum = summe;
			});
		}
	}
	
	// get all transactions
	$scope.getTransactions = function(){
		$scope.transactions = null;
		var reqUrlTrans = baseUrlTrans + "/" + $cookieStore.get('code');
		$http.get(reqUrlTrans)
		.then(function success(response){
			$scope.transactions = response.data;
			$scope.getAccounts();
		});
	}
	
	// get transactions (each call also get accounts -> update balance)
	$scope.getTransactions();
	
	$scope.createTransaction = function(transaction){
		if ($cookieStore.get("code") != undefined){
			transaction.code = $cookieStore.get("code");
			console.log(JSON.stringify(transaction));
			$http.post(baseUrlTrans, JSON.stringify(transaction))
			.then(function success(response){
				console.log(response.data);
				$scope.getTransactions();
			});
		}
		else {
			console.log("LOGIN!!!");
		}
	}
	
	$scope.createAccount = function(account){
		if ($cookieStore.get("code") != undefined){
			account.code = $cookieStore.get("code");
			console.log(JSON.stringify(account));
			$http.post(baseUrlAcc, JSON.stringify(account))
			.then(function success(response){
				console.log(response.data);
				$scope.getTransactions();	// also accounts
			});
		}
		else {
			console.log("LOGIN!!!");
		}
	}
	
	// reset data after login
	$scope.logoutReset = function(){
		$scope.accounts = null;
		$scope.transactions = null;
		$scope.sum = 0;
		$cookieStore.remove("user_id");
		$cookieStore.remove("code");
		$cookieStore.remove("user");
	}
	
	// init listener for reset
	$rootScope.$on("reset", function(){
        $scope.logoutReset();
     });
});


