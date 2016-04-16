module.exports = {
    entry: './entry.js',
    output: {
        filename: 'bundle.js'
    },
    resolve: {
        alias: {
            foo: 'foo.js'
        }
    }
};
