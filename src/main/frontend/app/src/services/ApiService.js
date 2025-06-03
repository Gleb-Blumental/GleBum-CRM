/**
 * Service for handling API requests to the backend
 */
class ApiService {
  constructor() {
    // Base URL for API requests - would come from environment variables in production
    this.baseUrl = process.env.REACT_APP_API_URL || 'http://localhost:8080/api';
  }

  /**
   * Get the authorization header for authenticated requests
   * @private
   * @returns {Object} The headers object with Authorization header if available
   */
  _getHeaders() {
    const headers = {
      'Content-Type': 'application/json',
    };

    // Get token from localStorage (in a real app, this would be a JWT token from Cognito)
    const token = localStorage.getItem('auth_token');
    if (token) {
      headers['Authorization'] = `Bearer ${token}`;
    }

    return headers;
  }

  /**
   * Make a GET request to the API
   * @param {string} endpoint - The API endpoint to request
   * @returns {Promise<any>} The response data
   */
  async get(endpoint) {
    try {
      const response = await fetch(`${this.baseUrl}${endpoint}`, {
        method: 'GET',
        headers: this._getHeaders(),
      });

      if (!response.ok) {
        throw new Error(`API error: ${response.status} ${response.statusText}`);
      }

      return await response.json();
    } catch (error) {
      console.error('API request failed:', error);
      throw error;
    }
  }

  /**
   * Make a POST request to the API
   * @param {string} endpoint - The API endpoint to request
   * @param {Object} data - The data to send in the request body
   * @returns {Promise<any>} The response data
   */
  async post(endpoint, data) {
    try {
      const response = await fetch(`${this.baseUrl}${endpoint}`, {
        method: 'POST',
        headers: this._getHeaders(),
        body: JSON.stringify(data),
      });

      if (!response.ok) {
        throw new Error(`API error: ${response.status} ${response.statusText}`);
      }

      return await response.json();
    } catch (error) {
      console.error('API request failed:', error);
      throw error;
    }
  }

  /**
   * Make a PUT request to the API
   * @param {string} endpoint - The API endpoint to request
   * @param {Object} data - The data to send in the request body
   * @returns {Promise<any>} The response data
   */
  async put(endpoint, data) {
    try {
      const response = await fetch(`${this.baseUrl}${endpoint}`, {
        method: 'PUT',
        headers: this._getHeaders(),
        body: JSON.stringify(data),
      });

      if (!response.ok) {
        throw new Error(`API error: ${response.status} ${response.statusText}`);
      }

      return await response.json();
    } catch (error) {
      console.error('API request failed:', error);
      throw error;
    }
  }

  /**
   * Make a DELETE request to the API
   * @param {string} endpoint - The API endpoint to request
   * @returns {Promise<any>} The response data
   */
  async delete(endpoint) {
    try {
      const response = await fetch(`${this.baseUrl}${endpoint}`, {
        method: 'DELETE',
        headers: this._getHeaders(),
      });

      if (!response.ok) {
        throw new Error(`API error: ${response.status} ${response.statusText}`);
      }

      return await response.json();
    } catch (error) {
      console.error('API request failed:', error);
      throw error;
    }
  }

  // Example methods for specific API endpoints

  /**
   * Get all customers
   * @returns {Promise<Array>} Array of customers
   */
  async getCustomers() {
    return this.get('/customers');
  }

  /**
   * Get a customer by ID
   * @param {string} id - The customer ID
   * @returns {Promise<Object>} The customer data
   */
  async getCustomer(id) {
    return this.get(`/customers/${id}`);
  }

  /**
   * Create a new customer
   * @param {Object} customer - The customer data
   * @returns {Promise<Object>} The created customer
   */
  async createCustomer(customer) {
    return this.post('/customers', customer);
  }

  /**
   * Update a customer
   * @param {string} id - The customer ID
   * @param {Object} customer - The updated customer data
   * @returns {Promise<Object>} The updated customer
   */
  async updateCustomer(id, customer) {
    return this.put(`/customers/${id}`, customer);
  }

  /**
   * Delete a customer
   * @param {string} id - The customer ID
   * @returns {Promise<Object>} The response data
   */
  async deleteCustomer(id) {
    return this.delete(`/customers/${id}`);
  }
}

export default new ApiService();
