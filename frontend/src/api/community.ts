import request from './request'

export function getPostList(params?: Record<string, any>) {
  return request.get('/v1/community/posts', { params })
}

export function getPostDetail(id: number) {
  return request.get(`/v1/community/posts/${id}`)
}

export function createPost(data: { title: string; content: string; category: string }) {
  return request.post('/v1/community/posts', data)
}

export function addComment(postId: number, content: string) {
  return request.post(`/v1/community/posts/${postId}/comments`, { content })
}

export function likePost(postId: number) {
  return request.post(`/v1/community/posts/${postId}/like`)
}
