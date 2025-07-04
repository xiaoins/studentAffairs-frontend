const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    port: 8082,
    proxy: {
      '/api': {
        target: 'http://localhost:8081',
        pathRewrite: {'^/api': ''},
        changeOrigin: true,
        secure: false
      }
    }
  }
})
