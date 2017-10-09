const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const HtmlWebpackPluginConfig = new HtmlWebpackPlugin({
    template: './source/index.html',
    filename: 'index.html',
    inject: 'body'
})
const CopyWebpackPlugin = require('copy-webpack-plugin');


module.exports = {
    entry: './source/app.js',
    output: {
        path: path.resolve('dist'),
        filename: 'app.js'
    },
    module: {
        loaders: [
            { test: /\.js$/, loaders: ['react-hot-loader/webpack', 'babel-loader'], exclude: /node_modules/ },
            { test: /\.jsx$/, loaders: ['react-hot-loader/webpack', 'babel-loader'], exclude: /node_modules/ },
            { test: /\.css$/,  loaders: ['style-loader', 'css-loader'] },
            { test: /\.(png|woff|woff2|eot|ttf|svg)$/, loader: 'url-loader?limit=100000' }
        ]
    },
    resolve: {
        extensions: ['.js', '.jsx', '.json']
    },
    plugins: [
        HtmlWebpackPluginConfig,
        new CopyWebpackPlugin( [ { from: 'images/cbm.png', to: 'images/cbm.png' } ])
    ]
}