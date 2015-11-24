<%@ page import="db.Sqlite, resources.Resource, java.util.ArrayList, models.Category, models.User" language="java"
	contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
	
	<%
	ArrayList<Category> categories = Sqlite.getInstance().getCategories();
	%>

<!DOCTYPE html>
<html>
<head>
	<title>Create Item</title>
	<%@include file="navbar.jsp" %>
</head>
<body>
	<h1 class="h1"> Create Item </h1>
	<div class="row">
		<div class="col-xs-1 col-md-1 col-sm-1 col-lg-1">
		</div>
		<div class="col-xs-10 col-md-10 col-sm-10 col-lg-10" ng-app="WebShop" ng-controller="ItemCtrl">
			<form class="form-group">
				<label for="author">Author</label>
				<input id="author" class="form-control" name="author"  value="<%out.println(User.getInstance().getUsername());%>" readonly/>
				<br>
				<label for="title">Title</label>
				<input class="form-control" name="title" ng-model="item.title" />
				<br>
				<label for="description">Description</label>
				<textarea class="form-control" name="description" rows="3" ng-model="item.description"></textarea>
				<br>
				<label for="price">Price</label>
				<div class="input-group">
					<span class="input-group-addon">$</span>
					<input type="text" class="form-control" name="price" ng-model="item.price" aria-label="Amount (to the nearest dollar)">
				</div>
				<br>
				<select id="category" name="category" class="selectpicker" title="Select Category ...">
					<%for(int i = 0; i < categories.size();i++){%>
						<option><% out.println(categories.get(i).getName()); %></option>
					<%}%>
				</select>
				<br>
				<br>
				<input class="btn btn-default" type="submit" value="Submit" name="submit" ng-click="createItem(item)"/>
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