var path = require('path');

module.exports = {
    entry: './entry.js',
    output: {
        filename: 'bundle.js'
    },
    resolve: {
        alias: {
            absolutePath: path.resolve(__dirname, './value.js'),
            relativePath: './value.js'
        }
    }
};
