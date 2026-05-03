import { useState } from 'react';
import { addComment } from '../api/posts';

interface Props {
    postId: string;
    onCommentAdded: () => void;
}

export default function AddCommentForm({ postId, onCommentAdded }: Props) {
    const [text, setText] = useState('');
    const [author, setAuthor] = useState('');
    const [loading, setLoading] = useState(false);

    const handleSubmit = async () => {
        if (!text || !author) return;
        try {
            setLoading(true);
            await addComment(postId, { text, author });
            setText('');
            setAuthor('');
            onCommentAdded();
        } catch (err) {
            console.error('Failed to add comment');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="add-comment">
            <input
                placeholder="Your name"
                value={author}
                onChange={e => setAuthor(e.target.value)}
            />
            <input
                placeholder="Add a comment..."
                value={text}
                onChange={e => setText(e.target.value)}
            />
            <button
                className="btn-primary"
                onClick={handleSubmit}
                disabled={loading}
            >
                {loading ? 'Adding...' : 'Add Comment'}
            </button>
        </div>
    );
}