<%@page import="java.util.ArrayList"%>
<%@ page
	import="models.User, db.Sqlite, models.Category, models.ActiveUser"
	language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<!DOCTYPE html>
<html>
<head>
<title>Profil</title>
<%@include file="navbar.jsp"%>

<style>
#map {
	left: 30%;
	top: 10%;
	height: 300px;
	width: 500px;
}

#floating-panel {
	position: absolute;
	top: 85%;
	left: 40%;
	z-index: 5;
	background-color: #fff;
	padding: 5px;
	border: 1px solid #999;
	text-align: center;
	font-family: 'Roboto', 'sans-serif';
	line-height: 30px;
	padding-left: 10px;
}
</style>
</head>
<body>
	<div id="angDiv" Ng-init='getAddress()'>
		<h1 class="h1">Profil</h1>
		
		<div class="h2" style="font-size: 20px;">{{current_user().name;}}</div>
		<h2 class="h2" style="font-size: 20px; margin-top: -10px;">{{addresseObj}}</h2>
		<div style="text-align: center">
		<div id="longLat" class="btn btn-default" onClick="getLongLat()">Long / Lat</div>
		</div>

		<div id="floating-panel">
			<input id="address" type="textbox" value="{{addresseObj}}"> <input
				id="submit" type="button" value="Geocode">
		</div>
		<div id="map"></div>
		<script>
			function initMap() {
				var map = new google.maps.Map(document.getElementById('map'), {
					zoom : 8,
					center : {
						lat : -34.397,
						lng : 150.644
					}
				});
				var geocoder = new google.maps.Geocoder();

				document.getElementById('submit').addEventListener('click',
						function() {
							geocodeAddress(geocoder, map);
						});
			}
			var longLat;

			function geocodeAddress(geocoder, resultsMap) {
				var address = document.getElementById('address').value;
				
				geocoder
						.geocode(
								{
									'address' : address
								},
								function(results, status) {
									if (status === google.maps.GeocoderStatus.OK) {
										resultsMap
												.setCenter(results[0].geometry.location);
										var marker = new google.maps.Marker(
												{
													map : resultsMap,
													position : results[0].geometry.location
													
												});
										longLat = results[0].geometry.location;
										getLongLat();
									} else {
										alert('Geocode was not successful for the following reason: '
												+ status);
									}
								});
			}
			
			function getLongLat(){
				console.log(longLat);
				var lat = longLat.lat();
				var long1 = longLat.lng();
				$("#longLat").html(lat + "  /  " + long1);
				return longLat;
			}
		</script>
		<script
			src="https://maps.googleapis.com/maps/api/js?&signed_in=true&callback=initMap"
			async defer></script>
	</div>
</body>
</html>