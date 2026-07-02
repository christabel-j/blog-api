import { useState } from 'react'
import { updatePost, deletePost } from './api.js'

function PostCard({ post, onUpdate, onDelete }) {
  const [isEditing, setIsEditing] = useState(false)
  const [title, setTitle] = useState(post.title)
  const [content, setContent] = useState(post.content)
  const [error, setError] = useState(null)

  function handleDelete() {
    deletePost(post.id)
      .then(() => onDelete(post.id))
      .catch(() => setError('Failed to delete post.'))
  }

  function handleSave() {
    updatePost(post.id, title, content)
      .then(updatedPost => {
        onUpdate(updatedPost)
        setIsEditing(false)
        setError(null)
      })
      .catch(() => setError('Failed to save changes.'))
  }

  if (isEditing) {
    return (
      <div className="bg-white rounded-xl shadow p-6">
        {error && (
          <p className="text-red-600 text-sm mb-3">{error}</p>
        )}
        <input
          type="text"
          value={title}
          onChange={e => setTitle(e.target.value)}
          className="w-full border border-gray-300 rounded-lg px-4 py-2 text-sm font-semibold mb-3 focus:outline-none focus:ring-2 focus:ring-blue-400"
        />
        <textarea
          value={content}
          onChange={e => setContent(e.target.value)}
          rows={4}
          className="w-full border border-gray-300 rounded-lg px-4 py-2 text-sm mb-4 focus:outline-none focus:ring-2 focus:ring-blue-400 resize-none"
        />
        <div className="flex gap-2">
          <button
            onClick={handleSave}
            className="bg-green-600 hover:bg-green-700 text-white text-sm font-medium px-4 py-1.5 rounded-lg transition-colors"
          >
            Save
          </button>
          <button
            onClick={() => { setIsEditing(false); setError(null) }}
            className="bg-gray-200 hover:bg-gray-300 text-gray-700 text-sm font-medium px-4 py-1.5 rounded-lg transition-colors"
          >
            Cancel
          </button>
        </div>
      </div>
    )
  }

  return (
    <div className="bg-white rounded-xl shadow p-6">
      <h2 className="text-xl font-semibold text-gray-900 mb-2">{post.title}</h2>
      <p className="text-gray-600 text-sm mb-4">{post.content}</p>
      {error && (
        <p className="text-red-600 text-sm mb-3">{error}</p>
      )}
      <div className="flex gap-2">
        <button
          onClick={() => setIsEditing(true)}
          className="bg-blue-100 hover:bg-blue-200 text-blue-700 text-sm font-medium px-4 py-1.5 rounded-lg transition-colors"
        >
          Edit
        </button>
        <button
          onClick={handleDelete}
          className="bg-red-100 hover:bg-red-200 text-red-700 text-sm font-medium px-4 py-1.5 rounded-lg transition-colors"
        >
          Delete
        </button>
      </div>
    </div>
  )
}

export default PostCard
