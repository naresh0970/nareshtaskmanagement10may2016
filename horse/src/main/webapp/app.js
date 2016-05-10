 var app = angular.module("myApp", []);

 app.directive("test", function() {
     return {
         restrict: 'EAC',
         scope: {
             object: '=',
             button:'=',
             src:'@',
             validclass:'@',
             invalidclass:'@',
             select:'@',
         },
        
         template:'<form name="dynamicForm" ng-submit="validate()" novalidate><div ng-repeat="obj in object" class="form-group" ><label>{{obj.label}}</label><input type="{{obj.type}}" name="{{obj.name}}"  class="{{obj.elementclass}}" ng-click="submit" ng-model="formData[obj.model]" ng-class="val()"  required></div><button type="{{button.type}}" value="{{button.vlaue}}" class="{{button.buttonClass}}">{{button.value}}</button></form> ',
         
         controller:function($scope,$http){  
			 
			$scope.formData = {};
			$scope.post=function(){
						$scope.$emit("promiseHandler",$http.post("adduser",$scope.formData))
			}
		 },
         link: function($scope, element, attrs,ctrl) { 
        	 
        	 $scope.val=function(){
					
					angular.forEach($scope.object, function(val,key){
						var e=$('input');
					if($scope.dynamicForm[val.name].$valid){
						$("[name = "+val.name).removeClass($scope.invalidclass)
					$("[name = "+val.name).addClass($scope.validclass)
					}
					
					});
					}
        	 
			$scope.validate=function(){
			
				angular.forEach($scope.object, function(val,key){
						var e=$('input');
					if($scope.dynamicForm[val.name].$invalid){
					$("[name = "+val.name).addClass($scope.invalidclass)
					}
					else{
						$("[name = "+val.name).addClass($scope.validclass)
					}
					
					});
				if($scope.dynamicForm.$valid){
					 $scope.$emit('passModelData',$scope.formData)
				if($scope.src){
					 $scope.post();
				}
				else{
					//alert("src not defined")
					 $scope.post();
				}
				}
				else{
					$scope.$emit('passModelData')
				}
			}
			}
     } 
 });
