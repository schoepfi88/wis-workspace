<!DOCTYPE html>
<html>
<head>
	<meta charset="US-ASCII">
	<title>Login</title>
	<%@include file="navbar.jsp" %>
</head>
<body>
	<h1 class="h1">Login</h1>
	<div class="row">
		<div class="col-xs-1 col-md-1 col-sm-1 col-lg-1">
		</div>
		<div class="col-xs-10 col-md-10 col-sm-10 col-lg-10" ng-app="WebShop" ng-controller="LoginCtrl">
			<form class="form-group">
				<label for="name">Username</label>
				<input class="form-control" name="name" ng-model="user.name"/>
				<br/>
				<label for="password">Password</label>
				<input class="form-control" ng-model="user.password" name="password" type="password"/>
				<br/>
				<input name="submit" class="btn btn-default" type="submit" ng-click="login(user)" value="Submit" />
				<div class="btn btn-default btn-right" onclick="location.href='register.jsp'">Register</div>
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