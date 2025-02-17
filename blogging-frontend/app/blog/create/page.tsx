'use client';
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useRouter } from "next/navigation"; // ✅ Use Next.js router
import Cookies from 'js-cookie';
import Link from "next/link"; // ✅ Use Next.js Link instead of <a>
import Header from "../../header/page";

interface User {
  id: number;
  name: string;
  email: string;
}




const LoginPage: React.FC = () => {
    const [title, settitle] = useState<string>('');
    const [content, setcontent] = useState<string>('');
    const [error, setError] = useState<string>('');
    const [success, setSuccess] = useState('');
    const router = useRouter(); // ✅ Use Next.js router
    const jwt_token = Cookies.get('jwt'); // Remove the JWT token
    const [user, setUser] = useState<User>();

    useEffect(() => {
        const storedEmail = sessionStorage.getItem('email');
        console.log("stored email: " + storedEmail);

        async function fetchUserDetails() {
            try {
                const response = await fetch(`http://localhost:8080/user/email/${encodeURIComponent(storedEmail)}`, {
                    method: "GET",
                    headers: {
                        'Authorization': `Bearer ${jwt_token}`
                    }
                });

                const user = await response.json()

                if (!user) {
                    throw new Error("Failed to fetch user details by email");
                }
                setUser(user);
                console.log("===================================id value:" + user?.id)
            } catch (error) {
                setError(`Error fetching user:+ ${error}`);
                console.error();
            }

        }
        
        fetchUserDetails();
    }, []);
    

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!title || !content) {
      setError('Please fill in all fields');
    } else {
        setError('');
        setSuccess('');

        try {
            if (!user) {
                console.error("User ID is not set!");
                return;
            } else {
                console.log("userid: " + user.id);
                console.log({
                    userid: user.id,
                    title: title,
                    content: content,
                });
            }

            const response = await fetch('http://localhost:8080/blog/create', {
                method: "POST",
                headers: {
                'Content-Type': 'application/json',  
                'Authorization': `Bearer ${jwt_token}`
                },
                body: JSON.stringify({
                        userId: user.id,
                        title: title,
                        content: content,
                    })
                });

                if (response.ok) { 
                    setError('');
                    setSuccess('Blog created successfully! You can view your new blog in home page');
                    settitle('');
                    setcontent('');
                //router.push("/home");
                } else {
                    setError('Blog creation failed. Please try again.');
                }
            } catch (err: any) {
                console.error(err);
                setError(err.response?.data?.message || 'The content you’ve entered is incorrect. Please try again!');
            }
        }
  };
  

    return (
    <div>
        <Header/>
        <div className="min-h-screen flex items-center justify-center">
            <div className="w-full max-w-5xl p-8 space-y-6 bg-white rounded-lg shadow-lg">
                <h2 className="text-2xl font-bold text-center text-gray-700">Create a Blog</h2>
                    {error && <p className="text-red-500 text-center">{error}</p>}
                    {success && <p className="text-green-500 text-center">{success}</p>}
                <form onSubmit={handleSubmit} className="space-y-4">
                <div>
                    <input
                    id="title"
                    type="title"
                    className="w-full p-3 mt-1 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                    placeholder="Enter blog title"
                    value={title}
                    onChange={(e) => settitle(e.target.value)}
                    required
                    />
                </div>
                
                <div>
                    <textarea
                    id="content"
                    rows={18}
                    className="w-full p-3 mt-1 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                    placeholder="Enter blog content"
                    value={content}
                    onChange={(e) => setcontent(e.target.value)}
                    required
                    ></textarea>
                </div>
                        
                <div className="flex items-center justify-between">
                    <button
                    type="submit"
                    className="w-full py-3 bg-blue-500 text-white font-bold rounded-lg hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500"
                    >
                    Create
                    </button>
                </div>
                </form>
            <div/>
        </div>
    </div>
    </div>
  );
  
};

export default LoginPage;
