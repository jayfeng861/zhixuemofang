import request from './request'

export function getDashboardStats() {
  return request.get('/v1/dashboard/stats')
}

export function getWeeklyData() {
  return request.get('/v1/dashboard/weekly')
}

export function getKnowledgeHeatmap() {
  return request.get('/v1/dashboard/knowledge-heatmap')
}

export function getRankingList(params?: Record<string, any>) {
  return request.get('/v1/dashboard/ranking', { params })
}

export function fetchStats() {
  return request.get('/v1/home/stats')
}

export function fetchAnnouncements() {
  return request.get('/v1/home/announcements')
}