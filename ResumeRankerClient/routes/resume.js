var Client = require('node-rest-client').Client;

exports.SearchResume = function(req, res){
	  
	console.log("Entered 'SearchResume' function on Node Server..");
	
	var client = new Client();
	
	var DesriredSkills = req.param("skill");
	
	// set content-type header and data as json in args parameter 
	var args = {
		data: { "skill": DesriredSkills },
		headers: { "Content-Type": "application/json" }
	};
	 
	client.post("http://localhost:8080/ResumeRankerServer/SearchResumes", args, function (data, response) {
 
		console.log("POST request from 'SearchResume' working fine...!!!");
		console.log("data " + JSON.stringify(data));
		res.end(JSON.stringify(data));
		
	});
	
};