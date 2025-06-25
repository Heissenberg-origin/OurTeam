// src/api/axios.js
// src/api/axios.js
import axios from 'axios'

// 使用 import.meta.env 代替 process.env
const http = axios.create({
    baseURL: 'http://127.0.0.1:8080',
    timeout:10000,
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded',
      Accept: 'application/json'
    }
})
export default http
// 其余代码保持不变...

// 请求拦截器
// instance.interceptors.request.use(
//   config => {
//     // 可以在这里添加token等
//     return config
//   },
//   error => {
//     return Promise.reject(error)
//   }
// )

// // 响应拦截器
// instance.interceptors.response.use(
//   response => {
//     return response.data
//   },
//   error => {
//     return Promise.reject(error)
//   }
// )

// export default instance