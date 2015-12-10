<%@ page import="models.User, resources.Resource" language="java"
	contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>

<!DOCTYPE html>
<html>
<head>
	<link href="css/bootstrap.min.css" rel="stylesheet"></link>
	<link href="css/webshop.css" rel="stylesheet"></link>
	<link href="css/bootstrap-select.min.css" rel="stylesheet"></link>
	<link href='https://fonts.googleapis.com/css?family=Montserrat' rel='stylesheet' type='text/css'>
	<script src="js/jquery-2.1.4.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script type="text/javascript" src="js/angular.js"></script>
	<script type="text/javascript" src="js/angular-cookies.min.js"></script>
	<script type="text/javascript" src="js/resourceCtrl.js"></script>
	<script type="text/javascript" src="js/bootstrap-select.min.js"></script>
	<script type="text/javascript" src="js/moment.min.js"></script>
	<script type="text/javascript" src="js/control.js"></script>
</head>
<body ng-app="WebShop" ng-controller="LoginCtrl">
	<nav class="navbar navbar-default" ng-init="checkLogin()">
		<div class="container-fluid">
			<!-- mobile use -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target=".navbar-collapse"
					aria-expanded="true">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="index.jsp">Webshop</a>
			</div>

			<div class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li id="homeNavBar"><a href="/webshop">Home</a></li>
					<li><a href="/webshop">Items</a></li>
					<li id="createCatNavBar"><a href="/webshop/categories.jsp">Categories</a></li>
					<li id="createItemNavBar" ng-show="current_user().priv == 7"><a href="/webshop/create.jsp">Create Item</a></li>
					<li id="loginNavBar" ng-show="current_user().name == 'guest'"><a href="login.jsp">Login</a></li>
					<li id="logoutNavBar" ng-show="current_user().name != 'guest'" ng-click="logout()"><a>Logout</a></li>
					<li id="userNavBar"><a href="#/"> {{current_user().name;}}</a></li>
					<li id="cart" ng-show="current_user().priv < 7 && current_user().priv != 0"><a onclick="location.href='/webshop/cart.jsp'"><span class="glyphicon glyphicon-shopping-cart"></span></a></li>
				</ul>
				<form class="navbar-form navbar-right" role="search">
					<div class="input-group">
						<input type="text" class="form-control" placeholder="Search">
						<span class="input-group-btn">
							<button class="btn btn-default" type="button">Go</button>
						</span>
					</div>
					<!-- /input-group -->
				</form>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container-fluid -->
	</nav>
</body>
</html>