<%@page import="java.util.ArrayList"%>
<%@ page import="models.User, db.Sqlite, models.Category" language="java"
	contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<!DOCTYPE html>
<html>
	<head>
	<title>Categories</title>
		<%@include file="navbar.jsp" %>
	</head>
<body>
	<h1 class="h1">Categories</h1>
	<% ArrayList<Category> cats = Sqlite.getInstance().getCategories(); %>
	<div class="row" ng-app="WebShop" ng-controller="CategoryCtrl">
		<div class="col-xs-1 col-md-1 col-sm-1 col-lg-1">
				</div>
		<div class="col-xs-10 col-md-10 col-sm-10 col-lg-10">
			<div class="panel panel-info" ng-repeat="category in categories track by $index">
				<div class="panel-body">{{category.name}}<span class="desc">{{category.description}}</span></div>
			</div>
			<div class="btn btn-default" onclick="location.href='/webshop/createCategory.jsp'">Create Category</div>
			<br>
			<br>
		</div>
	</div>
</body>
</html>