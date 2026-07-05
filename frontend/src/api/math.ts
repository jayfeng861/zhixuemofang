import request from './request'

export function getWrongQuestions(params?: Record<string, any>) {
  return request.get('/v1/math/wrong-questions', { params })
}

export function getWrongQuestionAnalysis() {
  return request.get('/v1/math/wrong-analysis')
}

export function getGeometryData() {
  return request.get('/v1/math/geometry')
}

export function saveGeometry(data: Record<string, any>) {
  return request.post('/v1/math/geometry', data)
}
