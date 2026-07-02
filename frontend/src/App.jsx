import { useState, useEffect } from 'react'
import PostCard from './PostCard.jsx'

function App() {
  const [posts, setPosts] = useState([])
  const [title, setTitle] = useState('')
  const [content, setContent] = useState('')

  useEffect(() => {
    fetch('/api/v1/posts')
      .then(response => response.json())
      .then(data => setPosts(data))
  }, [])

  function handleSubmit(e) {
    e.preventDefault()
    fetch('/api/v1/posts', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ title, content })
    })
      .then(response => response.json())
      .then(newPost => {
        setPosts([...posts, newPost])
        setTitle('')
        setContent('')
      })
  }

  function handleUpdate(updatedPost) {
    setPosts(posts.map(p => p.id === updatedPost.id ? updatedPost : p))
  }

  function handleDelete(id) {
    setPosts(posts.filter(p => p.id !== id))
  }

  return (
    <div className="min-h-screen bg-gray-100">
      <div className="max-w-2xl mx-auto py-10 px-4">

        <h1 className="text-4xl font-bold text-gray-900 mb-8">My Blog</h1>

        <div className="bg-white rounded-xl shadow p-6 mb-8">
          <h2 className="text-lg font-semibold text-gray-700 mb-4">New Post</h2>
          <form onSubmit={handleSubmit} className="flex flex-col gap-3">
            <input
              type="text"
              placeholder="Title"
              value={title}
              onChange={e => setTitle(e.target.value)}
              className="border border-gray-300 rounded-lg px-4 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-400"
            />
            <textarea
              placeholder="Content"
              value={content}
              onChange={e => setContent(e.target.value)}
              rows={4}
              className="border border-gray-300 rounded-lg px-4 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-400 resize-none"
            />
            <button
              type="submit"
              className="self-end bg-blue-600 hover:bg-blue-700 text-white text-sm font-medium px-5 py-2 rounded-lg transition-colors"
            >
              Publish
            </button>
          </form>
        </div>

        <div className="flex flex-col gap-4">
          {posts.map(post => (
            <PostCard key={post.id} post={post} onUpdate={handleUpdate} onDelete={handleDelete} />
          ))}
        </div>

      </div>
    </div>
  )
}

export default App
