module.exports = {
    entry: './entry.ts',
    output: {
        filename: 'bundle.js'
    },
    resolve: {
        extensions: ['', '.ts', '.js']
    },
    modules: {
        loaders: [
            {
                test: /\.ts$/,
                loader: 'awesome-typescript-loader'
            }
        ]
    }
};
