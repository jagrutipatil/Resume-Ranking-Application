
/*
 * GET home page.
 */

var Client = require('node-rest-client').Client;

exports.index = function(req, res){
  res.render("index");
};

/*exports.index1 = function(req, res){
	  res.end("Healthy!!!");
};*/

exports.home = function(req, res){
		console.log("Home working!!!");
	  res.render("home");
	};
	
exports.filter = function(req, res){
	console.log("Filter working!!!");
  res.render("filter");
};

exports.login = function(req, res){
	console.log("Login working!!!");
	res.render("login");
};


exports.SearchResume = function(req, res){
	  
	console.log("Entered 'SearchResume' function on Node Server..");
	
	var client = new Client();
	
	var DesriredSkills = req.param("skill");
	
	// set content-type header and data as json in args parameter 
	var args = {
		data: { "skill": DesriredSkills },
		headers: { "Content-Type": "application/json" }
	};
	 
	client.post("http://localhost:8080/Client1/RenderClient", args, function (data, response) {
 
		console.log("POST request from 'SearchResume' working fine...!!!");
		console.log("data " + JSON.stringify(data));
		res.end(JSON.stringify(data));
		
	});
	
};