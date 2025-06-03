# ExamensArbete Frontend Application

This is the React frontend application for the ExamensArbete project. It provides a user interface for interacting with the backend services and integrates with AWS Cognito for authentication.

## Features

- Welcome page with central content window
- Authentication with AWS Cognito
- Responsive design

## Getting Started

### Prerequisites

- Node.js (v14 or higher)
- npm (v6 or higher)

### Installation

1. Install dependencies:

```bash
npm install
```

2. Create a `.env` file in the root of the app directory with the following variables:

```
REACT_APP_API_URL=http://localhost:8080/api
REACT_APP_COGNITO_REGION=eu-north-1
REACT_APP_COGNITO_USER_POOL_ID=eu-north-1_NlfnzCNIn
REACT_APP_COGNITO_APP_CLIENT_ID=7kcplepb7r7spt5jnjco3fecnl
REACT_APP_COGNITO_REDIRECT_URI=http://localhost:3000/callback
REACT_APP_COGNITO_DOMAIN=https://eu-north-1nlfnzcnin.auth.eu-north-1.amazoncognito.com
```

Replace these values with your actual AWS Cognito configuration.

### Running the Application

To start the development server:

```bash
npm start
```

The application will be available at [http://localhost:3000](http://localhost:3000).

### Building for Production

To create a production build:

```bash
npm run build
```

This will create optimized files in the `build` directory that can be deployed to a web server.

## Environment Variables

| Variable | Description |
|----------|-------------|
| `REACT_APP_API_URL` | The URL of the backend API |
| `REACT_APP_COGNITO_REGION` | AWS region where your Cognito User Pool is located |
| `REACT_APP_COGNITO_USER_POOL_ID` | ID of your Cognito User Pool |
| `REACT_APP_COGNITO_APP_CLIENT_ID` | Client ID for your Cognito App Client |
| `REACT_APP_COGNITO_REDIRECT_URI` | URI where Cognito will redirect after authentication |
| `REACT_APP_COGNITO_DOMAIN` | Domain of your Cognito User Pool |

## Project Structure

```
src/
├── components/         # React components
│   ├── Callback.js     # Handles OAuth callback from Cognito
│   ├── Header.js       # Application header with login button
│   ├── Login.js        # Login redirect component
│   └── Welcome.js      # Welcome page with central content
├── services/           # Service modules
│   ├── ApiService.js   # Handles API requests to the backend
│   └── AuthService.js  # Manages authentication with Cognito
├── App.js              # Main application component
└── index.js            # Application entry point
```

## Deployment

For deployment instructions, see the main project README or the EC2 deployment guide in the `src/main/docker` directory.

## AWS Cognito Integration

This application integrates with AWS Cognito for authentication. When a user clicks the "Login / Sign Up" button, they are redirected to the Cognito hosted UI for authentication. After successful authentication, Cognito redirects back to the application's callback URL, where the application processes the authentication response.

In development mode, the application simulates the authentication flow without actually redirecting to Cognito. In production, it uses the actual Cognito hosted UI.
