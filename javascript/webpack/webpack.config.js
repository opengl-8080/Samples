var path = require('path');

module.exports = {
    entry: './entry.js',
    output: {
        filename: './bundle.js'
    },
    module: {
        loaders: [
            {
                test: /\.json$/,
                include: [
                    path.resolve(__dirname, 'foo')
                ],
                loader: 'raw'
            },
            {
                test: /\.json$/,
                include: [
                    /bar/
                ],
                loader: 'json'
            },
        ]
    }
};