
/**
 * Module dependencies.
 */

var express = require('express')
  , routes = require('./routes')
  , user = require('./routes/user')
  , render = require('./routes/render')
  , resume = require('./routes/resume')
  , http = require('http')
  , path = require('path');

var app = express();

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


/************* POST REQUESTS **************/

app.post('/login', user.login);
app.post('/register', user.register);
app.post('/SearchResume', resume.SearchResume);

http.createServer(app).listen(app.get('port'), function(){
  console.log('Express server listening on port ' + app.get('port'));
});
