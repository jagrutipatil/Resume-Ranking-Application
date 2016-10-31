var myApp = angular.module("filterApp", []);

myApp.controller('filterController', function($scope, $http, $window) {

	$scope.analysisSoftware = ["ANSYS", "COSMOS", "Flottem", "Fluent", "ICEPAK"];	
	$scope.cad = ["AutoCad", "Creo", "ProE", "Solidworks"];
	$scope.cloudTechnologies = ["AWS", "Bluemix", "Cloud9", "Heroku"];
	$scope.databases = ["Cassandra", "CouchDB", "DB2", "MongoDB", "MySQL", "Oracle", "PostgreSQL", "Redis", "Spanner", "Vertica"];
	$scope.microControllers = ["Arduino", "Atmel", "Beaglebone Black", "PSOQ", "Raspberry Pi"];
	$scope.operatingSystems = ["Linux", "OS X", "Windows"];
	$scope.other = ["Microsoft Excel", "Microsoft Office", "Microsoft PowerPoint", "Microsoft Word"];
	$scope.programmingLang = ["C++", "C#", "Java", "Pyhton", "Ruby", "Scala", "SQL"];
	$scope.tools = ["Ant", "Git", "Jenkins", "JIRA", "JProfiler", "SVN"];
	$scope.webDevelopment = ["amCharts", "Angular.js", "Backbone.js", "Bootstrap", "CSS", "Hibernate", "HTML", "JavaScript", "JSON", "JQuery", "Node.js", "PHP", "Rails", "REST", "ReactJS", "SOAP", "Spring", "Struts", "Tomcat", "XML"];
	$scope.previousEmployer = ["Google", "Apple", "Facebook", "Amazon", "Uber", "Tesla" , "Cisco", "Paypal"];
	$scope.selected = [];
	$scope.selectedEmployer = [];
	
	$scope.categories = true;
	$scope.skills = true;
	$scope.customKey = true;
	$scope.searchButton = true;
	$scope.resumeList = false;

	$scope.bachlorsText = "No";
	$scope.mastersText = "No";
	$scope.experienceText = "No";

	$scope.updateSkillAndEmpOP = function(operation) {
	  $scope.opSKillEmp = operation;
	  console.log($scope.opSKillEmp);
	}; 	

	var updateSelected = function(action, id) {
	  if (action === 'add' && $scope.selected.indexOf(id) === -1) {
	    $scope.selected.push(id);
	  }
	  if (action === 'remove' && $scope.selected.indexOf(id) !== -1) {
	    $scope.selected.splice($scope.selected.indexOf(id), 1);
	  }
	};

	$scope.updateSelection = function($event, id) {
	  var checkbox = $event.target;
	  var action = (checkbox.checked ? 'add' : 'remove');
	  updateSelected(action, id);
	}; 	

	var updateSelectedPEmployer = function(action, id) {
	  if (action === 'add' && $scope.selectedEmployer.indexOf(id) === -1) {
	    $scope.selectedEmployer.push(id);
	  }
	  if (action === 'remove' && $scope.selectedEmployer.indexOf(id) !== -1) {
	    $scope.selectedEmployer.splice($scope.selectedEmployer.indexOf(id), 1);
	  }
	};

	$scope.enableExpereince = function() {
		$scope.experience = !$scope.experience;
		$scope.experienceText = ($scope.experience ? 'Yes' : 'No');
	}

	$scope.updatePreviousEmployer = function($event, id) {
	  var checkbox = $event.target;
	  var action = (checkbox.checked ? 'add' : 'remove');
	  updateSelectedPEmployer(action, id);
	}; 		

	$scope.hasMasters = function($event) {
		var checkbox = $event.target;
		$scope.masters = checkbox.checked;
		$scope.mastersText = ($scope.masters ? 'Yes' : 'No');
	};

	$scope.hasBachlors = function($event) {
		var checkbox = $event.target;
		$scope.bachlors = checkbox.checked;
		$scope.bachlorsText = ($scope.bachlors ? 'Yes' : 'No');
	};

	$scope.appendKeyword = function(){
		
		$scope.selected.push($scope.customKeyword);
		$scope.customKeyword = "";
	};
	
	$scope.appendPEmployer = function(){
		
		$scope.selectedEmployer.push($scope.customKeywordEmployer);
		$scope.customKeywordEmployer = "";
	};
	$scope.saveConfiguration = function(Name) {
		
		var CommaFormattedKeywords = "";
		
		for(var i=0 ; i < $scope.selected.length; i++) {
			
			if (i!==$scope.selected.length-1){
				CommaFormattedKeywords = CommaFormattedKeywords + $scope.selected[i] + ",";
			}
			else{
				CommaFormattedKeywords = CommaFormattedKeywords + $scope.selected[i];
			}
		}
		
		var configurationDetails = {"jobId": $scope.jobId, "jobTitle": $scope.jobTitle, "keywords": CommaFormattedKeywords};

		$http.post("/SaveProfile", configurationDetails)
		.success(function(data,status) {
			if (data === "SUCCESS")
			{
				console.log("Success returned from SaveProfile Function");
				$scope.saveStatus = "Configuration saved successfully!!!";
			}
			else
			{
				console.log("Error returned from SaveProfile Function");
				$scope.saveStatus = "Problem in saving configuration!!!";
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
	
	$scope.search = function(){
		console.log("searching!!!");

		var keywords = {
						 "skill": $scope.selected,
		 				 "previousEmployer": $scope.selectedEmployer,
		 				 "skill_op_emp" : $scope.opSKillEmp,
		 				 "minGPA" : $scope.minGPA,
		 				 "maxGPA" : $scope.maxGPA,
		 				 "masters" : $scope.masters,
		 				 "bachlors" : $scope.bachlors,
		 				 "experience": $scope.experience
		 				};

		$scope.filesList = [];

		$http.post("/SearchResume", keywords)
		.success(function(data,status) {
			if (status === 200) {
				console.log("Success returned from searchResume Function");
				console.log(data);
				console.log(status);

				var i = 0;
				for (i=0; data.files != null && i<data.files.length; i++){
					var fileDetails = {};
					fileDetails["Name"] = data.files[i];
					$scope.filesList.push(fileDetails);
				}

				$scope.categories = false;
				$scope.skills = false;
				$scope.customKey = false;
				$scope.searchButton = false;
				$scope.resumeList = true;
				
			} else {
				console.log("Error returned from searchResume Function");
			}
		});
	};
	

});