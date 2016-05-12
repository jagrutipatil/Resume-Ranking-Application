var myApp = angular.module("loginApp", []);

myApp.controller('loginController', function($scope, $http) {

	$scope.login = function(){
  		
		console.log("login angular working...");
		
		var loginInfo = {loginEmail:$scope.loginEmail, loginPass:$scope.loginPassword};
		
		$http.post("/login", loginInfo)
		.success(function(data) {
			if (data === "SUCCESS")
			{
				console.log("Success returned from Login Function");
				window.location.assign("/filter");
			}
			else
			{
				console.log("Incorrect Login");
				//$scope.loginStatus = "INVALID!";
			}
		});
			
	};
	
	
	$scope.register = function(){
  		
			console.log("register angular working...");
				
  			var signUpInfo = {registerEmail:$scope.registerEmail, registerPass:$scope.registerPassword};
  			
  			$http.post("/register", signUpInfo)
  			.success(function(data) {
  				if (data === "SUCCESS")
  				{
  					console.log("Success returned from signup Function");
  					window.location.assign("/filter");
  				}
  				else if (data === "FAILED")
  				{
  					console.log("There was an error creating an account");
  					//$scope.signupStatus = "ERROR CREATING AN USER!!!";
  				}
            });
            
        };
        
});