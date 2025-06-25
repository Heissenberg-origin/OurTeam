import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import http from './api/axios'
const app = createApp(App)
app.use(router)  
app.use(ElementPlus)
app.config.globalProperties.$http = http // 将http挂载到全局属性上
app.mount('#app')
