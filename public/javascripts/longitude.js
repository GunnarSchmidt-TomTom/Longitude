var app = angular.module('longitude',['leaflet-directive']);

app.controller('LocationController', function($scope, $http) {
  var mapLocation = {lat : 0, lng: 0, zoom: 0};
  var buddyLocation = {latLong : null, name : null};
  var ownLocation = {latLong : null, name : ""};
  var mapMarkers = {};

  var createMapMarker = function(latLong, name){
    return {
        lat : latLong.latitude,
        lng : latLong.longitude,
        message : name,
        focus : true,
        draggable: false
      };
  };

  var updateMap = function() {
    if(ownLocation.latLong){
      mapMarkers.ownMarker = createMapMarker(ownLocation.latLong, "You");
    }else {
      delete mapMarkers.ownMarker;
    }

    if (buddyLocation.latLong) {
      mapMarkers.buddyMarker = createMapMarker(buddyLocation.latLong, buddyLocation.name);
    }else {
      delete mapMarkers.buddyMarker;
    }
  }

  var getLocation = function(name) {
    $http.get("/api/location/" + name).success(function(response) {
      buddyLocation.latLong = response.latLong
      buddyLocation.name = response.name;
      updateMap();
    }).error(function(response) {
      buddyLocation = null;
    });
  };

  var sendLocation = function() {
      var location = {
          latLong : ownLocation.latLong,
          name : ownLocation.name,
          precision : 0.1
      };

      $http.post("/api/location", location).error(function(error){
          console.log(error);
      });
  }


  var setOwnLocation = function(position){
      ownLocation.latLong = {
         latitude : position.coords.latitude,
         longitude : position.coords.longitude
      };
  };

  var init = function(position){
      setOwnLocation(position);
      updateMap();

      mapLocation.lat = position.coords.latitude;
      mapLocation.lng = position.coords.longitude;
      mapLocation.zoom = 10;
  };

  navigator.geolocation.getCurrentPosition(init);

  $scope.mapLocation = mapLocation;
  $scope.ownLocation = ownLocation;
  $scope.buddyLocation = buddyLocation;
  $scope.mapMarkers = mapMarkers;

  $scope.getLocation = getLocation;
  $scope.sendLocation = sendLocation;
});
