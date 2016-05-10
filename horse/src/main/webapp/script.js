var app = angular.module('studentApp', ['ngRoute','myApp']);
app.config(['$routeProvider', function($routeProvider) {

	// Define routes
	$routeProvider.when('/Add', {
		templateUrl: 'StudentForm.html',
		controller: 'formCtrl'


	}).otherwise({
		redirectTo: 'index.html'
	});

}]);

app.controller('ctrl',function($scope){ 
	
	$scope.$on("passModelData",function(event,data){
		 $scope.form=data;
		 
	 })
	
	 $scope.$on('promiseHandler',function(event,data){
		data.success(function(response){
			debugger
			console.log(response)
		})
	 })
})