var app = angular.module('longitude',[]);

app.controller('LocationController', function($scope, $http) {
  var getLocation = function(name) {
    $http.get("/api/location/" + name).success(function(response) {
      $scope.currentLocation = response;
    }).error(function(response) {
      $scope.currentLocation = null;
    })
  }
  $scope.getLocation = getLocation;
});