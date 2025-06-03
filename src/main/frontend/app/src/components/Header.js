import React from 'react';
import { Link } from 'react-router-dom';
import './Header.css';
import AuthService from '../services/AuthService';

const Header = () => {
  // Get authentication status from our service
  const isAuthenticated = AuthService.isAuthenticated();
  
  // Handle login by redirecting to the Cognito login page
  const handleLogin = async () => {
    try {
      const loginUrl = await AuthService.getLoginUrl();
      window.location.href = loginUrl;
    } catch (error) {
      console.error('Error getting login URL:', error);
      // Fallback to the login page
      window.location.href = '/login';
    }
  };
  
  // Handle logout
  const handleLogout = () => {
    AuthService.logout();
    // Refresh the page to update the UI
    window.location.reload();
  };

  return (
    <header className="header">
      <div className="logo">
        <Link to="/">
          <h1>Glebski CRM</h1>
        </Link>
      </div>
      <div className="nav-links">
        {isAuthenticated ? (
          <button className="auth-button" onClick={handleLogout}>
            Logout
          </button>
        ) : (
          <button className="auth-button" onClick={handleLogin}>
            Login / Sign Up
          </button>
        )}
      </div>
    </header>
  );
};

export default Header;
