var Client = require('node-rest-client').Client;

exports.login = function(req, res){
	  
	console.log("Starting login on webserver..");
	
	var client = new Client();
	
	var args = {
		data: {emailId: req.param('loginEmail'), password: req.param('loginPass')},
		headers: { "Content-Type": "application/json" }
	};
	 
	client.post("http://localhost:8080/ResumeRankerServer/LoginServlet", args, function (data, response) {
 
		if (response.statusCode === 200){
			req.session.emailId = req.param('loginEmail');
			console.log("success");
			res.end("SUCCESS");
		}
		else{
			res.end("NO SUCCESS");
		}
	});
	
};


exports.logout = function(req, res){
	  
	console.log("Entered 'logout' function on Node Server..");
	
	req.session.destroy();
	
	res.render("index");
};

exports.register = function(req, res){
	  
	console.log("Entered 'register' function on Node Server..");
	
	var client = new Client();
	
	var args = {
		data: {emailId: req.param('registerEmail'), password: req.param('registerPass')},
		headers: { "Content-Type": "application/json" }
	};
	 
	client.post("http://localhost:8080/ResumeRankerServer/RegisterServlet", args, function (data, response) {
 
		if (response.statusCode === 200){
			req.session.emailId = req.param('registerEmail');
			res.end("SUCCESS");
		}
		else{
			res.end("NO SUCCESS");
		}
		
	});
	
};