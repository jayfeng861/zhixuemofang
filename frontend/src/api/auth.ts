import request from './request'

export function login(data: { username: string; password: string }) {
  return request.post('/v1/auth/login', data)
}

export function register(data: { username: string; password: string; nickname: string; grade: string }) {
  return request.post('/v1/auth/register', data)
}

export function getUserInfo() {
  return request.get('/v1/auth/info')
}

export function updateProfile(data: Record<string, string>) {
  return request.put('/v1/user/profile', data)
}
