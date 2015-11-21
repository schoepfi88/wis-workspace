var app = angular.module("WebShop", []);

app.controller("ItemCtrl", function($scope, $http) {
	$scope.baseUrl = "http://localhost:8080/webshop/api/resource/item/";
	$http.get('http://localhost:8080/webshop/api/resource/item').
	success(function(data, status, headers, config) {
    	$scope.items = data;
    	$scope.oneSelected = false;
    	$scope.created = false;
	}).
	error(function(data, status, headers, config) {

	});
	
	$scope.getItem = function(id){
		$scope.id = $scope.items[id].id;
		$scope.url = $scope.baseUrl + $scope.id + "/comment";
		$scope.oneSelected = true;
		$scope.items = [$scope.items[id]];
		$http.get($scope.url).
		success(function(data, status, headers, config) {
	    	$scope.comments = data;
	    	for (var i = 0; i < data.length; i++){	// add one hour for correct local time
	    		$scope.comments[i].createdAt = moment($scope.comments[i].createdAt).add(1, 'hours').format('YYYY-MM-DD HH:mm:ss');
	    	}
		}).
		error(function(data, status, headers, config) {

		});
	}
	
	$scope.createComment = function(comment){
		$http.post($scope.url, JSON.stringify(comment));
		comment.createdAt = moment().format('YYYY-MM-DD HH:mm:ss');
		$scope.comments.push(comment);
		$scope.comment = null;
		$scope.created = true;
	}
	
	$scope.deleteItem = function(index){
		var id = $scope.items[index].id;
		$http.delete('http://localhost:8080/webshop/api/resource/item/' + id);
		$scope.items.splice(index, 1);
	}
	
	$scope.createItem = function(item){
		item.author = $("#author").val();
		item.category = $("#category").val();
		$http.post($scope.baseUrl, JSON.stringify(item)).
		success(function(data, status, headers, config) {
	    	$scope.items = $scope.items.push(item);
	    	$scope.created = true;
	    	$scope.feedback = "Item successfully created";
	    	$scope.item.title = "";
	    	$scope.item.description = "";
	    	$scope.item.price = "";
	    	$('select').val('').selectpicker('refresh');
		}).
		error(function(data, status, headers, config) {

		});
	}
});

app.controller("CategoryCtrl", function($scope, $http){
	$scope.baseUrl = "http://localhost:8080/webshop/api/resource/category";
	// get categories
	$http.get($scope.baseUrl).
	success(function(data, status, headers, config) {
    	$scope.categories = data;
    	$scope.oneSelected = false;
    	$scope.created = false;
	}).
	error(function(data, status, headers, config) {

	});
	
	
	// create
	$scope.createCategory = function(category){
		$http.post($scope.baseUrl, JSON.stringify(category))
		.then(function successCallback(response) {
		    if (response.data.feedback == 200)
		    	$scope.feedback = "Category successfully created";
		  });
		$scope.created = true;
		$scope.category.name = "";
		$scope.category.description = "";
	}
});