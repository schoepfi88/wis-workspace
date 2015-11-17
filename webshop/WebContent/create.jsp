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
		<div class="col-xs-10 col-md-10 col-sm-10 col-lg-10">
			<form class="form-group" action="../webshop/api/resource/item" method="POST">
				<label for="author">Author</label>
				<input class="form-control" name="author" value="<%out.println(User.getInstance().getUsername());%>" readonly/>
				<br>
				<label for="title">Title</label>
				<input class="form-control" name="title" />
				<br>
				<label for="description">Description</label>
				<textarea class="form-control" name="description" rows="3"></textarea>
				<br>
				<label for="price">Price</label>
				<div class="input-group">
					<span class="input-group-addon">$</span>
					<input type="text" class="form-control" name="price" aria-label="Amount (to the nearest dollar)">
				</div>
				<br>
				<select name="category" class="selectpicker" multiple data-max-options="1" title="Select Category ...">
					<%for(int i = 0; i < categories.size();i++){%>
						<option><% out.println(categories.get(i).getName()); %></option>
					<%}%>
				</select>
				<br>
				<br>
				<input class="btn btn-default" type="submit" value="Submit" />
			</form>
			<% if (Resource.getFeedbackTrigger() == Resource.getLoadTrigger()) { %>
				<div id="feedback" class="alert alert-success" role="alert"><% out.println(Resource.getFeedback()); %></div>
			<% } %>
		</div>
	</div>
</body>
</html>