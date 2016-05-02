 var app = angular.module("myApp", []);

 app.directive("test", function() {
     return {
         restrict: 'EAC',
         scope: {
             object: '=',
             button:'=',
             src:'@'
         },
        
         template: '<form name="dynamicForm" ng-submit="validate()" novalidate><div ng-repeat="obj in object" class="form-group" ><label>{{obj.label}}</label><input type="{{obj.type}}" name="{{obj.name}}"  class="{{obj.elementclass}}" ng-click="submit" ng-model="formData[obj.model]" ng-class="val()"  required><span class="glyphicon glyphicon-ok" ng-show="dynamicForm.{{obj.name}}.$valid"></span></div><button type="{{button.type}}" value="{{button.vlaue}}" class="{{button.buttonClass}}">{{button.value}}</button></form> ',
         
         controller:function($scope,$http){  
			 
			$scope.formData = {};
			$scope.post=function(){
				debugger
				$http.post($scope.src,$scope.formData).success(function(){
					alert("w8")
				}).error(function(err){
					console.log(err)
				})
			}
		 },
         link: function($scope, element, attrs,ctrl) { 
        	 $scope.val=function(){
					
					angular.forEach($scope.object, function(val,key){
						var e=$('input');
					if($scope.dynamicForm[val.name].$valid){
						
					$("[name = "+val.name).css({" -webkit-box-shadow": "inset 0 1px 1px rgba(0,0,0,.075)", "border-color": "green"});
					}
					
					
					});
					}
        	 
			$scope.validate=function(){
			
				angular.forEach($scope.object, function(val,key){
						var e=$('input');
					if($scope.dynamicForm[val.name].$invalid){
					$("[name = "+val.name).css({" -webkit-box-shadow": "inset 0 1px 1px rgba(0,0,0,.075)", "border-color": "red"});
					}
					else{
						$("[name = "+val.name).css({" -webkit-box-shadow": "inset 0 1px 1px rgba(0,0,0,.075)", "border-color": ""});
					}
					
					});
				if($scope.dynamicForm.$valid){
					 $scope.$emit('passModelData',$scope.formData)
				if($scope.src){
					 $scope.post();
				}
				else{
					alert("src not defined")
				}
				}
				else{
					$scope.$emit('passModelData')
				}
			}
			}
     } 
 });
