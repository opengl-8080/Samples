var webpack = require("webpack");

module.exports = {
    entry: './entry.js',
    output: {
        filename: 'bundle.js'
    },
    plugins: [
        new webpack.optimize.UglifyJsPlugin()
    ]
};
