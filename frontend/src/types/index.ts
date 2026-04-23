export interface Comment {
    id: string;
    text: string;
    author: string;
    createdAt: string;
}

export interface Post {
    id: string;
    title: string;
    content: string;
    author: string;
    tags: string[];
    comments: Comment[];
    createdAt: string;
}