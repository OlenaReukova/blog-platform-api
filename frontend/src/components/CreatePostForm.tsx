import { useState } from 'react';
import { createPost } from '../api/posts';


interface Props {
    onPostCreated: () => void;
}

export default function CreatePostForm({ onPostCreated }: Props) {


    const [title, setTitle] = useState('');
    const [content, setContent] = useState('');
    const [author, setAuthor] = useState('');
    const [tags, setTags] = useState('');
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);


    const handleSubmit = async () => {
        if (!title || !content || !author) {
            setError('Title, content and author are required!');
            return;
        }
        try {
            setLoading(true);
            await createPost({
                title,
                content,
                author,
                tags: tags.split(',').map(t => t.trim()).filter(Boolean),
            });

            setTitle('');
            setContent('');
            setAuthor('');
            setTags('');
            setError(null);

            onPostCreated();
        } catch (err) {
            setError('Failed to create post');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="create-form">
            <h2>Create New Post</h2>

            {error && <div className="error">{error}</div>}

            <input
                placeholder="Title *"
                value={title}
                onChange={e => setTitle(e.target.value)}
            />
            <textarea
                placeholder="Content *"
                value={content}
                onChange={e => setContent(e.target.value)}
                rows={4}
            />
            <input
                placeholder="Author *"
                value={author}
                onChange={e => setAuthor(e.target.value)}
            />
            <input
                placeholder="Tags (comma separated: java, spring)"
                value={tags}
                onChange={e => setTags(e.target.value)}
            />
            <button
                className="btn-primary"
                onClick={handleSubmit}
                disabled={loading}
            >
                {loading ? 'Creating...' : 'Create Post'}
            </button>
        </div>
    );
}