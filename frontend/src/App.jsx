import { useState, useEffect } from 'react'
import PostCard from './PostCard.jsx'
import { getAllPosts, createPost } from './api.js'

function App() {
  const [posts, setPosts] = useState([])
  const [title, setTitle] = useState('')
  const [content, setContent] = useState('')
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)

  useEffect(() => {
    getAllPosts()
      .then(data => setPosts(data))
      .catch(() => setError('Could not load posts. Is the backend running?'))
      .finally(() => setLoading(false))
  }, [])

  function handleSubmit(e) {
    e.preventDefault()
    createPost(title, content)
      .then(newPost => {
        setPosts([...posts, newPost])
        setTitle('')
        setContent('')
      })
      .catch(() => setError('Failed to create post. Please try again.'))
  }

  function handleUpdate(updatedPost) {
    setPosts(posts.map(p => p.id === updatedPost.id ? updatedPost : p))
  }

  function handleDelete(id) {
    setPosts(posts.filter(p => p.id !== id))
  }

  if (loading) {
    return (
      <div className="min-h-screen bg-gray-100 flex items-center justify-center">
        <p className="text-gray-500 text-lg">Loading posts...</p>
      </div>
    )
  }

  return (
    <div className="min-h-screen bg-gray-100">
      <div className="max-w-2xl mx-auto py-10 px-4">

        <h1 className="text-4xl font-bold text-gray-900 mb-8">My Blog</h1>

        {error && (
          <div className="bg-red-50 border border-red-200 text-red-700 rounded-lg px-4 py-3 mb-6 text-sm">
            {error}
          </div>
        )}

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
