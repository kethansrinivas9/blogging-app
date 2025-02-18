'use client';
import React, { useState, useEffect } from 'react';
import Cookies from 'js-cookie';
import Header from '../../../header/page';

// Define interfaces for our data types
interface Blog {
  id: number;
  title: string;
  content: string;
  userId: string;
  userName: string;
}

const UserBlogs = () => {
    const [blogs, setBlogs] = useState<Blog[]>([]);
    const jwt_token = Cookies.get('jwt'); // Remove the JWT token
    const [loading, setLoading] = useState(true);
     // This state will track which blog's content is expanded
    const [expandedBlogId, setExpandedBlogId] = useState<number | null>(null);


    useEffect(() => {
        const fetchBlogs = async () => {
        
        const id = sessionStorage.getItem("userid");
        console.log("id: " + id);
            try {
            
                if (id == null)
                    throw new Error("User not logged-in or userid is null");

            const response = await fetch(`http://localhost:8080/user/${encodeURIComponent(id)}/blog/all`, {
                headers: {
                'Authorization': `Bearer ${jwt_token}`
                }
            });

            if (!response.ok) {
                console.log(response.status);
                throw new Error(`HTTP Error! Status: ${response.status}`);
            }

            const data = await response.json();
            setBlogs(data);
            setLoading(false);
        } catch (error) {
            console.error('Error fetching blogs:', error);
            setLoading(false);
        }
    };
    
    fetchBlogs();
    }, [jwt_token]);



   const handleToggleContent = (id: number) => {
    // If the clicked blog is already expanded, collapse it. Otherwise, expand it.
    setExpandedBlogId(expandedBlogId === id ? null : id);
  };

    return (
        <div>
            <Header/>

        <div className="max-w-screen-2xl mx-auto py-6 sm:px-6 lg:px-8">
            <div className="max-w-6xl mx-auto">
                {loading ? (
                    <p className="text-center text-gray-600">Loading blogs...</p>
                ) : (
                        
                        <div className="grid grid-cols-1 gap-6">
                        {blogs.length === 0 ? (
                            <p className="text-center text-gray-600">No blogs available.</p>
                        ) : (
                            blogs.map((blog) => (
                                <div
                                    key={blog.id}
                                    className="bg-white overflow-hidden shadow rounded-lg hover:shadow-lg transition-shadow duration-300"
                                >
                                    <div className="p-6">
                                        <h2 className="text-2xl font-semibold text-gray-800">{blog.title}</h2>
                                        <span className="text-sm text-gray-500 mt-2">Posted By: {blog.userName}</span>
                                        <p className="text-gray-600 mt-4 flex-grow">
                                            {expandedBlogId === blog.id
                                                ? blog.content
                                                : blog.content.slice(0, 500) + '...'}
                                        </p>
                                        <div className="mt-auto">
                                            <button
                                                className="text-blue-600 hover:underline font-medium"
                                                onClick={() => handleToggleContent(blog.id)}
                                            >
                                                {expandedBlogId === blog.id ? 'Read Less' : 'Read More'}
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            ))
                        )}
                    </div>
                )}
            </div>
            </div>
                    </div>
    );
};


export default UserBlogs;