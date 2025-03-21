'use client';
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useRouter } from "next/navigation"; // ✅ Use Next.js router
import Cookies from 'js-cookie';
import Link from "next/link"; // ✅ Use Next.js Link instead of <a>

const LoginPage: React.FC = () => {
  const [email, setEmail] = useState<string>('');
  const [password, setPassword] = useState<string>('');
  const [error, setError] = useState<string>('');
  const router = useRouter(); // ✅ Use Next.js router

  useEffect(() => {
    // Retrieve email and password from sessionStorage
    const storedEmail = sessionStorage.getItem('email');
    const storedPassword = sessionStorage.getItem('password');

    if (storedEmail) {
      setEmail(storedEmail);
    }
    if (storedPassword) {
      setPassword(storedPassword);
    }
  }, []);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!email || !password) {
      setError('Please fill in all fields');
    } else {
      setError('');
      // handle login logic here (e.g., call API)
      console.log('Logging in with', { email, password });
    try {
            const response = await axios.post('http://localhost:8080/auth/login', {
                email,
                password,
            });

            console.error(response);

            if (response.status === 200) {
                setError('');
                sessionStorage.setItem('email', email);
                sessionStorage.setItem('password', password);
              
              const token = response.data; 
              
              //The cookie expiration is set to 1 hour to match with the JWT expiration of one hour
              Cookies.set('jwt', token, { secure: false, sameSite: 'Lax', expires: 1 / 24 });
              
              //Can shift to Strict when deployed in https mode later
              //Cookies.set('jwt', token, { secure: false, sameSite: 'Strict' });
              router.push("/home");
            } else {
                setError('Login failed. Please try again.');
            }
        } catch (err: unknown) {
      
          if (err instanceof Error) {
            console.error(err);
            setError(err.message || 'The password you’ve entered is incorrect. Please try again!');
          }
            
        }
    }
  };
  

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100">
      <div className="w-full max-w-md p-8 space-y-6 bg-white rounded-lg shadow-lg">
        <h2 className="text-2xl font-bold text-center text-gray-700">Login</h2>
        {error && <p className="text-red-500 text-center">{error}</p>}
        <form onSubmit={handleSubmit} className="space-y-4">
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
              Login
            </button>
          </div>
        </form>

        <div className="text-center text-sm text-gray-500">
          Don&apos;t have an account?{' '}
          <Link href="/signup" className="text-blue-500 hover:underline">
            Sign up
          </Link>
        </div>
      </div>
    </div>
  );
};

export default LoginPage;
