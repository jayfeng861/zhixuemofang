import axios from 'axios'
import { ElMessage } from 'element-plus'
import { getToken, removeToken, getAdminToken, removeAdminToken } from '@/utils/auth'

const request = axios.create({
  baseURL: '/api',
  timeout: 15000,
  headers: {
    'Content-Type': 'application/json'
  }
})

request.interceptors.request.use(
  (config) => {
    const url = config.url || ''
    // 管理后台API使用管理员token
    if (url.includes('/v1/admin/')) {
      const adminToken = getAdminToken()
      if (adminToken) {
        config.headers.Authorization = `Bearer ${adminToken}`
      }
    } else {
      // 用户API使用用户token
      const token = getToken()
      if (token) {
        config.headers.Authorization = `Bearer ${token}`
      }
    }
    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)

request.interceptors.response.use(
  (response) => {
    return response.data
  },
  (error) => {
    const { response } = error
    if (response) {
      switch (response.status) {
        case 401:
          // 判断是管理后台还是用户端
          const requestUrl = response.config?.url || ''
          if (requestUrl.includes('/v1/admin/')) {
            removeAdminToken()
            if (window.location.pathname.startsWith('/admin')) {
              window.location.href = '/admin/login'
            }
          } else {
            removeToken()
            if (!window.location.pathname.startsWith('/login')) {
              window.location.href = '/login'
            }
          }
          ElMessage.error('登录已过期，请重新登录')
          break
        case 403:
          ElMessage.error('没有权限访问')
          break
        case 500:
          ElMessage.error('服务器错误，请稍后重试')
          break
        default:
          ElMessage.error((response.data && response.data.message) || '请求失败')
      }
    } else {
      ElMessage.error('网络连接异常，请检查网络')
    }
    return Promise.reject(error)
  }
)

export default request
