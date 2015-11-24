<%@ page import="db.Sqlite, resources.Resource, java.util.ArrayList, models.Category" language="java"
	contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Create Category</title>
	<%@include file="navbar.jsp" %>
</head>
<body>
	<h1 class="h1"> Create Category </h1>
	<div class="row">
		<div class="col-xs-1 col-md-1 col-sm-1 col-lg-1">
		</div>
		<div class="col-xs-10 col-md-10 col-sm-10 col-lg-10" ng-app="WebShop" ng-controller="CategoryCtrl">
			<form class="form-group">
				<label for="name">Name</label>
				<input class="form-control" name="name" ng-model="category.name"/>
				<br/>
				<label for="description">Description</label>
				<textarea class="form-control" ng-model="category.description" name="description" rows="3"></textarea>
				<br/>
				<input name="submit" class="btn btn-default" type="submit" ng-click="createCategory(category)" value="Submit" />
			</form>
			<div ng-show="alert">
				<div ng-show="success">
					<div id="feedback" class="alert alert-success" role="alert">{{feedback}}</div>
				</div>
				<div ng-show="!success">
					<div id="feedback" class="alert alert-danger" role="alert">{{feedback}}</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>