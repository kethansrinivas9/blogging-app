import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Cookies from 'js-cookie';


const HomePage: React.FC = () => {
    const navigate = useNavigate();

    const handleLogout = async (e: React.FormEvent) => {
        e.preventDefault(); // Prevents form reload
        
        await fetch("http://localhost:8080/auth/logout", {
            method: "POST",
            credentials: "include", // Send cookies with the request
        });
        
        // Optionally, remove client-side cookies (if stored manually)
        document.cookie = "JSESSIONID=; Path=/; Max-Age=0;";
        Cookies.remove('jwt'); // Remove the JWT token

        navigate('/login'); // Redirect to login
    };

    return (
        <div className="min-h-screen flex items-center justify-center bg-gray-100">
            <div className="w-full max-w-md p-8 space-y-6 bg-white rounded-lg shadow-lg">
                <h2 className="text-2xl font-bold text-center text-gray-700">
                    Welcome to the Home Page!
                </h2>


            </div>

          
                    <form onSubmit={handleLogout} className="space-y-4">

          <div className="flex items-center justify-between">
            <button
              type="submit"
              className="w-full py-3 bg-blue-500 text-white font-bold rounded-lg hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500"
            >
              Logout
            </button>
          </div>
        </form>
        </div>
        
    );
};

export default HomePage;
