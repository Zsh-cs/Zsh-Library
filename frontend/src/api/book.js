import request from '@/utils/request'

export function addBook(payload) {
  return request.post('/admin/book', payload)
}
