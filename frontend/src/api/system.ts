import request from './request'

export function getAnnouncements() {
  return request.get('/v1/system/announcements')
}

export function getFeedbackTypes() {
  return request.get('/v1/system/feedback-types')
}

export function submitFeedback(data: Record<string, string>) {
  return request.post('/v1/system/feedback', data)
}
