var app = angular.module("WebShop", ['ngCookies']);

app.service('loginService', function($cookieStore){
	return {
		current_user: function () {
			var user = {name: "", priv: ""};
			user.name = $cookieStore.get('user');
			user.priv = $cookieStore.get('priv');
			if (user.name == null){
				user.name = "guest";
				user.priv = 0;
			}
			return user;
		}, 
		set_user: function(user){
			$cookieStore.put('user', user.name);
			$cookieStore.put('priv', user.priv);
		}, 
		unset_user: function(){
			$cookieStore.remove('user');
			$cookieStore.remove('priv');
		}
	};
});

app.controller("ItemCtrl", function($scope, $http, $rootScope, loginService) {
	$scope.baseUrl = "http://localhost:8080/webshop/api/resource/item/";
	$http.get('http://localhost:8080/webshop/api/resource/item').
	success(function(data, status, headers, config) {
    	$scope.items = data;
    	$scope.oneSelected = false;
    	$rootScope.alert = false;
	});
	
	$scope.getItem = function(id){
		$scope.comment = {author: "", title1: "", message: ""};
		$scope.id = $scope.items[id].id;
		$scope.comments = null;
		$scope.url = $scope.baseUrl + $scope.id + "/comment";
		$scope.oneSelected = true;
		$scope.items = [$scope.items[id]];
		$scope.comment.author = $scope.current_user().name;
		$http.get($scope.url).
		success(function(data, status, headers, config) {
	    	$scope.comments = data;
	    	for (var i = 0; i < data.length; i++){	// add one hour for correct local time
	    		$scope.comments[i].createdAt = moment($scope.comments[i].createdAt).add(1, 'hours').format('YYYY-MM-DD HH:mm:ss');
	    	}
	    	if (data.length == 0){
	    		$scope.empty = true;
	    	}
		}).
		error(function(data, status, headers, config) {

		});
	}
	
	$scope.createComment = function(comment){
		$http.post($scope.url, JSON.stringify(comment))
		.then(function success(response) {
			if (response.data.success){
				comment.createdAt = moment().format('YYYY-MM-DD HH:mm:ss');
				comment.id = response.data.cID;
				comment.itemID = response.data.itemID;
				var tmpComment = {author: comment.author, title: comment.title1, message: comment.message, id: comment.id, itemID: comment.itemID, createdAt: comment.createdAt};
				$scope.comments.push(tmpComment);
				$rootScope.alert = true;
				$rootScope.success = true;
				$rootScope.feedback = response.data.feedback;
				$scope.comment.message = "";
				$scope.comment.title1 = "";
			} else {
				$rootScope.alert = true;
				$rootScope.success = false;
				$rootScope.feedback = response.data.feedback;
			}
			
		});
		
	}
	
	$scope.deleteComment = function(index){
		var commID = $scope.comments[index].id;
		var itemID = $scope.comments[index].itemID;
		$http.delete('http://localhost:8080/webshop/api/resource/item/' + itemID + '/comment/' + commID)
		.then(function success(response) {
			$rootScope.feedback = response.data.feedback;
			if (response.data.success){
				$rootScope.alert = true;
				$rootScope.success = true;
				$scope.comments.splice(index, 1);
			} else {
				$rootScope.alert = true;
				$rootScope.success = false;
			}
		});
	}
	
	$scope.deleteItem = function(index){
		var id = $scope.items[index].id;
		$http.delete('http://localhost:8080/webshop/api/resource/item/' + id)
		.then(function success(response) {
			$rootScope.feedback = response.data.feedback;
			if (response.data.success){
				$rootScope.alert = true;
				$rootScope.success = true;
				$scope.comments.splice(index, 1);
			} else {
				$rootScope.alert = true;
				$rootScope.success = false;
			}
		});
		$scope.items.splice(index, 1);
	}
	
	$scope.createItem = function(item){
		item.author = $("#author").val();
		item.category = $("#category").val();
		$http.post($scope.baseUrl, JSON.stringify(item))
		.then(function success(response) {
			if (response.data.success){
		    	$scope.items = $scope.items.push(item);
		    	$rootScope.alert = true;
		    	$rootScope.success = true;
		    	$rootScope.feedback = response.data.feedback;
		    	$scope.item.title = "";
		    	$scope.item.description = "";
		    	$scope.item.price = "";
		    	$('select').val('').selectpicker('refresh');
			} else {
				$rootScope.alert = true;
				$rootScope.success = false;
				$rootScope.feedback = response.data.feedback;
			}
		});
	}
	
	$scope.current_user = function() {
		return loginService.current_user();
	};
});

app.controller("CategoryCtrl", function($scope, $http){
	$scope.baseUrl = "http://localhost:8080/webshop/api/resource/category";
	// get categories
	$http.get($scope.baseUrl).
	success(function(data, status, headers, config){
    	$scope.categories = data;
    	$scope.oneSelected = false;
    	$scope.created = false;
    	$scope.deleted = false;
	}).
	error(function(data, status, headers, config) {

	});
	
	
	// create
	$scope.createCategory = function(category){
		$http.post($scope.baseUrl, JSON.stringify(category))
		.then(function successCallback(response) {
		    if (response.data.success){
		    	$scope.feedback = response.data.feedback;
		    	$scope.alert = true;
		    	$scope.success = true;
		    	$scope.category.name = "";
				$scope.category.description = "";
		    } else {
		    	$scope.feedback = response.data.feedback;
		    	$scope.alert = true;
		    	$scope.success = false;
		    }
		  });
		
	}
	
	$scope.deleteCategory = function(index){
		var id = $scope.categories[index].id;
		$http.delete('http://localhost:8080/webshop/api/resource/category/' + id)
		.then(function success(response) {
			if (response.data.success){
				$scope.feedback = response.data.feedback;
				$scope.alert = true;
				$scope.success = true;
				$scope.categories.splice(index, 1);
			}
		});
		
	}
});

app.controller("LoginCtrl", function($scope, $http, loginService, $rootScope, $cookieStore){
	$scope.login = function(user){
		$http.post('http://localhost:8080/webshop/api/resource/login', JSON.stringify(user))
		.then(function success(response){
			$rootScope.feedback = response.data.feedback;
			$rootScope.alert = true;
			$rootScope.success = response.data.success;
			if (response.data.success){
				var tmpUser = {name: "", priv: "", session: ""};
				tmpUser.name = response.data.name;
				tmpUser.priv = response.data.priv;
				loginService.set_user(tmpUser);
			} else {
				loginService.unset_user();
			}
		});
	}
	
	$scope.current_user = function() {
		return loginService.current_user();
	};
	
	$scope.logout = function() {
		$http.post("http://localhost:8080/webshop/api/resource/logout", JSON.stringify($scope.current_user()))
		.then(function success(response){
			if (response.data.success){
				$rootScope.alert = true;
				$rootScope.success = true;
				$rootScope.feedback = response.data.feedback;
				loginService.unset_user();
			}
		});
	}
	
	$scope.register = function(user) {
		$http.post('http://localhost:8080/webshop/api/resource/register', JSON.stringify(user))
		.then(function success(response){
			$rootScope.feedback = response.data.feedback;
			$rootScope.alert = true;
			$rootScope.success = response.data.success;
		});
	}
});