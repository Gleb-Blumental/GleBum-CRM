# CRM System - Gleb Blumental

A comprehensive Customer Relationship Management (CRM) system built with Spring Boot and MongoDB. This application provides functionality for managing customers, orders, products, services, tickets, and tasks.

## Table of Contents

- [Technologies](#technologies)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Environment Configuration](#environment-configuration)
- [Running the Application](#running-the-application)
- [API Documentation](#api-documentation)
- [Project Structure](#project-structure)
- [Authentication](#authentication)
- [Database](#database)
- [Deployment](#deployment)
- [CI/CD](#cicd)
- [License](#license)

## Technologies

- Java 17
- Spring Boot 3.4.4
- Spring Security
- Spring Data MongoDB
- MongoDB Atlas
- AWS Cognito for Authentication
- Maven
- Docker
- Jenkins (CI/CD)

## Prerequisites

Before you begin, ensure you have the following installed:

- Java 17 or higher
- Maven
- Docker (optional, for containerization)
- MongoDB Compass (optional, for database visualization)

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/examensarbete.git
   cd examensarbete
   ```

2. Install dependencies:
   ```bash
   ./mvnw clean install
   ```

## Environment Configuration

The application uses environment variables for configuration. Create a `.env` file in the root directory with the following variables:

```
# MongoDB Atlas Configuration
MONGODB_URI=mongodb+srv://username:password@cluster.mongodb.net/?retryWrites=true&w=majority&appName=app-name
MONGODB_DATABASE=examensarbete

# AWS Cognito Configuration
COGNITO_POOL_ID=your-pool-id
COGNITO_CLIENT_ID=your-client-id
COGNITO_CLIENT_SECRET=your-client-secret
```

Make sure to replace the placeholder values with your actual configuration.

## Running the Application

### Using Maven

```bash
./mvnw spring-boot:run
```

### Using Docker

1. Build the Docker image:
   ```bash
   docker build -t examensarbete .
   ```

2. Run the container:
   ```bash
   docker run -p 8080:8080 --env-file .env examensarbete
   ```

### Using Docker Compose

```bash
docker-compose up
```

The application will be available at `http://localhost:8080`.

## API Documentation

### Customer API

- `GET /api/customers` - Get all customers
- `GET /api/customers/{id}` - Get customer by ID
- `POST /api/customers` - Create a new customer
- `PUT /api/customers/{id}` - Update a customer
- `DELETE /api/customers/{id}` - Delete a customer

### Order API

- `GET /api/orders` - Get all orders
- `GET /api/orders/{id}` - Get order by ID
- `POST /api/orders` - Create a new order
- `PUT /api/orders/{id}` - Update an order
- `DELETE /api/orders/{id}` - Delete an order

### Product API

- `GET /api/products` - Get all products
- `GET /api/products/{id}` - Get product by ID
- `POST /api/products` - Create a new product
- `PUT /api/products/{id}` - Update a product
- `DELETE /api/products/{id}` - Delete a product

### Service API

- `GET /api/services` - Get all services
- `GET /api/services/{id}` - Get service by ID
- `POST /api/services` - Create a new service
- `PUT /api/services/{id}` - Update a service
- `DELETE /api/services/{id}` - Delete a service

### Ticket API

- `GET /api/tickets` - Get all tickets
- `GET /api/tickets/{id}` - Get ticket by ID
- `POST /api/tickets` - Create a new ticket
- `PUT /api/tickets/{id}` - Update a ticket
- `DELETE /api/tickets/{id}` - Delete a ticket

### Task API

- `GET /api/tasks` - Get all tasks
- `GET /api/tasks/{id}` - Get task by ID
- `POST /api/tasks` - Create a new task
- `PUT /api/tasks/{id}` - Update a task
- `DELETE /api/tasks/{id}` - Delete a task

### Worker API

- `GET /api/workers` - Get all workers
- `GET /api/workers/{id}` - Get worker by ID
- `POST /api/workers` - Create a new worker
- `PUT /api/workers/{id}` - Update a worker
- `DELETE /api/workers/{id}` - Delete a worker

### Admin API

- `GET /api/admins` - Get all admins
- `GET /api/admins/{id}` - Get admin by ID
- `POST /api/admins` - Create a new admin
- `PUT /api/admins/{id}` - Update an admin
- `DELETE /api/admins/{id}` - Delete an admin

## Project Structure

```
src/
├── main/
│   ├── docker/
│   │   └── Dockerfile
│   ├── frontend/
│   │   └── Dockerfile
│   ├── java/
│   │   └── gleb/
│   │       └── blum/
│   │           └── examensarbete/
│   │               ├── CognitoAuth/
│   │               │   ├── CognitoJwtAuthFilter.java
│   │               │   ├── SecurityConfig.java
│   │               │   └── TokenValidationService.java
│   │               ├── config/
│   │               │   ├── DataInitializer.java
│   │               │   └── MongoConfig.java
│   │               ├── controllers/
│   │               │   ├── AdminController.java
│   │               │   ├── CustomerController.java
│   │               │   ├── OrderController.java
│   │               │   ├── ProductController.java
│   │               │   ├── ServiceController.java
│   │               │   ├── TaskController.java
│   │               │   ├── TicketController.java
│   │               │   └── WorkerController.java
│   │               ├── DTO/
│   │               │   ├── CustomerDTO.java
│   │               │   ├── OrderDTO.java
│   │               │   ├── ProductDTO.java
│   │               │   ├── ServiceDTO.java
│   │               │   ├── TaskDTO.java
│   │               │   ├── TicketDTO.java
│   │               │   └── WorkerDTO.java
│   │               ├── exceptions/
│   │               │   ├── ErrorResponse.java
│   │               │   ├── GlobalExceptionHandler.java
│   │               │   └── ResourceNotFoundException.java
│   │               ├── models/
│   │               │   ├── Admin.java
│   │               │   ├── AdminRole.java
│   │               │   ├── Contact.java
│   │               │   ├── CRM.java
│   │               │   ├── Customer.java
│   │               │   ├── Manager.java
│   │               │   ├── Order.java
│   │               │   ├── Product.java
│   │               │   ├── Service.java
│   │               │   ├── Task.java
│   │               │   ├── TaskStatus.java
│   │               │   ├── Ticket.java
│   │               │   └── Worker.java
│   │               ├── repositories/
│   │               │   ├── AdminRepository.java
│   │               │   ├── ContactRepository.java
│   │               │   ├── CRMRepository.java
│   │               │   ├── CustomerRepository.java
│   │               │   ├── ManagerRepository.java
│   │               │   ├── OrderRepository.java
│   │               │   ├── ProductRepository.java
│   │               │   ├── ServiceRepository.java
│   │               │   ├── TaskRepository.java
│   │               │   ├── TicketRepository.java
│   │               │   └── WorkerRepository.java
│   │               ├── services/
│   │               │   ├── AdminService.java
│   │               │   ├── CustomerService.java
│   │               │   ├── OrderService.java
│   │               │   ├── ProductService.java
│   │               │   ├── ServiceService.java
│   │               │   ├── TaskService.java
│   │               │   ├── TicketService.java
│   │               │   └── WorkerService.java
│   │               ├── status/
│   │               │   ├── CustomerStatus.java
│   │               │   ├── OrderStatus.java
│   │               │   └── TicketStatus.java
│   │               ├── validation/
│   │               │   └── CustomerValidation.java
│   │               └── ExamensarbeteApplication.java
│   ├── jenkins/
│   │   └── Jenkinsfile
│   └── resources/
│       ├── application.properties
│       ├── application.yml
│       ├── static/
│       └── templates/
└── test/
    └── java/
        └── gleb/
            └── blum/
                └── examensarbete/
                    └── ExamensarbeteApplicationTests.java
```

## Authentication

This application uses AWS Cognito for authentication. The authentication flow is as follows:

1. User authenticates with AWS Cognito and receives a JWT token
2. The token is included in the Authorization header of subsequent requests
3. The CognitoJwtAuthFilter validates the token for each request
4. If the token is valid, the request is processed; otherwise, it is rejected

## Database

The application uses MongoDB Atlas as its database. The database schema includes the following collections:

- admins
- contacts
- crm
- customers
- managers
- orders
- products
- services
- tasks
- tickets
- workers

### Connecting to MongoDB

#### Using MongoDB Compass

1. Download and install [MongoDB Compass](https://www.mongodb.com/products/compass)
2. Open MongoDB Compass
3. Use the connection string from your `.env` file
4. Click "Connect"

#### Using MongoDB Shell

1. Install [MongoDB Shell](https://www.mongodb.com/try/download/shell)
2. Open a terminal or command prompt
3. Run the following command:
   ```
   mongosh "your-connection-string"
   ```

### WIP

## Deployment 

### Docker Deployment

1. Build the Docker image:
   ```bash
   docker build -t examensarbete .
   ```

2. Run the container:
   ```bash
   docker run -p 8080:8080 --env-file .env examensarbete
   ```

### Docker Compose Deployment

```bash
docker-compose up -d
```

## CI/CD

This project uses Jenkins for continuous integration and continuous deployment. The Jenkinsfile in the `src/main/jenkins` directory defines the pipeline.

### Jenkins Pipeline Stages

1. **Checkout**: Retrieves the source code from the repository
2. **Build**: Compiles the application and runs tests
3. **Docker Build**: Creates a Docker image
4. **Docker Push**: Pushes the Docker image to a registry
5. **Deploy**: Deploys the application to the target environment


## License

This project is licensed under the MIT License - see the LICENSE file for details.
