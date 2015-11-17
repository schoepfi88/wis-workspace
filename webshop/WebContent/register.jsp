<!DOCTYPE html>
<html>
<head>
	<meta charset="US-ASCII">
	<title>Register</title>
	<%@include file="navbar.jsp" %>
</head>
<body>
	<h1 class="h1">Register</h1>
	<div class="col-xs-1 col-md-1 col-sm-1 col-lg-1">
		</div>
	<div class="col-xs-10 col-md-10 col-sm-10 col-lg-10">
		<form class="form-group" action="/webshop/Register" method="post">
			<label for="name">Username</label>
			<input class="form-control" name="name" />
			<br/>
			<label for="password">Password</label>
			<input class="form-control" name="password" type="password" />
			<br/>
			<input class="btn btn-default" type="submit" value="Submit" />
		</form>
	</div>

</body>
</html>