# E-Commerce Backend

A microservices-based e-commerce backend built with Spring Boot and Spring Cloud, designed to provide scalable and maintainable e-commerce functionality. Development is still ongoing...

## 🚀 Technologies

### Core Framework
- **Spring Boot 3.3.2** - Main application framework
- **Spring Cloud 4.1.3** - Microservices infrastructure
- **Java 17** - Programming language
- **Kotlin 1.9.22** - Additional language support for data classes and utilities

### Microservices Architecture
- **Spring Cloud Gateway** - API Gateway for routing and load balancing
- **Netflix Eureka** - Service discovery and registration
- **OpenFeign** - Declarative HTTP client for service-to-service communication

### Database & Storage
- **PostgreSQL** - Primary relational database for most services
- **MongoDB** - Document database for file service
- **Redis** - Key-Value token storage for authentication service
- **Elasticsearch** - Search engine for product service

### Security & Authentication
- **Spring Security** - Security framework
- **JWT (JSON Web Tokens)** - Stateless authentication

### Monitoring & Observability
- **Spring Boot Actuator** - Application monitoring
- **Micrometer** - Metrics collection
- **Zipkin** - Distributed tracing
- **Elasticsearch + Kibana** - Log aggregation and visualization
- **Logstash** - Log processing pipeline

### Development Tools
- **MapStruct** - Object mapping library
- **Swagger/OpenAPI** - API documentation
- **Gradle** - Build tool

## 🏗️ Architecture

This project follows a microservices architecture pattern with the following services:

### Core Services

#### 1. **Discovery Service**
- **Purpose**: Service registry and discovery using Netflix Eureka
- **Port**: 8761
- **Database**: None (in-memory registry)

#### 2. **Gateway Service**
- **Purpose**: API Gateway for routing requests to appropriate microservices
- **Port**: 8080
- **Features**: Request routing, load balancing, security filtering

#### 3. **Auth Service**
- **Purpose**: Authentication and authorization management
- **Port**: 9091
- **Database**: Redis (for token storage)
- **Features**: JWT token generation, user authentication, session management

#### 4. **User Service**
- **Purpose**: User management and profile operations
- **Port**: 9090
- **Database**: PostgreSQL
- **Features**: User CRUD operations, profile management

#### 5. **Product Service**
- **Purpose**: Product catalog and inventory management
- **Port**: 9094
- **Database**: PostgreSQL + Elasticsearch
- **Features**: Product CRUD, search functionality, batch processing

#### 6. **Product Gallery Service**
- **Purpose**: Product image and media management
- **Port**: 9095
- **Database**: PostgreSQL
- **Features**: Product image management, gallery operations

#### 7. **Media Service**
- **Purpose**: Media content management
- **Port**: 9093
- **Database**: PostgreSQL
- **Features**: Media file management, content operations

#### 8. **File Service**
- **Purpose**: File storage and management
- **Port**: 9092
- **Database**: MongoDB
- **Features**: File upload/download, storage management

### Shared Modules

#### **Common Module**
- Shared DTOs, constants, and utilities
- Feign clients for inter-service communication
- Common configurations and interceptors

#### **Security Module**
- Security configurations and filters
- Common security utilities

## 🐳 Quick Start

### Prerequisites
- Docker and Docker Compose
- Java 17
- Gradle 7.x

### Running with Docker

For detailed Docker setup instructions, see [docker/README.md](docker/README.md).

## 📊 Monitoring & Observability

### Available Endpoints
- **Gateway**: http://localhost:8080
- **Discovery Service**: http://localhost:8761
- **Zipkin Tracing**: http://localhost:9411
- **Product Service Kibana**: http://localhost:5601
- **Logging Kibana**: http://localhost:5602

### Health Checks
Each service provides health check endpoints at `/actuator/health`

## 🔧 Configuration

Services are configured using Spring Boot profiles:
- `dev` - Development environment
- `prod` - Production environment

Configuration files are located in each service's `src/main/resources/` directory.

## 📝 API Documentation

Each service provides Swagger/OpenAPI documentation at:
- `http://localhost:{service-port}/swagger-ui.html`
