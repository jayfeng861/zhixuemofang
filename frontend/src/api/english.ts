import request from './request'

export function getVocabularyList(params?: Record<string, any>) {
  return request.get('/v1/english/vocabulary', { params })
}

export const fetchVocabularyList = getVocabularyList

export function submitVocabularyTest(data: Record<string, any>) {
  return request.post('/v1/english/vocabulary/test', data)
}

export function getOralTopics() {
  return request.get('/v1/english/oral/topics')
}

export function submitOralPractice(data: { topicId: number; audio?: string; text: string }) {
  return request.post('/v1/english/oral/practice', data)
}

export function getOralHistory(params?: Record<string, any>) {
  return request.get('/v1/english/oral/history', { params })
}
