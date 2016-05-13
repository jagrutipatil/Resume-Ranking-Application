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
	                    	 "skill": "C"
	                     },
	                     {
	                    	 "skill": "C++"
	                     },
	                     {
	                    	 "skill": "Java"
	                     },
	                     {
	                    	 "skill": "JavaScript"
	                     },
	                     {
	                    	 "skill": "SQL"
	                     },
	                     {
	                    	 "skill": "HTML"
	                     },
	                     {
	                    	 "skill": "CSS"
	                     },
	                     {
	                    	 "skill": "Node.js"
	                     },
	                     {
	                    	 "skill": "Angular.js"
	                     },
	                     {
	                    	 "skill": "Bootstrap"
	                     },
	                     {
	                    	 "skill": "XML"
	                     },
	                     {
	                    	 "skill": "Unix"
	                     },
	                     {
	                    	 "skill": "Windows"
	                     },
	                     {
	                    	 "skill": "Oracle"
	                     },
	                     {
	                    	 "skill": "SQL Server"
	                     }
	                     ];

	
	$scope.techSkills1 = [
	                     {
	                    	 "skill": "SVN"
	                     },
	                     {
	                    	 "skill": "Git"
	                     },
	                     {
	                    	 "skill": "Ant"
	                     },
	                     {
	                    	 "skill": "JProfiler"
	                     },
	                     {
	                    	 "skill": "Jenkins"
	                     },
	                     {
	                    	 "skill": "VIM"
	                     },
	                     {
	                    	 "skill": "JIRA"
	                     },
	                     {
	                    	 "skill": "PowerBuilder"
	                     },
	                     {
	                    	 "skill": "JSON"
	                     },
	                     {
	                    	 "skill": "RabbitMQ"
	                     },
	                     {
	                    	 "skill": "REST"
	                     },
	                     {
	                    	 "skill": "SOAP"
	                     },
	                     {
	                    	 "skill": "Redis"
	                     },
	                     {
	                    	 "skill": "AWS"
	                     },
	                     {
	                    	 "skill": "Heroku"
	                     }
	                     ];
	
	
	$scope.techSkills2 = [
		                     {
		                    	 "skill": "amCharts"
		                     },
		                     {
		                    	 "skill": "Tomcat"
		                     },
		                     {
		                    	 "skill": "Docker"
		                     },
		                     {
		                    	 "skill": "JQuery"
		                     },
		                     {
		                    	 "skill": "Spring"
		                     },
		                     {
		                    	 "skill": "Hibernate"
		                     },
		                     {
		                    	 "skill": "Struts"
		                     },
		                     {
		                    	 "skill": "Ruby"
		                     },
		                     {
		                    	 "skill": "Rails"
		                     },
		                     {
		                    	 "skill": "Python"
		                     },
		                     {
		                    	 "skill": "Netty"
		                     },
		                     {
		                    	 "skill": "Open MPI"
		                     },
		                     {
		                    	 "skill": "Virtualization"
		                     },
		                     {
		                    	 "skill": "Linux"
		                     },
		                     {
		                    	 "skill": "KVM"
		                     }
		                     ];
	
	
	$scope.techSkills3 = [
		                     {
		                    	 "skill": "spark"
		                     },
		                     {
		                    	 "skill": "scala"
		                     },
		                     {
		                    	 "skill": "Autocad"
		                     },
		                     {
		                    	 "skill": "CAN"
		                     },
		                     {
		                    	 "skill": "3d Drawing"
		                     },
		                     {
		                    	 "skill": "Machine Learning"
		                     },
		                     {
		                    	 "skill": "AI"
		                     },
		                     {
		                    	 "skill": "NLP"
		                     },
		                     {
		                    	 "skill": "Embedded"
		                     },
		                     {
		                    	 "skill": "Backbone.js"
		                     },
		                     {
		                    	 "skill": "ReactJS"
		                     },
		                     {
		                    	 "skill": "Word"
		                     },
		                     {
		                    	 "skill": "Excel"
		                     },
		                     {
		                    	 "skill": "Powerpoint"
		                     },
		                     {
		                    	 "skill": "Data Science"
		                     }
		                     ];
	
	
	$scope.data = [];
	$scope.data1 = [];
	$scope.selectedKeywords = [];

	$scope.sync = function(bool, item){
		if(bool){
			// add item
			$scope.data.push(item);
			$scope.selectedKeywords.push(item.skill);
		} else {
			// remove item
			for(var i=0 ; i < $scope.data.length; i++) {
				if($scope.data[i].skill == item.skill){
					$scope.data.splice(i,1);
					$scope.selectedKeywords.splice(i,1);
				}
			}      
		}
		
		
	};
	
	$scope.appendKeyword = function(){
		
		var skillJson = {"skill": $scope.customKeyword};
		//skillJson.skill = $scope.customKeyword;
		$scope.data.push(skillJson);
		$scope.selectedKeywords.push(skillJson.skill);
		//console.log("keywords: " + $scope.data1);
		$scope.customKeyword = "";
	};

	$scope.search = function(){
		$scope.skills = false;
		console.log("searching!!!");

		//$scope.data1 = [];
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

		var downloadURL = 'http://localhost:8080/ResumeRankerServer/DownloadResume?fileName=';
		var fileName = Name;

		var finalDownloadURL = downloadURL.concat(fileName);
		console.log(finalDownloadURL);

		$window.open(finalDownloadURL);

	};
	
	$scope.saveConfiguration = function(Name) {
		
		var CommaFormattedKeywords = "";
		
		for(var i=0 ; i < $scope.selectedKeywords.length; i++) {
			
			if (i!==$scope.selectedKeywords.length-1){
				CommaFormattedKeywords = CommaFormattedKeywords + $scope.selectedKeywords[i] + ",";
			}
			else{
				CommaFormattedKeywords = CommaFormattedKeywords + $scope.selectedKeywords[i];
			}
		}
		
		var configurationDetails = {"jobId": $scope.jobId, "jobTitle": $scope.jobTitle, "keywords": CommaFormattedKeywords};

		$http.post("/SaveProfile", configurationDetails)
		.success(function(data,status) {
			if (data === "SUCCESS")
			{
				console.log("Success returned from SaveProfile Function");
			}
			else
			{
				console.log("Error returned from SaveProfile Function");
			}
		});
		
	};
	

});