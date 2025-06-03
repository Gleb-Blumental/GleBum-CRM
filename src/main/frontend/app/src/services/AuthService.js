/**
 * Service for handling authentication with AWS Cognito
 */
class AuthService {
  constructor() {
    // Get all values from environment variables
    this.region = process.env.REACT_APP_COGNITO_REGION;
    this.userPoolId = process.env.REACT_APP_COGNITO_USER_POOL_ID;
    this.clientId = process.env.REACT_APP_COGNITO_APP_CLIENT_ID;
    this.redirectUri = process.env.REACT_APP_COGNITO_REDIRECT_URI;
    this.cognitoDomain = process.env.REACT_APP_COGNITO_DOMAIN;
    
    // For development/testing, we'll use localStorage to simulate token storage
    this.tokenKey = 'auth_token';
    this.userKey = 'auth_user';
  }

  /**
   * Get the Cognito login URL
   * @returns {string} The Cognito login URL
   */
  async getLoginUrl() {
    try {
      // Get the login URL from the backend API
      const response = await fetch('/api/auth/login-url');
      if (!response.ok) {
        throw new Error('Failed to get login URL');
      }
      
      const data = await response.json();
      return data.loginUrl;
    } catch (error) {
      console.error('Error getting login URL:', error);
      
      // Fallback to constructing the URL directly
      return `${this.cognitoDomain}/login?client_id=${this.clientId}&response_type=code&redirect_uri=${encodeURIComponent(this.redirectUri)}`;
    }
  }

  /**
   * Check if the user is authenticated
   * @returns {boolean} Whether the user is authenticated
   */
  isAuthenticated() {
    const token = localStorage.getItem(this.tokenKey);
    if (!token) return false;
    
    try {
      // Check if token is expired
      const payload = JSON.parse(atob(token.split('.')[1]));
      const expiry = payload.exp * 1000; // Convert to milliseconds
      return Date.now() < expiry;
    } catch (error) {
      console.error('Error validating token:', error);
      return false;
    }
  }

  /**
   * Get the current user
   * @returns {Object|null} The current user, or null if not authenticated
   */
  getCurrentUser() {
    if (!this.isAuthenticated()) return null;
    
    try {
      const userStr = localStorage.getItem(this.userKey);
      return userStr ? JSON.parse(userStr) : null;
    } catch (error) {
      console.error('Error getting user:', error);
      return null;
    }
  }

  /**
   * Process the authentication callback
   * @param {string} code The authorization code from Cognito
   * @returns {Promise<Object>} The user information
   */
  async processCallback(code) {
    // In a real implementation, this would exchange the code for tokens
    // For now, we'll simulate a successful login
    
    // Simulate API call to exchange code for tokens
    return new Promise((resolve) => {
      setTimeout(() => {
        // Create a fake token (DO NOT USE IN PRODUCTION)
        const fakeToken = this.createFakeToken();
        localStorage.setItem(this.tokenKey, fakeToken);
        
        // Create a fake user
        const user = {
          id: 'user-123',
          email: 'user@example.com',
          name: 'Test User'
        };
        localStorage.setItem(this.userKey, JSON.stringify(user));
        
        resolve(user);
      }, 500);
    });
  }

  /**
   * Log out the current user
   */
  logout() {
    // Clear local storage
    localStorage.removeItem(this.tokenKey);
    localStorage.removeItem(this.userKey);
    
    // Redirect to the Cognito logout URL
    const logoutUrl = `${this.cognitoDomain}/logout?client_id=${this.clientId}&logout_uri=${encodeURIComponent(window.location.origin)}`;
    window.location.href = logoutUrl;
  }

  /**
   * Create a fake JWT token for development/testing
   * @private
   * @returns {string} A fake JWT token
   */
  createFakeToken() {
    // DO NOT USE THIS IN PRODUCTION - this is just for development/testing
    const header = {
      alg: 'HS256',
      typ: 'JWT'
    };
    
    const now = Math.floor(Date.now() / 1000);
    const payload = {
      sub: 'user-123',
      email: 'user@example.com',
      name: 'Test User',
      iat: now,
      exp: now + 3600 // Expires in 1 hour
    };
    
    const base64Header = btoa(JSON.stringify(header));
    const base64Payload = btoa(JSON.stringify(payload));
    const signature = 'fake_signature'; // In a real JWT, this would be cryptographically signed
    
    return `${base64Header}.${base64Payload}.${signature}`;
  }
}

export default new AuthService();
