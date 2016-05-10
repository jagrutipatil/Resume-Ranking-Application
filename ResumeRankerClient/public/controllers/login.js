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
				window.location.assign("/home");
			}
			else if (data === "FAILED")
			{
				console.log("Incorrect Login");
				$scope.loginStatus = "INVALID!";
			}
		});
			
	};
	
	
	$scope.register = function(){
  		
			console.log("register angular working...");
			/*$scope.signupStatus = "";
			
			if ($scope.fName == null || $scope.fName == "")
			{
				$scope.signupStatus = "ERROR CREATING AN USER! FIRST NAME REQUIRED!";
			}
			
			else if ($scope.lName == null || $scope.lName == "")
			{
				$scope.signupStatus = "ERROR CREATING AN USER! LAST NAME REQUIRED!";
			}
			
			else if ($scope.emailID == null || $scope.emailID == "")
			{
				$scope.signupStatus = "ERROR CREATING AN USER! EMAILID REQUIRED!";
			}
			
			else if ($scope.paswd == null || $scope.paswd == "")
			{
				$scope.signupStatus = "ERROR CREATING AN USER! PASSWORD REQUIRED!";
			}*/
		
			/*
			if ($scope.signupStatus == ""){
				console.log("hellooooo");*/
			
  			var signUpInfo = {registerEmail:$scope.registerEmail, registerPass:$scope.registerPassword};
  			
  			$http.post("/register", signUpInfo)
  			.success(function(data) {
  				if (data === "SUCCESS")
  				{
  					console.log("Success returned from signup Function");
  					window.location.assign("/home");
  				}
  				else if (data === "FAILED")
  				{
  					console.log("There was an error creating an account");
  					$scope.signupStatus = "ERROR CREATING AN USER!!!";
  				}
            });
            
        };
        
});