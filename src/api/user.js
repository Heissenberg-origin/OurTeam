// src/api/user.js
import http from './axios'  // 使用你全局挂载的http实例

export function getHello() {
  return http.get('/user/hello')
}

export function getHello2() {
  return http.get('/user/hello2')
}

// 添加登录接口
export function login(data) {
  return http.post('/user/login', data, {
    headers: {
      'Content-Type': 'application/json' // 覆盖默认的x-www-form-urlencoded
    }
  })
}