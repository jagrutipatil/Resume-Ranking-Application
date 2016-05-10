exports.index = function(req, res){
	console.log("Index working!!!");
	res.render("index");
};

exports.home = function(req, res){
	console.log("Home working!!!");
	res.render("home");
};

exports.login = function(req, res){
	console.log("Login working!!!");
	res.render("login");
};	

exports.filter = function(req, res){
	console.log("Filter working!!!");
	res.render("filter");
};