import { useState, useEffect } from 'react';
import type { Post } from './types';
import { getAllPosts } from './api/posts';
import './App.css';

function App() {
  const [posts, setPosts] = useState<Post[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchPosts = async () => {
      try {
        const data = await getAllPosts();
        setPosts(data);
      } catch (err) {
        setError('Failed to load posts');
      } finally {
        setLoading(false);
      }
    };
    fetchPosts();
  }, []);

  if (loading) return <div className="loading">Loading posts...</div>;
  if (error) return <div className="error">{error}</div>;

  return (
      <div className="app">
        <header className="app-header">
          <h1>📝 Blog Platform</h1>
        </header>
        {posts.length === 0 ? (
            <div className="empty">No posts yet!</div>
        ) : (
            <div className="posts-grid">
              {posts.map(post => (
                  <div key={post.id} className="post-card">
                    <h2>{post.title}</h2>
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