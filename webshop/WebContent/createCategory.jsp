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
		<div class="col-xs-10 col-md-10 col-sm-10 col-lg-10">
			<form class="form-group" action="../webshop/api/resource/category" method="POST">
				<label for="name">Name</label>
				<input class="form-control" name="name" />
				<br/>
				<label for="description">Description</label>
				<textarea class="form-control" name="description" rows="3"></textarea>
				<br/>
				<input class="btn btn-default" type="submit" value="Submit" />
			</form>
			<% if (Resource.getFeedbackTrigger() == Resource.getLoadTrigger()) { %>
				<div id="feedback" class="alert alert-success" role="alert"><% out.println(Resource.getFeedback()); %></div>
			<% } %>
		</div>
	</div>
</body>
</html>