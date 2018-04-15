'use strict';

var path = require('path');
var express = require('express');
var bodyParser = require('body-parser');
var app = express();

app.set('port', 3000);
app.set('host', 'localhost');
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
app.use(express.static(path.join(__dirname, './view')));

app.listen(app.get('port'), function() {
	console.log('Iniciado na porta: ' + app.get('port'));
});