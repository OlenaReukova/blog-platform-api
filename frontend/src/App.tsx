import { useState, useEffect } from 'react';
import type { Post } from './types';
import { getAllPosts, deletePost } from './api/posts';
import CreatePostForm from './components/CreatePostForm';
import './App.css';

function App() {
  const [posts, setPosts] = useState<Post[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [showCreateForm, setShowCreateForm] = useState(false);

  const fetchPosts = async () => {
    try {
      setLoading(true);
      const data = await getAllPosts();
      setPosts(data);
    } catch (err) {
      setError('Failed to load posts');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchPosts();
  }, []);

  const handleDelete = async (id: string) => {
    try {
      await deletePost(id);
      fetchPosts(); // обновляем список после удаления
    } catch (err) {
      setError('Failed to delete post');
    }
  };

  if (loading) return <div className="loading">Loading posts...</div>;
  if (error) return <div className="error">{error}</div>;

  return (
      <div className="app">
        <header className="app-header">
          <h1>📝 Blog Platform</h1>
          <button
              className="btn-primary"
              onClick={() => setShowCreateForm(!showCreateForm)}
          >
            {showCreateForm ? 'Cancel' : '+ New Post'}
          </button>
        </header>
        {showCreateForm && (
            <CreatePostForm
                onPostCreated={() => {
                  fetchPosts();
                  setShowCreateForm(false);
                }}
            />
        )}
        {posts.length === 0 ? (
            <div className="empty">No posts yet!</div>
        ) : (
            <div className="posts-grid">
              {posts.map(post => (
                  <div key={post.id} className="post-card">
                    <div className="post-header">
                    <h2>{post.title}</h2>
                      <button
                          className="btn-danger"
                          onClick={() => handleDelete(post.id)}
                      >
                        🗑️
                      </button>
                    </div>
                    <p className="post-content">{post.content}</p>
                    <div className="post-meta">
                      <span>👤 {post.author}</span>
                      <span>📅 {new Date(post.createdAt).toLocaleDateString()}</span>
                    </div>
                    {post.tags.length > 0 && (
                        <div className="tags">
                          {post.tags.map(tag => (
                              <span key={tag} className="tag">#{tag}</span>
                          ))}
                        </div>
                    )}
                  </div>
              ))}
            </div>
        )}
      </div>
  );
}

export default App;