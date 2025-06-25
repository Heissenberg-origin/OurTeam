import http from './axios'

export const login = (data) => {
  return http.post('/api/auth/login', null, {
    params: {
      id: data.id,
      password: data.password
    }
  })
}


export const changePassword = (data) => {
  return http.put('/api/auth/password', null, {
    params: {
      id: data.id,
      oldPassword: data.oldPassword,
      newPassword: data.newPassword
    }
  })
}