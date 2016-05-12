var Client = require('node-rest-client').Client;

exports.login = function(req, res){
	  
	console.log("Entered 'login' function on Node Server..");
	
	var client = new Client();
	
	var loginData = {email: req.param('loginEmail'), password: req.param('loginPass')};
	
	var args = {
		data: { "login": loginData },
		headers: { "Content-Type": "application/json" }
	};
	 
	client.post("http://localhost:8080/Client1/login", args, function (data, response) {
 
		/*console.log("POST request from 'SearchResume' working fine...!!!");
		console.log("data " + JSON.stringify(data));
		res.end(JSON.stringify(data));*/
		
	});
	
};

exports.register = function(req, res){
	  
	console.log("Entered 'register' function on Node Server..");
	
	var client = new Client();
	
	//var registerData = {emailId: req.param('registerEmail'), password: req.param('registerPass')};
	
	var args = {
		data: {emailId: req.param('registerEmail'), password: req.param('registerPass')},
		headers: { "Content-Type": "application/json" }
	};
	 
	client.post("http://localhost:8080/Client1/RegisterServlet", args, function (data, response) {
 
		console.log("Response from RegisterServlet: " + response);
		
	});
	
};