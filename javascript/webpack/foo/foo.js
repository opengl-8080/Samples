module.exports = function() {
    var absolutePath = require('absolutePath');
    console.log('foo.absolutePath = ' + absolutePath);
    
    var relativePath = require('relativePath');
    console.log('foo.relativePath = ' + relativePath);
};
