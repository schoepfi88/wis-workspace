<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>

<!DOCTYPE html>
<html>
<head>
<title>Cart</title>
<%@include file="navbar.jsp"%>
</head>
<body>
	<h1 class="h1">Shopping Cart</h1>
	<div class="row" ng-app="WebShop" ng-controller="CategoryCtrl">
		<div class="col-xs-1 col-md-1 col-sm-1 col-lg-1"></div>
		<div class="col-xs-10 col-md-10 col-sm-10 col-lg-10">
			<div ng-show="alert">
				<div ng-show="success">
					<div id="feedback" class="alert alert-success" role="alert">{{feedback}}</div>
				</div>
				<div ng-show="!success">
					<div id="feedback" class="alert alert-danger" role="alert">{{feedback}}</div>
				</div>
				<br> <br>
			</div>
			<div class="jumbotron" ng-app="WebShop" ng-controller="ItemCtrl">
				<div class="row" ng-repeat="item in cartItems track by $index">
					<div class="col-xs-1 col-md-1 col-sm-1 col-lg-1"></div>
					<div class="col-xs-10 col-md-10 col-sm-10 col-lg-10">
						<div class="panel panel-default">
							<a class="clickPanel" ng-click="getItem($index)">
								<div class="panel-heading">
									{{item.title}} <span class="author"> {{item.author}} -
										{{item.createdAt}} </span>
								</div>
							</a>
							<div class="panel-body">
								{{item.description}} <span class="price"> {{item.price}}
									$ <select id="counter{{$index}}"
									class="selectpicker count-picker" data-width="fit">
										<option ng-repeat="num in numbers track by $index">{{num}}</option>
								</select>
									<div ng-show="current_user().priv < 7"
										ng-click="removeFromCart($index)" id="addbtn"
										class="btn btn-danger btn-sm">
										<span class="glyphicon glyphicon-minus"></span>
									</div>
								</span>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-1 col-md-1 col-sm-1 col-lg-1"></div>
					<div class="col-xs-10 col-md-10 col-sm-10 col-lg-10">
						<div class="panel panel-default">
							<div class="panel-body">
								<div id="total" ng-show="cartItems.length" class="price">Total:
									{{sum}} $</div>
								<div ng-show="!cartItems.length">Cart is empty</div>
							</div>
						</div>
						<div class="input-group" ng-show="cartItems.length">
							<span class="input-group-addon" id="basic-addon1">Address</span>
							<input ng-model="address" type="text" class="form-control"
								placeholder="" aria-describedby="basic-addon1">
						</div>
						<br>
						<div ng-show="cartItems.length" class="btn btn-danger">Clear
							Cart</div>
						<div ng-show="!cartItems.length" class="btn btn-danger" disabled>Clear
							Cart</div>
						<div ng-show="address.length" class="btn btn-success btn-right">
							<form action="https://www.sandbox.paypal.com/cgi-bin/webscr"
								method="post" target="_top">
								<input type="hidden" name="cmd" value="_xclick"> <input
									type="hidden" name="business" value="RZ2K3LJH3WBYS"> <input
									type="hidden" name="lc" value="AT"> <input
									type="hidden" name="item_name" value="Shopping-Cart"> <input
									type="hidden" name="amount" value="{{sum}}"> <input
									type="hidden" name="currency_code" value="EUR"> <input
									type="hidden" name="button_subtype" value="services"> <input
									type="hidden" name="no_note" value="0"> <input
									type="hidden" name="cn" value="Mitteilung an den Händler:">
								<input type="hidden" name="no_shipping" value="2"> <input
									type="hidden" name="bn"
									value="PP-BuyNowBF:btn_buynowCC_LG.gif:NonHosted"> <input
									type="image"
									src="https://www.sandbox.paypal.com/de_DE/AT/i/btn/btn_buynowCC_LG.gif"
									border="0" name="submit"
									alt="Jetzt einfach, schnell und sicher online bezahlen – mit PayPal.">
								<img alt="" border="0"
									src="https://www.sandbox.paypal.com/de_DE/i/scr/pixel.gif"
									width="1" height="1">
							</form>


						</div>
						<div ng-show="!address.length" class="btn btn-success btn-right"
							disabled>Proceed</div>
					</div>
				</div>
			</div>
		</div>
</body>
</html>