console.log('Hello Webpack Configuration!!');

module.exports = (function() {
    var option = {};
    
    option.entry = './entry.js';
    
    option.output = {
        filename: 'bundle.js'
    };
    
    return option;
})();
