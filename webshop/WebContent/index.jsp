<%@ page import="models.User" language="java"
	contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>

<!DOCTYPE html>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js" type="text/javascript"></script>
<script type="text/javascript">
function share(item, id){
	$('#share_button').click(function(e){
		e.preventDefault();
		FB.ui(
		{
			method: 'feed',
			name: 'Share Item ' + item,
			link: 'http://localhost:8080/webshop',
			caption: 'Top 3 reasons why you should care about your finance',
			description: "http://localhost:8080/webshop/item/" + id,
			message: ""
		});
	});
};
</script>
<html>
	<head>
		<title>Webshop</title>
		<%@include file="navbar.jsp" %>
	</head>
	<h1 class="h1">Latest Items</h1>
	<body ng-app="WebShop">
	  <div ng-controller="ItemCtrl">
	  	<div ng-show="alert">
	  		<div class="row">
	  			<div class="col-xs-1 col-md-1 col-sm-1 col-lg-1">
				</div>
				<div class="col-xs-10 col-md-10 col-sm-10 col-lg-10">
					<div ng-show="success">
						<div id="feedback" class="alert alert-success" role="alert">{{feedback}}</div>
					</div>
					<div ng-show="!success">
						<div id="feedback" class="alert alert-danger" role="alert">{{feedback}}</div>
					</div>
					<br>
					<br>
				</div>
			</div>
		</div>
	  	<div class="row" ng-repeat="item in items track by $index">
	  		<div class="col-xs-1 col-md-1 col-sm-1 col-lg-1">
				</div>
			<div class="col-xs-10 col-md-10 col-sm-10 col-lg-10">
				<div class="panel panel-default">
					<a class="clickPanel" ng-click="getItem($index)">
						<div class="panel-heading">
							{{item.title}}
							<span class="author">
								{{item.author}} - {{item.createdAt}}
								<div id="share_button" class="fb-share-button" onclick="share(item.name,item.id)" data-layout="button"></div>
							</span>
						</div>
					</a>
					<div class="panel-body">
						{{item.description}}
						<span class="price">
							{{item.price}} $
							<div ng-show="current_user().priv == 7" id="delbtn" class="btn btn-danger btn-sm" ng-click="deleteItem($index)">
								<span class="glyphicon glyphicon-trash"></span>
							</div>
							<div ng-show="current_user().priv < 7" ng-click="addToCart(item)" id="addbtn" class="btn btn-success btn-sm">
								<span class="glyphicon glyphicon-plus"></span>
							</div>
						</span>
					</div>
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
						<div ng-show = "comments.length == 0">
							<div class="alert alert-warning" role="alert">No Comments available</div>
							<br>
							<br>
						</div>
						<div class="panel panel-default" ng-repeat="commi in comments track by $index">
							<div class="panel-body">
								{{commi.title}} : {{commi.message}}
								<span class="author">
									{{commi.author}} - {{commi.createdAt}}
									<div ng-show="current_user().priv == 7" id="delbtn" class="btn btn-danger btn-sm" ng-click="deleteComment($index)">
										<span class="glyphicon glyphicon-trash"></span>
									</div>
								</span>
							</div>
						</div>
						<h2 ng-show="current_user().priv > 0" class="h2">Create Comment</h2>
				  		<form ng-show="current_user().priv > 0" class="form-group" id="commentForm" method="POST">
							<label for="author">Author</label>
							<input class="form-control" ng-model="comment.author" name="author" readonly/>
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