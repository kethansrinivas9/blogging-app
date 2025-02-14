import React from 'react';
import LoginPage from './components/Login';
import SignupPage from './components/Signup';
import HomePage from './components/Home';
import HomePagenew from './components/HomePage';
import ProtectedRoute from './components/ProtectedRoute'
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route path="/signup" element={<SignupPage />} />
        <Route path="/home" element={<ProtectedRoute element={<HomePage />} />} />
        <Route path="/homepage" element={<ProtectedRoute element={<HomePagenew />} />} />
      </Routes>
    </Router>
  );
}

export default App;
