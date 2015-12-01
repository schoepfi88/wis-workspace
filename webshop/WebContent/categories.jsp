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
	<div class="row" ng-app="WebShop" ng-controller="CategoryCtrl">
		<div class="col-xs-1 col-md-1 col-sm-1 col-lg-1">
				</div>
		<div class="col-xs-10 col-md-10 col-sm-10 col-lg-10">
			<div ng-show="alert">
				<div ng-show="success">
					<div id="feedback" class="alert alert-success" role="alert">{{feedback}}</div>
				</div>
				<div ng-show="!success">
					<div id="feedback" class="alert alert-danger" role="alert">{{feedback}}</div>
				</div>
				<br>
				<br>
			</div>
			<div class="panel panel-info" ng-repeat="category in categories track by $index">
				<div class="panel-heading clickPanel" data-toggle="collapse" data-target="#collapseOne{{$index}}" ng-click="loadItemsOfCategory($index)">
					{{category.name}}
					<span class="desc">{{category.description}}
						<div ng-show="current_user().priv == 7" id="delbtn" class="btn btn-danger btn-sm" ng-click="deleteCategory($index)">
							<span class="glyphicon glyphicon-trash"></span>
						</div>
					</span>
				</div>
				<div id="collapseOne{{$index}}" class="panel-collapse collapse">
					<div class="panel-body">
						<div ng-show="category.items.length == 0">
								<li class="list-group-item itemHead">
									No Item available
								</li>
						</div>
						<ul class="list-group" ng-show="category.items.length > 0" ng-repeat="item in category.items">
							<li class="list-group-item itemHead">{{item.title}}
							  	<span class="author">
									{{item.author}} - {{item.createdAt}}
								</span>
							</li>
							<li class="list-group-item itemBody">{{item.description}}
						  	<span class="price">
								{{item.price}} $
							</li>
						</span>
						  
						  </li>
						</ul>
					</div>
				</div>
			</div>
			<div ng-show="current_user().priv == 7" class="btn btn-default" onclick="location.href='/webshop/createCategory.jsp'">Create Category</div>
			<br>
			<br>
		</div>
	</div>
</body>
</html>