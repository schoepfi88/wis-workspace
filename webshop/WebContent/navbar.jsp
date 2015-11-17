<%@ page import="models.User, resources.Resource" language="java"
	contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>

<!DOCTYPE html>
<html>
<head>
	<% Resource.incLoadTrigger(); %>
	<link href="css/bootstrap.min.css" rel="stylesheet"></link>
	<link href="css/webshop.css" rel="stylesheet"></link>
	<link href="css/bootstrap-select.min.css" rel="stylesheet"></link>
	<link href='https://fonts.googleapis.com/css?family=Montserrat' rel='stylesheet' type='text/css'>
	<script src="js/jquery-2.1.4.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script type="text/javascript" src="js/control.js"></script>
	<script type="text/javascript" src="js/angular.js"></script>
	<script type="text/javascript" src="js/loadItems.js"></script>
	<script type="text/javascript" src="js/bootstrap-select.min.js"></script>
	<script type="text/javascript" src="js/moment.min.js"></script>
	<nav class="navbar navbar-default">
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
					<li><a href="/webshop">Home</a></li>
					<li><a href="/webshop">Items</a></li>
					<li><a href="/webshop/categories.jsp">Categories</a></li>
					<li><a href="/webshop/create.jsp">Create Item</a></li>
					<li><a href="login.jsp">Login</a></li>
					<%
						User user = User.getInstance();
						String usr = user.getUsername();
					%>
					<li><a href="#/"> <% out.println(usr); %></a></li>
					<li><a href="/webshop/Logout">Logout</a></li>
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
</head>
</html>