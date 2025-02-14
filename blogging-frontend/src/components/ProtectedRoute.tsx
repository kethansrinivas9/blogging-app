import React from 'react';
import { Route, Navigate , RouteProps } from 'react-router-dom';
import Cookies from 'js-cookie';

interface ProtectedRouteProps {
    element: React.ReactNode;
}

const ProtectedRoute: React.FC<ProtectedRouteProps> = ({ element }) => {
    const token = Cookies.get('jwt'); // Retrieve the JWT token from cookies

    return token ? (
        <>{element}</> // If token exists, render the protected route
    ) : (
        <Navigate to="/login" /> // If no token, redirect to login page
    );
};

export default ProtectedRoute;