# Docker and AWS Cognito Configuration Guide

This guide explains how to set up and use the Docker configuration and AWS Cognito authentication for the CRM application.

## Docker Configuration

The application is containerized using Docker with a multi-container setup defined in `docker-compose.yml`. The setup includes:

- **MongoDB**: Database service
- **Backend**: Spring Boot application
- **Frontend**: React application served by Nginx
- **Backend-test**: Service for running tests

### Docker Files

- `docker-compose.yml`: Defines the multi-container setup
- `Dockerfile`: Builds the Spring Boot backend application
- `../frontend/Dockerfile`: Builds the React frontend application

### Running with Docker

1. Make sure Docker and Docker Compose are installed on your system
2. Create a `.env` file in the project root based on `.env.template`
3. Use the provided scripts to run Docker Compose:

**For Windows:**
```bash
cd src/main/docker
run-docker.bat
```

**For Linux/macOS:**
```bash
cd src/main/docker
chmod +x run-docker.sh
./run-docker.sh
```

These scripts will:
- Copy the `.env` file from the project root to the docker directory
- Start all services in detached mode

You can access:
- Frontend: http://localhost:3000
- Backend API: http://localhost:8080
- MongoDB: mongodb://localhost:27017

To stop the services:

```bash
docker-compose down
```

To view logs:

```bash
docker-compose logs -f
```

**Note:** The scripts ensure that environment variables from your `.env` file are properly loaded by Docker Compose, avoiding the need to hardcode sensitive information.

## AWS Cognito Configuration

The application uses AWS Cognito for authentication and authorization. Here's how to set it up:

### 1. Create a Cognito User Pool

1. Go to the [AWS Cognito Console](https://console.aws.amazon.com/cognito/home)
2. Click "Create user pool"
3. Choose "Cognito user pool" as the authentication provider
4. Configure sign-in experience:
   - Select "Email" as the cognito user pool sign-in option
   - Enable "Allow users to sign in with a preferred username"
   - Enable "Also allow sign in with verified email address"
5. Configure security requirements:
   - Choose password policy (recommended: Cognito defaults)
   - Enable multi-factor authentication (MFA) if required
   - Configure account recovery options
6. Configure sign-up experience:
   - Enable self-registration
   - Select required attributes (email is mandatory)
   - Add custom attributes if needed
7. Configure message delivery:
   - Choose "Send email with Cognito" for development
   - For production, consider using Amazon SES
8. Integrate your app:
   - Give your user pool a name (e.g., "crm-user-pool")
   - Choose "Use the Cognito Hosted UI"
   - Create a new app client:
     - App client name: "crm-app-client"
     - Select "Generate a client secret"
     - Set callback URLs: `http://localhost:3000/callback` (for development)
     - Set sign-out URLs: `http://localhost:3000/logout` (for development)
     - Select OAuth 2.0 grant types: "Authorization code grant"
     - Select OAuth 2.0 scopes: "email", "openid", "profile"
9. Review and create the user pool

### 2. Configure Environment Variables

After creating the user pool, update your `.env` file with the following values:

```
# AWS Region
AWS_REGION=eu-north-1  # Replace with your AWS region

# AWS Cognito Configuration
COGNITO_POOL_ID=eu-north-1_xxxxxxxx  # Replace with your User Pool ID
COGNITO_CLIENT_ID=xxxxxxxxxxxxxxxxxxxxxxxxxx  # Replace with your App Client ID
COGNITO_CLIENT_SECRET=xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx  # Replace with your App Client Secret
COGNITO_REDIRECT_URI=http://localhost:3000/callback  # For development
COGNITO_LOGOUT_URI=http://localhost:3000/logout  # For development
```

For production, you should update the redirect and logout URIs to your production domain.

### 3. Test URLs for Development

For development purposes, you can use the following test URLs:
- Redirect URI: `http://localhost:3000/callback`
- Logout URI: `http://localhost:3000/logout`

These URLs can be changed later in the AWS Cognito Console when you move to production:
1. Go to the AWS Cognito Console
2. Select your user pool
3. Go to "App integration" > "App client settings"
4. Update the callback and sign-out URLs

### 4. Authentication Flow

The application uses the Authorization Code Grant flow:

1. User is redirected to the Cognito hosted UI for login
2. After successful authentication, Cognito redirects back to your application with an authorization code
3. Your backend exchanges this code for access and ID tokens
4. The tokens are validated using the JWT validation service
5. User roles and permissions are extracted from the token claims

## Docker Compose Configuration Details

The `docker-compose.yml` file includes the following services:

### MongoDB Service

```yaml
mongodb:
  image: mongo:latest
  container_name: mongodb
  ports:
    - "27017:27017"
  environment:
    - MONGO_INITDB_DATABASE=${MONGODB_DATABASE:-examensarbete}
  volumes:
    - mongo-data:/data/db
  networks:
    - app-network
  healthcheck:
    test: ["CMD", "mongo", "--eval", "db.adminCommand('ping')"]
    interval: 10s
    timeout: 5s
    retries: 5
    start_period: 10s
```

### Backend Service

```yaml
backend:
  build:
    context: ../..
    dockerfile: src/main/docker/Dockerfile
  container_name: backend
  depends_on:
    mongodb:
      condition: service_healthy
  environment:
    - SPRING_DATA_MONGODB_URI=mongodb://mongodb:27017/${MONGODB_DATABASE:-examensarbete}
    - MONGODB_DATABASE=${MONGODB_DATABASE:-examensarbete}
    - COGNITO_POOL_ID=${COGNITO_POOL_ID}
    - COGNITO_CLIENT_ID=${COGNITO_CLIENT_ID}
    - COGNITO_CLIENT_SECRET=${COGNITO_CLIENT_SECRET}
    - COGNITO_REDIRECT_URI=${COGNITO_REDIRECT_URI:-http://localhost:3000/callback}
  ports:
    - "8080:8080"
  networks:
    - app-network
  restart: unless-stopped
```

### Frontend Service

```yaml
frontend:
  build:
    context: ../frontend
    dockerfile: Dockerfile
  container_name: frontend
  ports:
    - "3000:80"
  depends_on:
    - backend
  networks:
    - app-network
  restart: unless-stopped
  environment:
    - REACT_APP_API_URL=http://localhost:8080/api
    - REACT_APP_COGNITO_REGION=${AWS_REGION:-eu-north-1}
    - REACT_APP_COGNITO_USER_POOL_ID=${COGNITO_POOL_ID}
    - REACT_APP_COGNITO_APP_CLIENT_ID=${COGNITO_CLIENT_ID}
    - REACT_APP_COGNITO_REDIRECT_URI=${COGNITO_REDIRECT_URI:-http://localhost:3000/callback}
```

## Troubleshooting

### Common Issues

1. **Connection refused to MongoDB**:
   - Ensure MongoDB container is running: `docker ps | grep mongodb`
   - Check MongoDB logs: `docker logs mongodb`

2. **Cognito authentication issues**:
   - Verify environment variables are correctly set
   - Check that callback URLs in Cognito console match your application
   - Ensure the JWT validation is working correctly

3. **Docker build failures**:
   - Check Docker logs: `docker-compose logs -f`
   - Ensure all required files are present
   - Verify Docker and Docker Compose versions are up to date

### Logs

To view logs for specific services:

```bash
# View backend logs
docker logs backend

# View frontend logs
docker logs frontend

# View MongoDB logs
docker logs mongodb
```

## Security Considerations

1. **Environment Variables**: Never commit `.env` files with real credentials to version control
2. **Cognito Client Secret**: Keep your client secret secure
3. **Production Setup**: For production, consider:
   - Using AWS Secrets Manager for sensitive information
   - Enabling HTTPS for all communications
   - Setting up proper network security groups
   - Implementing proper logging and monitoring
