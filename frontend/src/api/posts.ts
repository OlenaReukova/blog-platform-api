import axios from 'axios';
import type { Post } from '../types';

const API_BASE = 'http://localhost:8080/api/posts';

export const getAllPosts = async (): Promise<Post[]> => {
    const response = await axios.get<Post[]>(API_BASE);
    return response.data;
};
    export const createPost = async (post: {
        title: string;
        content: string;
        author: string;
        tags: string[];
    }):Promise<Post> => {
       const response = await axios.post<Post>(API_BASE, post);
       return response.data;
};
