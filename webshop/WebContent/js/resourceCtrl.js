var app = angular.module("WebShop", []);

app.controller("ItemCtrl", function($scope, $http) {
	$scope.baseUrl = "http://localhost:8080/webshop/api/resource/item/";
	$http.get('http://localhost:8080/webshop/api/resource/item').
	success(function(data, status, headers, config) {
    	$scope.items = data;
    	$scope.oneSelected = false;
    	$scope.alert = false;
	});
	
	$scope.getItem = function(id){
		$scope.id = $scope.items[id].id;
		$scope.comments = null;
		$scope.url = $scope.baseUrl + $scope.id + "/comment";
		$scope.oneSelected = true;
		$scope.items = [$scope.items[id]];
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
				console.log(JSON.stringify(response.data));
				comment.id = response.data.cID;
				comment.itemID = response.data.itemID;
				$scope.comments.push(comment);
				$scope.comment = null;
				$scope.alert = true;
				$scope.success = true;
				$scope.feedback = response.data.feedback;
			} else {
				$scope.alert = true;
				$scope.success = false;
				$scope.feedback = response.data.feedback;
			}
		});
		
	}
	
	$scope.deleteComment = function(index){
		var commID = $scope.comments[index].id;
		var itemID = $scope.comments[index].itemID;
		console.log(JSON.stringify($scope.comments[index]));
		$http.delete('http://localhost:8080/webshop/api/resource/item/' + itemID + '/comment/' + commID)
		.then(function success(response) {
			$scope.feedback = response.data.feedback;
			if (response.data.success){
				$scope.alert = true;
				$scope.success = true;
				$scope.comments.splice(index, 1);
			} else {
				$scope.alert = true;
				$scope.success = false;
			}
		});
	}
	
	$scope.deleteItem = function(index){
		var id = $scope.items[index].id;
		$http.delete('http://localhost:8080/webshop/api/resource/item/' + id)
		.then(function success(response) {
			$scope.feedback = response.data.feedback;
			if (response.data.success){
				$scope.alert = true;
				$scope.success = true;
				$scope.comments.splice(index, 1);
			} else {
				$scope.alert = true;
				$scope.success = false;
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
		    	$scope.alert = true;
		    	$scope.success = true;
		    	$scope.feedback = response.data.feedback;
		    	$scope.item.title = "";
		    	$scope.item.description = "";
		    	$scope.item.price = "";
		    	$('select').val('').selectpicker('refresh');
			} else {
				$scope.alert = true;
				$scope.success = false;
				$feedback = response.data.feedback;
			}
		});
	}
});

app.controller("CategoryCtrl", function($scope, $http){
	$scope.baseUrl = "http://localhost:8080/webshop/api/resource/category";
	// get categories
	$http.get($scope.baseUrl).
	success(function(data, status, headers, config){
    	$scope.categories = data;
    	console.log(JSON.stringify(data));
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