var path = require('path');

module.exports = {
    entry: 'entry.js',
    output: {
        filename: 'bundle.js'
    },
    resolve: {
        root: [path.resolve(__dirname, './app')]
    }
};
