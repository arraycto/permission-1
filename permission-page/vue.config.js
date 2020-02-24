module.exports = {
    devServer: {
        host: 'localhost',
        port: 8887,
        proxy: {
            '/': {
                ws: false,
                target: process.env.VUE_APP_BASE_API,
                changeOrigin: true,
                pathRewrite: {
                    '^/': ''
                }
            }
        }
    }
}
