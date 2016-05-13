var myApp = angular.module("configurationApp", []);

myApp.controller('configurationController', function($scope, $http, $window) {

	$scope.configDetailsList = [];
	$scope.resumeList = false;
	$scope.configList = true;
	
	$http.post("/ConfigurationList")
	.success(function(data,status) {
		if (status === 200)
		{
			console.log("Success returned from ConfigurationList Function");
			console.log(data);
			console.log(status);

			var i = 0;

			for (i=0; i<data.config.length; i++){
				var configDetails = {};
				configDetails["Id"] = data.config[i].jobId;
				configDetails["Title"] = data.config[i].jobTitle;
				configDetails["Keywords"] = data.config[i].keywords;
				$scope.configDetailsList.push(configDetails);
			}

		}
		else
		{
			console.log("Error returned from ConfigurationList Function");
		}
	});
	
	$scope.search = function(commakeywords){
		//$scope.skills = false;
		console.log("searching again!!!");

		console.log("KEYWORDS: " + commakeywords);
		
		var configKeywords = [];
		
		configKeywords = commakeywords.split(",");
		
		var keywords = {"skill": configKeywords};

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
				$scope.configList = false;
			}
			else
			{
				console.log("Error returned from searchResume Function");
			}
		});

	};

	$scope.download = function(Name) {

		var downloadURL = 'http://localhost:8080/ResumeRankerServer/DownloadResume?fileName=';
		var fileName = Name;

		var finalDownloadURL = downloadURL.concat(fileName);
		console.log(finalDownloadURL);

		$window.open(finalDownloadURL);

	};
	
	
});