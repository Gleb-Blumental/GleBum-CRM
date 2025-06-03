import React, { useEffect, useState } from 'react';
import './Login.css';
import AuthService from '../services/AuthService';

const Login = () => {
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const redirectToCognito = async () => {
      try {
        setIsLoading(true);
        // Get the Cognito login URL from the AuthService
        const cognitoUrl = await AuthService.getLoginUrl();
        
        // Log the URL for debugging
        console.log('Redirecting to Cognito URL:', cognitoUrl);
        
        // Redirect to Cognito
        window.location.href = cognitoUrl;
      } catch (error) {
        console.error('Error redirecting to Cognito:', error);
        setError('Failed to redirect to login page. Please try again later.');
        setIsLoading(false);
      }
    };

    // Redirect to Cognito immediately
    redirectToCognito();
  }, []);

  return (
    <div className="login-container">
      <div className="login-card">
        <h2>Redirecting to Login</h2>
        <p>You are being redirected to the authentication service...</p>
        <div className="loader"></div>
      </div>
    </div>
  );
};

export default Login;
