var webpack = require("webpack");

module.exports = {
    entry: './entry.js',
    output: {
        filename: 'bundle.js'
    },
    devtool: 'source-map',
    plugins: [
        new webpack.optimize.UglifyJsPlugin()
    ]
};
