import { useRouter } from "next/navigation"; // ✅ Use Next.js router
import Cookies from "js-cookie";
import React, { useState, useEffect } from 'react';


interface User {
  id: number;
  name: string;
  email: string;
}


const Header: React.FC = () => {
  const router = useRouter(); // ✅ Use Next.js router
  const [user, setUser] = useState<User>();
  const jwt_token = Cookies.get('jwt'); // Remove the JWT token
  
  useEffect(() => {
    // Retrieve email and password from sessionStorage
    const storedEmail = sessionStorage.getItem('email');
    //console.log("===================================email value:" + storedEmail);

    const fetchUser = async () => {
      try {
        const response = await fetch(`http://localhost:8080/user/email/${encodeURIComponent(storedEmail)}`, {
              method: "GET",
              headers: {
                'Authorization': `Bearer ${jwt_token}`
              }
        });

        if (!response.ok) {
          throw new Error("Failed to fetch user details by email");
        }
        const data = await response.json();
        setUser(data);
        console.log("===================================email value:" + data?.email)
      } catch (error) {
        console.error("Error fetching user:", error);
      }
    };

    fetchUser();
  }, []);
  
  

  const handleLogout = async () => {
    await fetch("http://localhost:8080/auth/logout", {
      method: "POST",
      credentials: "include", // Send cookies with the request
    });
  
    // Optionally, remove client-side cookies (if stored manually)
    document.cookie = "JSESSIONID=; Path=/; Max-Age=0;";
    Cookies.remove('jwt'); // Remove the JWT token
    router.push("/login");
  };
  

    return (

      <nav className="bg-black border-gray-200 dark:bg-gray-900">
        <div className="max-w-screen-xl flex flex-wrap items-center mx-auto p-4">
    
          {/* Left-aligned Blogger title */}
          <a href="/home" className="flex items-center space-x-3">
            <span className="text-2xl font-semibold text-white">BlogNest</span>
          </a>

          {/* Right-aligned buttons */}
          <div className="flex items-center space-x-4 ml-auto">
      
            {/* Navigation links */}
            <div className="hidden md:flex space-x-8">
              <a href="#" className="text-white">My blogs</a>
              <a href="#" className="text-white">Create a blog</a>
            </div>

            {/* User Menu */}
            <div className="relative">
              <button type="button" className="flex text-sm bg-gray-800 rounded-full focus:ring-4 focus:ring-gray-300 dark:focus:ring-gray-600"
                id="user-menu-button"
                aria-expanded="false"
                onClick={() => {
                  const dropdown = document.getElementById("user-dropdown");
                  dropdown?.classList.toggle("hidden");
                }}>
                <div className="relative w-10 h-10 overflow-hidden bg-gray-100 rounded-full dark:bg-gray-600">
                  <svg className="w-10 h-10 text-gray-400" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                    <path fillRule="evenodd" d="M10 9a3 3 0 100-6 3 3 0 000 6zm-7 9a7 7 0 1114 0H3z" clipRule="evenodd"></path>
                  </svg>
                </div>
              </button>

              {/* Dropdown menu */}
              <div className="absolute right-0 mt-2 w-48 bg-white rounded-lg shadow-lg hidden z-50 dark:bg-gray-700" id="user-dropdown">
                <div className="px-4 py-3">
                  <span className="block text-sm text-gray-900 dark:text-white">{user?.name}</span>
                  <span className="block text-sm text-gray-500 dark:text-gray-400">{user?.email}</span>
                </div>
                <ul className="py-2">
                  <li><a href="#" className="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 dark:text-gray-200 dark:hover:bg-gray-600">Dashboard</a></li>
                  <li><a href="#" className="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 dark:text-gray-200 dark:hover:bg-gray-600">Settings</a></li>
                  <li><a href="#" onClick={(e) => {
                    e.preventDefault();
                    handleLogout();
                  }}
                    className="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 dark:text-gray-200 dark:hover:bg-gray-600">Logout</a></li>
                </ul>
              </div>
            </div>

            {/* Mobile Menu Button */}
            <button type="button" className="md:hidden p-2 text-gray-500 rounded-lg focus:ring-2 focus:ring-gray-200 dark:text-gray-400 dark:focus:ring-gray-600"
              onClick={() => {
                const menu = document.getElementById("mobile-menu");
                menu?.classList.toggle("hidden");
              }}>
              <span className="sr-only">Open main menu</span>
              <svg className="w-6 h-6" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M4 6h16M4 12h16m-7 6h7" />
              </svg>
            </button>
          </div>

          {/* Mobile Menu */}
          <div className="hidden w-full md:hidden mt-4" id="mobile-menu">
            <a href="#" className="block py-2 text-white">About</a>
            <a href="#" className="block py-2 text-white">Services</a>
          </div>

        </div>
      </nav>

    );
}

export default Header;