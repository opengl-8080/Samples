module.exports = {
    entry: './entry.js',
    output: {
        filename: './bundle.js'
    },
    module: {
        loaders: [
            {test: /\.json$/, loader: 'to-string!json'}
        ]
    }
};