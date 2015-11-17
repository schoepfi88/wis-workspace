<%@page import="java.util.ArrayList"%>
<%@ page import="models.User, db.Sqlite, models.Category" language="java"
	contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<!DOCTYPE html>
<html>
	<head>
	<title>Categories</title>
		<%@include file="navbar.jsp" %>
		<% if(Resource.getLoadTrigger() == Resource.getFeedbackTrigger()) {%>
			<div class="row">
				<div class="col-xs-1 col-md-1 col-sm-1 col-lg-1">
				</div>
				<div class="col-xs-10 col-md-10 col-sm-10 col-lg-10">
					<div id="feedback" class="alert alert-success" role="alert"><% out.println(Resource.getFeedback()); %></div>
				</div>
			</div>
		<% } %>
	</head>
<body>
	<h1 class="h1">Categories</h1>
	<% ArrayList<Category> cats = Sqlite.getInstance().getCategories(); %>
	<div class="row">
		<div class="col-xs-1 col-md-1 col-sm-1 col-lg-1">
				</div>
		<div class="col-xs-10 col-md-10 col-sm-10 col-lg-10">
			<% for (Category cat : cats) {%>
				<div class="panel panel-info">
					<div class="panel-body"><% out.println(cat.getName()); %><span class="desc"><%out.println(cat.getDescription());%></span></div>
				</div>
			<%} %>
			<div class="btn btn-default" onclick="location.href='/webshop/createCategory.jsp'">Create Category</div>
		</div>
	</div>
</body>
</html>