
/**
 * Module dependencies.
 */

var express = require('express')
  , routes = require('./routes')
  , user = require('./routes/user')
  , render = require('./routes/render')
  , resume = require('./routes/resume')
  , http = require('http')
  , path = require('path')
  //Importing the 'client-sessions' module
  , session = require('client-sessions');

var app = express();

//configure the sessions with our application
app.use(session({   
	  
	cookieName: 'session',    
	secret: 'Resume_Ranker',    
	duration: 30 * 60 * 1000,    
	activeDuration: 5 * 60 * 1000,  }));

// all environments
app.set('port', process.env.PORT || 3000);
app.set('views', __dirname + '/views');
app.set('view engine', 'ejs');
app.use(express.favicon());
app.use(express.logger('dev'));
app.use(express.bodyParser());
app.use(express.methodOverride());
app.use(app.router);
app.use(express.static(path.join(__dirname, 'public')));

// development only
if ('development' === app.get('env')) {
  app.use(express.errorHandler());
}

/************* GET REQUESTS **************/

app.get('/', render.index);
app.get('/home', render.home);
app.get('/login', render.login);
app.get('/filter', render.filter);
app.get('/configuration', render.configuration);
app.get('/logout', user.logout);

/************* POST REQUESTS **************/

app.post('/login', user.login);
app.post('/register', user.register);
app.post('/SearchResume', resume.SearchResume);
app.post('/SaveProfile', resume.SaveProfile);
app.post('/ConfigurationList', resume.ConfigurationList);

http.createServer(app).listen(app.get('port'), function(){
  console.log('Express server listening on port ' + app.get('port'));
});
