var myApp = angular.module("filterApp", []);

myApp.controller('filterController', function($scope, $http, $window) {

	$scope.skills = true;
	$scope.resumeList = false;

	$scope.isChecked = function(skill){
		var match = false;
		for(var i=0 ; i < $scope.data.length; i++) {
			if($scope.data[i].skill == skill){
				match = true;
			}
		}
		return match;
	};

	$scope.techSkills = [
	                     {
	                    	 "skill": "Java"
	                     },
	                     {
	                    	 "skill": "Git"
	                     },
	                     {
	                    	 "skill": "PHP"
	                     },
	                     {
	                    	 "skill": "Python"
	                     },
	                     {
	                    	 "skill": "Spring"
	                     },
	                     {
	                    	 "skill": "Hibernate"
	                     },
	                     {
	                    	 "skill": "REST"
	                     },
	                     {
	                    	 "skill": "SOAP"
	                     },
	                     {
	                    	 "skill": "XML"
	                     },
	                     {
	                    	 "skill": "JSON"
	                     },
	                     {
	                    	 "skill": "JavaScript"
	                     },
	                     {
	                    	 "skill": "AngularJs"
	                     },
	                     {
	                    	 "skill": "NodeJs"
	                     },
	                     {
	                    	 "skill": "Express"
	                     },
	                     {
	                    	 "skill": "Ruby"
	                     },
	                     {
	                    	 "skill": "ReactJs"
	                     },
	                     {
	                    	 "skill": "Ant"
	                     },
	                     {
	                    	 "skill": "Maven"
	                     }
	                     ];

	$scope.data = [];
	$scope.data1 = [];

	$scope.sync = function(bool, item){
		if(bool){
			// add item
			$scope.data.push(item);
		} else {
			// remove item
			for(var i=0 ; i < $scope.data.length; i++) {
				if($scope.data[i].skill == item.skill){
					$scope.data.splice(i,1);
				}
			}      
		}
	};

	$scope.search = function(){
		$scope.skills = false;
		console.log("searching!!!");

		$scope.data1 = [];
		for(var i in $scope.data) {
			if($scope.data.hasOwnProperty(i) && !isNaN(+i)) {
				$scope.data1[+i] = $scope.data[i].skill;
			}
		}

		var keywords = {"skill": $scope.data1};

		$scope.filesList = [];

		$http.post("/SearchResume", keywords)
		.success(function(data,status) {
			if (status === 200)
			{
				console.log("Success returned from searchResume Function");
				console.log(data);
				console.log(status);

				var i = 0;

				for (i=0; i<data.files.length; i++){
					var fileDetails = {};
					fileDetails["Name"] = data.files[i];
					$scope.filesList.push(fileDetails);
				}

				$scope.resumeList = true;
			}
			else
			{
				console.log("Error returned from searchResume Function");
			}
		});

	};

	$scope.download = function(Name) {

		var downloadURL = 'http://localhost:8080/Client1/DownloadFile?fileName=';
		var fileName = Name;

		var finalDownloadURL = downloadURL.concat(fileName);
		console.log(finalDownloadURL);

		$window.open(finalDownloadURL);

	};

});