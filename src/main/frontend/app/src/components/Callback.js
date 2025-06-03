import React, { useEffect, useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import AuthService from '../services/AuthService';
import './Callback.css';

/**
 * Component to handle the OAuth callback from Cognito
 * This component processes the authorization code and redirects to the home page
 */
const Callback = () => {
  const [error, setError] = useState(null);
  const navigate = useNavigate();
  const location = useLocation();

  useEffect(() => {
    const processAuth = async () => {
      try {
        // Get the authorization code from the URL
        const params = new URLSearchParams(location.search);
        const code = params.get('code');
        
        if (!code) {
          throw new Error('No authorization code found in the URL');
        }
        
        // Process the authorization code
        await AuthService.processCallback(code);
        
        // Redirect to the home page
        navigate('/');
      } catch (err) {
        console.error('Authentication error:', err);
        setError(err.message || 'An error occurred during authentication');
      }
    };

    processAuth();
  }, [location, navigate]);

  if (error) {
    return (
      <div className="callback-container error">
        <div className="callback-card">
          <h2>Authentication Error</h2>
          <p>{error}</p>
          <button onClick={() => navigate('/')}>Return to Home</button>
        </div>
      </div>
    );
  }

  return (
    <div className="callback-container">
      <div className="callback-card">
        <h2>Completing Authentication</h2>
        <p>Please wait while we complete the authentication process...</p>
        <div className="loader"></div>
      </div>
    </div>
  );
};

export default Callback;
