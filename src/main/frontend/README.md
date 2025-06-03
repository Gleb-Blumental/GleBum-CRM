# ExamensArbete Frontend

This is the frontend application for the ExamensArbete project. It's built with React and integrates with AWS Cognito for authentication.

## Project Structure

```
src/main/frontend/
├── app/                  # React application
│   ├── public/           # Public assets
│   └── src/
│       ├── components/   # React components
│       │   ├── Header.js     # Application header with login/logout button
│       │   ├── Welcome.js    # Welcome page with central content window
│       │   └── Login.js      # Login redirect component
│       ├── services/     # Service modules
│       │   └── AuthService.js # Authentication service for Cognito integration
│       ├── App.js        # Main application component with routing
│       └── index.js      # Application entry point
└── Dockerfile            # Docker configuration for production build
```

## Features

- **Welcome Page**: A landing page with a central content window displaying placeholder information
- **Authentication**: Login/Sign Up button in the header that redirects to AWS Cognito
- **Responsive Design**: Mobile-friendly layout

## Development

### Prerequisites

- Node.js (v14 or higher)
- npm (v6 or higher)

### Getting Started

1. Navigate to the app directory:
   ```
   cd src/main/frontend/app
   ```

2. Install dependencies:
   ```
   npm install
   ```

3. Start the development server:
   ```
   npm start
   ```

4. Open [http://localhost:3000](http://localhost:3000) in your browser

### Building for Production

```
npm run build
```

This creates a production-ready build in the `build` directory.

## Docker

The included Dockerfile builds the React application and serves it using Nginx. To build and run the Docker container:

```
docker build -t examensarbete-frontend .
docker run -p 80:80 examensarbete-frontend
```

