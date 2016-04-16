module.exports = function() {
    var absolutePath = require('absolutePath');
    console.log('bar.absolutePath = ' + absolutePath);
    
    var relativePath = require('relativePath');
    console.log('bar.relativePath = ' + relativePath);
};
