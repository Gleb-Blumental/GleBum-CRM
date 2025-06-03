import React, { useEffect } from 'react';
import './Login.css';
import AuthService from '../services/AuthService';

const Login = () => {
  useEffect(() => {
    const redirectToCognito = () => {
      // Get the Cognito login URL from the AuthService
      const cognitoUrl = AuthService.getLoginUrl();
      
      // Log the URL for debugging
      console.log('Redirecting to Cognito URL:', cognitoUrl);
      
      // Redirect to Cognito
      window.location.href = cognitoUrl;
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
