<%@ page import="models.User" language="java"
	contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>

<!DOCTYPE html>
<html>
	<head>
		<title>Webshop</title>
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
	<h1 class="h1">Latest Items</h1>
	<body ng-app="WebShop">
	  <div ng-controller="ItemCtrl">
	  	<div class="row" ng-repeat="item in items track by $index">
	  		<div class="col-xs-1 col-md-1 col-sm-1 col-lg-1">
				</div>
			<div class="col-xs-10 col-md-10 col-sm-10 col-lg-10">
				<div class="panel panel-default">
					<a ng-click="getItem($index)">
						<div class="panel-heading">
							{{item.title}}
							<span class="author">
								{{item.author}} - {{item.createdAt}}
							</span>
						</div>
						<div class="panel-body">
							{{item.description}}
							<span class="price">
								{{item.price}} $
								<div id="delbtn" class="btn btn-default btn-sm" ng-click="deleteItem($index)">
							<span class="glyphicon glyphicon-trash"></span>
						</div>
							</span>
						</div>
						</a>
				</div>
				
			</div>
			
	  	</div>
	  	<div ng-show="oneSelected">
				<br>
				<div class="row">
	  				<div class="col-xs-1 col-md-1 col-sm-1 col-lg-1">
					</div>
					<div class="col-xs-10 col-md-10 col-sm-10 col-lg-10">
						<h2 class="h2">Comments</h2>
						<div class="panel panel-default" ng-repeat="comment in comments">
							<div class="panel-body">
								{{comment.author}} : {{comment.message}} 
								<span class="author">
									{{comment.createdAt}}
								</span>
							</div>
						</div>
						<h2 class="h2">Create Comment</h2>
						<div ng-show="created">
							<div id="feedback" class="alert alert-success" role="alert">Comment successfully created</div>
							<br>
							<br>
						</div>
				  		<form class="form-group" id="commentForm" method="POST">
							<label for="author">Author</label>
							<input class="form-control" ng-model="comment.author" name="author" />
							<br>
							<label for="title1">Title</label>
							<input class="form-control" ng-model="comment.title1" name="title1" />
							<br>
							<label for="message">Message</label>
							<textarea class="form-control" ng-model="comment.message" name="message" rows="3"></textarea>
							<br>
							<input class="btn btn-default" ng-click="createComment(comment)" type="submit" value="Submit" />
						</form>
					</div>
				</div>
			</div>
	  		</div>
	  </div>
	</body>
</html>