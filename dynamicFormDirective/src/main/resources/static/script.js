 var app = angular.module("myApp1", []);

 app.directive("test", function() {
     return {
         restrict: 'EAC',
         scope: {
             object: '=',
         },
         template: '<form name="dynamicForm" ng-submit="validate()" novalidate><div ng-repeat="obj in object" class="form-group" ><label>{{obj.label}}</label><input type="{{obj.type}}"name="{{obj.name}}"  class="{{obj.elementclass}}" ng-click="submit" ng-model="formData[obj.model]" required></div></form> ',
		 controller:function($scope){  
			$scope.formData = {};
		 },
         link: function(scope, element, attrs,ctrl) { 

			scope.validate=function(){
				
				angular.forEach(scope.object, function(val,key){
						var e=$('input');
					if(scope.dynamicForm[val.name].$invalid){
					$("[name = "+val.name).css({" -webkit-box-shadow": "inset 0 1px 1px rgba(0,0,0,.075)", "border-color": "red"});
					}
					else{
						$("[name = "+val.name).css({" -webkit-box-shadow": "inset 0 1px 1px rgba(0,0,0,.075)", "border-color": "green"});
					}
					});
			}
			}
     } 
 });
 