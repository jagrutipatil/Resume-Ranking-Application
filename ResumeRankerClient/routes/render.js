exports.index = function(req, res){
	console.log("Index working!!!");
	res.render("index");
};

exports.home = function(req, res){
	console.log("Home working!!!");
	
	if (req.session.emailId){
		res.render("home");
	}
	else{
		res.render("login");
	}
};

exports.login = function(req, res){
	console.log("Login working!!!");
	res.render("login");
};	

exports.filter = function(req, res){
	console.log("Filter working!!!");
	
	if (req.session.emailId){
		res.render("filter");
	}
	else{
		res.render("login");
	}
	
};

exports.configuration = function(req, res){
	console.log("configuration working!!!");
	
	if (req.session.emailId){
		res.render("configuration");
	}
	else{
		res.render("login");
	}
	
};