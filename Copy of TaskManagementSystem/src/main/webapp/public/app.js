var app = angular.module('taskApp', ['ngRoute', 'ui.bootstrap', 'ngTagsInput','cgNotify']);


app.factory('UserStatus', ['$q', '$rootScope', '$http', function ($q, $rootScope, $http) {
    return {
        isAuthenticated: function () {
            var deferred = $q.defer();
            if ($rootScope.authenticationChecked) {
                if ($rootScope.authenticated) {
                    deferred.resolve('OK');
                } else {
                    deferred.reject('Unauthorized');
                }
            } else {
                $http.get('/login').then(function (response) {
                    deferred.resolve('OK');
                    $rootScope.authenticationChecked = true;
                    $rootScope.authenticated = true;
                }, function (response) {
                    deferred.reject('Unauthorized');
                });
            }
            return deferred.promise;
        }
    }
}]);
app.config(['$routeProvider', '$httpProvider', function ($routeProvider, $httpProvider) {

    // Define routes
    $routeProvider.when('/loginpage', {
        templateUrl: 'login.html',
        controller: 'loginController'

    }).when('/users', {
        templateUrl: '/userlist.html',
        resolve: {
            access: ['UserStatus', function (UserStatus) {
                return UserStatus.isAuthenticated();
            }],
        },
        controller: 'getUsers'

    }).when('/task', {
        templateUrl: '/tasklist.html',
        resolve: {
            access: ['UserStatus', function (UserStatus) {
                return UserStatus.isAuthenticated();
            }],
        },
        controller: 'taskCtrl'

    }).when('/addTask', {
        templateUrl: '/taskform.html',
        resolve: {
            access: ['UserStatus', function (UserStatus) {
                return UserStatus.isAuthenticated();
            }],
        },
        controller: 'createTask'

    }).when('/currentUserTask', {
        templateUrl: '/usertask.html',
        resolve: {
            access: ['UserStatus', function (UserStatus) {
                return UserStatus.isAuthenticated();
            }],
        },
        controller: 'currentUserTask'

    }).when('/admin', {
        templateUrl: '/admin/admin.html',
        resolve: {
            access: ['UserStatus', function (UserStatus) {
                return UserStatus.isAuthenticated();
            }],
        },
        controller: 'adminCtrl'

    }).otherwise('/currentUserTask');

    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
}]);

app.run(function ($rootScope, $location, $http) {

    $rootScope.loadUsers = function (e) {
        $rootScope.asignee = e.target.value;
        $http.get('/loadUsers?value=' + e.target.value).
            success(function (data) {

                $rootScope.userData = data
            })

    }

    $rootScope.$on("$routeChangeError", function (event, current, previous, rejection) {
        $location.url('/loginpage?redirect=' + $location.url());
    });
    $http({
        method: 'GET',
        url: '/login',

    }).success(function (data) {
        $rootScope.currentUserName = data.user;
        $rootScope.userRole = data.role[0].role;
        $rootScope.currentUserId = data.userId;
        $rootScope.authenticated = true;
        $rootScope.authorizationChecked = true;
        if ($rootScope.userRole === "user") {
            $rootScope.admin = false;
        } else {
            $rootScope.admin = true;
        }

    }).error(function (error) {

    })
 
    
    
});

app.controller('mainController', function ($location, $http, $rootScope, $scope) {

    $rootScope.logout = function () {
        $http.get("/logout");
        location.reload();
    }
    $scope.isActive = function (viewLocation) {
        var active = (viewLocation === $location.path());
        return active;
    }
});

app.controller('loginController', function ($scope, $http, $rootScope, $route, $location) {

    $scope.loginHeading = "Sign in to TaskManagement";
    $scope.registerHeading = "Sign up to TaskManagement";

    $scope.showField = true

    $scope.showOrHide = function () {

        $scope.showField = !$scope.showField;
    }
    $rootScope.loadImageFileAsURL = function () {
        var filesSelected = document.getElementById("imagefile").files;
        if (filesSelected.length > 0) {
            var fileToLoad = filesSelected[0];

            var fileReader = new FileReader();

            fileReader.onload = function (fileLoadedEvent) {
              
                $rootScope.srcData = fileLoadedEvent.target.result; // <--- data: base64

            }

            fileReader.readAsDataURL(fileToLoad);
        }

    };
     
    
    $scope.users = {
        image: $rootScope.srcData,
        userId: null,
        name: "",
        username: "",
        password: "",

      
    }
    $scope.register = function () {
        $scope.users.image = $rootScope.srcData,



        $http.post('/registerUser', $scope.users).success(function () {
      
            location.reload();
        }).error(function (error) {
        })
    }

    $scope.login = function () {

        var authenticationData = $scope.users.username + ':' + $scope.users.password;
        var encodedAuthData = btoa(authenticationData);

        $http({
            method: 'GET',
            url: '/login',
            headers: {
                'Authorization': 'Basic ' + encodedAuthData
            }

        }).success(function (data) {

            $rootScope.currentUserName = data.user;
            $rootScope.userRole = data.role[0].role;
            $rootScope.currentUserId = data.userId;
            $rootScope.authenticated = true;
            $rootScope.authorizationChecked = true;
           // $location.url("/currentUserTask");
            if ($rootScope.userRole === "user") {
                $rootScope.admin = false;
            } else {
                $rootScope.admin = true;
            }
        }).error(function (error) {

        })

    }


    $scope.reset = function () {
        $scope.users = null;
    }

});
app.controller('getUsers', function ($http, $scope, $rootScope) {

    $scope.intitPage = function () {
        $scope.page = {
            pageSize: 6,
            pageCount: 0
        };
    }

    $scope.getUsers = function () {

        $http.post("/getUsers", $scope.page).success(function (data) {
          
            console.log(data);
            $rootScope.totalUsers = data.totalElements;
        
            $scope.userList = data.content;
        });
    }

    $scope.toPageId = function (data) {
        $scope.intitPage();
        $scope.page.pageCount = data;
        $scope.getUsers();
    }
    $scope.intitPage();
    $scope.getUsers();

    $rootScope.getNumber = function (num) {
        return new Array(num);
    }


});
app.controller('createTask', function ($scope, $http, $rootScope,notify) {
  
    $scope.status = ['pending', 'completed', 'paused']
    $scope.types=['TASK','ISSUE']
   
   $http.get('getProject').success(function(data){
       $scope.projets=data;
   })
    $scope.loadTags = function (query) {
        return $http({
            method: 'GET',
            url: '/loadTags?value=' + query,
        }).error(function (error) {
            error
        });
    }
    
    $scope.file=document.getElementById('uploadfile').files;
    


    $http.get('/getModule').success(function (data) {
        $scope.modules = data;
    })


    $scope.task ={}
    
     $scope.addTask = function () {
    debugger;
     $scope.file=document.getElementById('uploadfile').value;
         $scope.task.document= $scope.file;
         $scope.task.assigner= $rootScope.currentUserName;
            $http.post('/addtask', $scope.task)
                .success(function () {
                successAlert();
              }) .error(function(){
                
            })
        }
    $scope.reset = function () {
        $scope.task = null;
    }
  
    function successAlert(){
            notify({
            message: 'Task added Successfully',
            classes: $scope.classes,
            templateUrl: $scope.template,
            position: 'center',
            duration: 800
    });
             $scope.reset()
}
});
app.controller('taskCtrl', function ($scope, $http, $rootScope, $routeParams) {
       
   jQuery('.taskheading').click(function(){
  jQuery('.taskheading').removeClass('active');
  jQuery(this).addClass('active');
});
    $scope.maxSize = 5;
    $scope.currentPage = 0;
    $scope.numPages = 1;

    $scope.getAllTask = function () {
        $scope.pageSize = 6;
        $http.post('/getAllTask?pc=' + $scope.currentPage + '&ps=' + $scope.pageSize)
            .success(function (data) {
                debugger;
                $rootScope.allTaskCount = data.totalElements;
                $scope.taskList = data.content;
                $scope.bigTotalItems = data.totalElements;
            })

        $scope.trigeredTask = function (index) {

            $scope.veiwTask = true;

            $scope.seletedTask = $scope.taskList[index];
        }

    }
    $scope.editTask = function (index) {

        $scope.selectedTask = $scope.taskList[index]
    }
    $scope.getAllTask();
    $scope.setPage = function (currentpage) {
        $scope.currentPage = currentpage;

        $scope.getAllTask();
    };

    $scope.tagId = "2";
    $scope.gettaskByTag = function () {

        $http.get('/getTaskByTag?value=' + $scope.tagId)
            .success(function (data) {
                console.log(data);
            })

    }


    $rootScope.getComments = function ($index) {

        $rootScope.commentdivshow = true;
        $http.get('/getComments?value=' + $scope.taskList[$index].taskId).success(function (response) {
            $rootScope.commentList = response;
            console.log(response);


        })
    }
    $scope.edit = true;
    $scope.enableTextField = function () {
        $scope.edit = false;
    }
});

app.controller('currentUserTask', function ($http, $scope, $rootScope) {
    jQuery('.taskheading').click(function(){
  jQuery('.taskheading').removeClass('active');
  jQuery(this).addClass('active');
});
    $scope.maxSize = 6;
    $scope.currentPage = 0;
    $scope.numPages = 1;

    $scope.getCurrentUserTask = function () {


        $scope.pageSize = 6;
        $http.get('/getCurrentUserTask?pc=' + $scope.currentPage + '&ps=' + $scope.pageSize + '&u=' + $rootScope.currentUserId)
            .success(function (data) {
                $scope.userTasks = data.content;
                $scope.bigTotalItems = data.totalElements;
            }).error(function (err) {
                console.log(err);
            });
    }
    $scope.getCurrentUserTask();
    $scope.setPage = function (currentpage) {
        $scope.currentPage = currentpage;
        $scope.getCurrentUserTask();
    };

    $scope.trigeredTask = function (index) {
        $scope.veiwTask = true;

        $scope.seletedTask = $scope.userTasks[index];

    }
    $scope.veiwTaskContent = function (status) {
        alert(status);
        if (status == true) {
            $scope.view = true;
        }

        else {
            $scope.view = false;
        }
    }
});
app.controller('adminCtrl', function ($scope, $rootScope, $http) {
    $scope.module = {
        moduleName: ""
    }
    $scope.addModule = function () {
        $http.post('/addModule', $scope.module)
            .success(function (data) {
                alert("w8");
            })
    }

})