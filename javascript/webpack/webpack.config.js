var path = require('path');

console.log(__dirname);
console.log(path.resolve(__dirname, './foo/bar'));

module.exports = {
    entry: './entry.js',
    output: {
        filename: 'bundle.js'
    }
};
