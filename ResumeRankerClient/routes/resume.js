var Client = require('node-rest-client').Client;

exports.SearchResume = function(req, res){
	  
	console.log("Entered 'SearchResume' function on Node Server..");
	
	var client = new Client();
	
	var DesriredSkills = req.param("skill");
	var previousEmployer = req.param("previousEmployer");
	var minGPA = req.param("minGPA");
	var maxGPA = req.param("maxGPA");
	var masters = req.param("masters");
	var bachlors = req.param("bachlors");
	var experience = req.param("experience");
	
	// set content-type header and data as json in args parameter 
	var args = {
		data: { "skill": DesriredSkills,  
				"previousEmployer" : previousEmployer, 
				"minGPA" : minGPA,
				"maxGPA" : maxGPA,
				"masters" : masters,
				"bachlors" : bachlors,
				"experience" : experience
			  },
		headers: { "Content-Type": "application/json" }
	};
	 
	client.post("http://localhost:8080/ResumeRankerServer/SearchResumes", args, function (data, response) {
 
		console.log("POST request from 'SearchResume' working fine...!!!");
		console.log("data " + JSON.stringify(data));
		res.end(JSON.stringify(data));
		
	});
	
};

exports.SaveProfile = function(req, res){
	  
	console.log("Entered 'SaveProfile' function on Node Server..");
	
	var client = new Client();
	
	// set content-type header and data as json in args parameter 
	var args = {
		data: { "jobId": req.param("jobId"), "jobTitle": req.param("jobTitle"), "keywords": req.param("keywords"), "emailId": req.session.emailId},
		headers: { "Content-Type": "application/json" }
	};
	 
	client.post("http://localhost:8080/ResumeRankerServer/SaveConfiguration", args, function (data, response) {
 
		console.log("POST request from 'SaveProfile' working fine...!!!");
		console.log("Response: " + response.statusCode);
		
		if (response.statusCode){
			res.end("SUCCESS");
		}
		else{
			res.end("NO SUCCESS");
		}
		
	});
	
};

exports.ConfigurationList = function(req, res){
	  
	console.log("Entered 'ConfigurationList' function on Node Server..");
	
	var client = new Client();
	
	// set content-type header and data as json in args parameter 
	var args = {
		data: { "emailId": req.session.emailId},
		headers: { "Content-Type": "application/json" }
	};
	 
	client.post("http://localhost:8080/ResumeRankerServer/GetConfiguration", args, function (data, response) {
 
		console.log("POST request from 'ConfigurationList' working fine...!!!");
		console.log("Configuration List Data " + JSON.stringify(data));
		res.end(JSON.stringify(data));
		
	});
	
};