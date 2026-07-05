import request from './request'

const ADMIN_PREFIX = '/v1/admin'

// 管理员登录
export function adminLogin(data: { username: string; password: string }) {
  return request.post(`${ADMIN_PREFIX}/login`, data)
}

// 获取当前管理员信息
export function getAdminInfo() {
  return request.get(`${ADMIN_PREFIX}/info`)
}

// 管理员列表（分页）
export function getAdminList(params: Record<string, any> = {}) {
  return request.get(`${ADMIN_PREFIX}/list`, { params })
}

// 添加管理员
export function addAdmin(data: Record<string, any>) {
  return request.post(`${ADMIN_PREFIX}/add`, data)
}

// 修改管理员信息
export function updateAdmin(data: Record<string, any>) {
  return request.put(`${ADMIN_PREFIX}/update`, data)
}

// 修改管理员密码
export function updateAdminPassword(data: { oldPassword: string; newPassword: string }) {
  return request.put(`${ADMIN_PREFIX}/password`, data)
}

// 删除管理员
export function deleteAdmin(id: number) {
  return request.delete(`${ADMIN_PREFIX}/${id}`)
}

// 用户列表（分页，支持角色筛选）
export function getUserList(params: Record<string, any> = {}) {
  return request.get(`${ADMIN_PREFIX}/users/list`, { params })
}

// 用户详情
export function getUserDetail(id: number) {
  return request.get(`${ADMIN_PREFIX}/users/${id}`)
}

// 修改用户信息
export function updateUser(data: Record<string, any>) {
  return request.put(`${ADMIN_PREFIX}/users/update`, data)
}

// 修改用户密码
export function updateUserPassword(data: { userId: number; newPassword: string }) {
  return request.put(`${ADMIN_PREFIX}/users/password`, data)
}

// 删除用户
export function deleteUser(id: number) {
  return request.delete(`${ADMIN_PREFIX}/users/${id}`)
}

// 系统设置列表
export function getSettingList(params: Record<string, any> = {}) {
  return request.get(`${ADMIN_PREFIX}/settings/list`, { params })
}

// 更新单个设置
export function updateSetting(data: { key: string; value: string }) {
  return request.put(`${ADMIN_PREFIX}/settings/update`, data)
}

// 批量更新设置
export function batchUpdateSetting(data: Record<string, string>) {
  return request.put(`${ADMIN_PREFIX}/settings/batch`, data)
}
