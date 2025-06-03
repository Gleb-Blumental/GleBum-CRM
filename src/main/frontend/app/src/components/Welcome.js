import React from 'react';
import './Welcome.css';
import Header from './Header';

const Welcome = () => {
  return (
    <div className="welcome-container">
      <Header />
      <main className="welcome-content">
        <div className="welcome-card">
          <h2>Welcome to my CRM</h2>
          <div className="placeholder-section">
            <h3>System Overview</h3>
            <p>Everything is working as expected</p>
          </div>
          <div className="placeholder-section">
            <h3>Recent Activity</h3>
            <ul className="placeholder-list">
              <li>Jane Doe</li>
              <li>Ulf Kristersson</li>
              <li>Gleb Blumental</li>
            </ul>
          </div>
          <div className="placeholder-section">
            <h3>Quick Actions</h3>
            <div className="action-buttons">
              <button className="action-button">Button for Work</button>
              <button className="action-button">Button for Client</button>
              <button className="action-button">Button for an amazing function</button>
            </div>
          </div>
        </div>
      </main>
    </div>
  );
};

export default Welcome;
