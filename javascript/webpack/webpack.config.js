var CommonsChunkPlugin = require("webpack/lib/optimize/CommonsChunkPlugin");

module.exports = {
    entry: {
        entry1: './entry1.js',
        entry2: './entry2.js',
        entry3: './entry3.js'
    },
    output: {
        path: 'output',
        filename: '[name].bundle.js'
    },
    plugins: [
        new CommonsChunkPlugin('common.js')
    ]
};
