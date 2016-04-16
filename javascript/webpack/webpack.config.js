module.exports = {
    entry: './entry.js',
    output: {
        filename: './bundle.js'
    },
    module: {
        loaders: [
            {test: /\.json$/, loader: 'json'}
        ]
    }
};