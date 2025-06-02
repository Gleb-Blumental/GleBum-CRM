@echo off
echo ===== Docker Compose Setup Script =====

echo Copying .env file from project root to docker directory...
copy ..\..\..\.env .env

echo Starting Docker Compose...
docker-compose up -d

echo Docker Compose started successfully!
echo You can access:
echo - Backend API: http://localhost:8080
echo - Frontend (if enabled): http://localhost:3000
echo - MongoDB: mongodb://localhost:27017

echo To view logs, run: docker-compose logs -f
echo To stop containers, run: docker-compose down
