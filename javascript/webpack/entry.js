var json = require('json!./sample.json');
console.log(typeof json);
console.log(json);

var tostring = require('to-string!json!./sample.json');
console.log(typeof tostring);
console.log(tostring);
