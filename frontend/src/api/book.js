import request from '@/utils/request'

export function addBook(payload) {
  return request.post('/admin/book', payload)
}

export function deleteBook() {
  return request.delete('/admin/book')
}

export function listBooks() {
  return request.get('/admin/book/list')
}


