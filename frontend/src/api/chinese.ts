import request from './request'

export function getWenyanwenList(params?: Record<string, any>) {
  return request.get('/v1/chinese/wenyanwen', { params })
}

export const fetchWenyanwenList = getWenyanwenList

export function getWenyanwenDetail(id: number) {
  return request.get(`/v1/chinese/wenyanwen/${id}`)
}

export function submitWenyanwenAnswer(id: number, data: Record<string, any>) {
  return request.post(`/v1/chinese/wenyanwen/${id}/answer`, data)
}

export function submitEssay(data: { title: string; content: string; type: string }) {
  return request.post('/v1/chinese/essay', data)
}

export function getEssayList(params?: Record<string, any>) {
  return request.get('/v1/chinese/essay', { params })
}

export function getEssayDetail(id: number) {
  return request.get(`/v1/chinese/essay/${id}`)
}
