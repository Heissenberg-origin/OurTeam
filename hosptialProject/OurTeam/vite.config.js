import { fileURLToPath, URL } from 'node:url'
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'

// https://vite.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    vueDevTools(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  server: {  // 这是你需要添加的部分
    proxy: {
      // 代理所有以/user开头的请求
      '/user': {
        target: 'http://127.0.0.1:8080',  // 你的后端地址
        changeOrigin: true,               // 需要跨域
        // rewrite: (path) => path.replace(/^\/user/, '') // 如果需要重写路径
      },
      // 可以添加其他代理规则
      '/api': {
        target: 'http://127.0.0.1:8080',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, '')
      }
    }
  }
})