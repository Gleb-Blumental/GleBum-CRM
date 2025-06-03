import React from 'react';
import './Welcome.css';
import Header from './Header';

const Welcome = () => {
  return (
    <div className="welcome-container">
      <Header />
      <main className="welcome-content">
        <div className="welcome-card">
          <h2>Welcome to ExamensArbete</h2>
          <div className="placeholder-section">
            <h3>System Overview</h3>
            <p>This is a placeholder for the system overview information. In a real application, this would contain actual data.</p>
          </div>
          <div className="placeholder-section">
            <h3>Recent Activity</h3>
            <ul className="placeholder-list">
              <li>Placeholder activity item 1</li>
              <li>Placeholder activity item 2</li>
              <li>Placeholder activity item 3</li>
            </ul>
          </div>
          <div className="placeholder-section">
            <h3>Quick Actions</h3>
            <div className="action-buttons">
              <button className="action-button">Action 1</button>
              <button className="action-button">Action 2</button>
              <button className="action-button">Action 3</button>
            </div>
          </div>
        </div>
      </main>
    </div>
  );
};

export default Welcome;
