const BASE_URL = '/api/v1/posts'

export function getAllPosts() {
  return fetch(BASE_URL).then(r => r.json())
}

export function createPost(title, content) {
  return fetch(BASE_URL, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ title, content })
  }).then(r => r.json())
}

export function updatePost(id, title, content) {
  return fetch(`${BASE_URL}/${id}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ title, content })
  }).then(r => r.json())
}

export function deletePost(id) {
  return fetch(`${BASE_URL}/${id}`, { method: 'DELETE' })
}
