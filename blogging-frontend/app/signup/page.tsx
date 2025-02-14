'use client';
import React, { useState } from 'react';
import axios from 'axios';
import { useRouter } from "next/navigation"; // ✅ Use Next.js router
import Link from "next/link"; // ✅ Use Next.js Link instead of <a>

const SignupPage: React.FC = () => {
    const [name, setName] = useState<string>('');
    const [email, setEmail] = useState<string>('');
    const [password, setPassword] = useState<string>('');
    const [error, setError] = useState<string>('');
    const [loading, setLoading] = useState(false);
    const [success, setSuccess] = useState('');
    const router = useRouter(); // ✅ Use Next.js router


    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!name || !email || !password) {
            setError('Please fill in all fields');
        } else {
            setError('');
            setSuccess('');
            setLoading(true);
            // handle signup logic here (e.g., call API to register)
            console.log('Signing up with', { name, email, password });
            try {
                const response = await axios.post('http://localhost:8080/auth/register', {
                    name,
                    email,
                    password,
                });

                console.error(response);

                if (response.status === 201) {
                    setError('');
                    setSuccess('Account created successfully! You can now log in.');
                    sessionStorage.setItem('email', email);
                    sessionStorage.setItem('password', password);
                    router.push("/login");
                } else {
                    setError('Signup failed. Please try again.');
                }
            } catch (err: any) {
                console.error(err);
                setError(err.response?.data?.message || 'Something went wrong.');
            } finally {
                setLoading(false);
            }
        };

    };

    return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100">
        <div className="w-full max-w-md p-8 space-y-6 bg-white rounded-lg shadow-lg">
        <h2 className="text-2xl font-bold text-center text-gray-700">Sign Up</h2>
                {error && <p className="text-red-500 text-center">{error}</p>}
                {success && <p className="text-red-500 text-center">{success}</p>}
        <form onSubmit={handleSubmit} className="space-y-4">
            <div>
            <input
                id="name"
                type="text"
                className="w-full p-3 mt-1 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                placeholder="Enter your name"
                value={name}
                onChange={(e) => setName(e.target.value)}
                required
            />
            </div>
            
            <div>
            <input
                id="email"
                type="email"
                className="w-full p-3 mt-1 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                placeholder="Enter your email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
            />
            </div>
            
            <div>
            <input
                id="password"
                type="password"
                className="w-full p-3 mt-1 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                placeholder="Enter your password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
            />
            </div>

            <div className="flex items-center justify-between">
            <button
                type="submit"
                className="w-full py-3 bg-blue-500 text-white font-bold rounded-lg hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500"
            >
                Sign Up
            </button>
            </div>
        </form>

        <div className="text-center text-sm text-gray-500">
            Already have an account?{' '}
            <Link  href="/login" className="text-blue-500 hover:underline">
            Login
            </Link>
        </div>
        </div>
    </div>
    );
};

export default SignupPage;
