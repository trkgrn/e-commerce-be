# E-Commerce Backend Docker Setup

This directory contains the necessary files to run all services of the e-commerce backend project in Docker.

## Services

### Core Services
- **Discovery Service** (Eureka Server) - Port 8761
- **Gateway Service** (Spring Cloud Gateway) - Port 8080

### Business Services
- **Auth Service** (Redis) - Port 9091
- **User Service** (PostgreSQL) - Port 9090
- **File Service** (MongoDB) - Port 9092
- **Media Service** (PostgreSQL) - Port 9093
- **Product Service** (PostgreSQL + Elasticsearch) - Port 9094
- **Product Gallery Service** (PostgreSQL) - Port 9095

### Infrastructure Services
- **Redis** - Port 6372
- **PostgreSQL** (5 instance) - Ports 6948-6951, 27017
- **MongoDB** - Port 27017
- **Elasticsearch** - Port 9200
- **Kibana** (Product) - Port 5601
- **Kibana** (Logging) - Port 5602
- **Zipkin** - Port 9411
- **Logstash** - Port 5000

## Installation and Running

### 1. Requirements
- Docker
- Docker Compose
- Java 17
- Gradle

### 2. Environment File
```bash
cp env.example .env
```

### 3. Building Services

#### Windows Command Prompt:
```cmd
build.bat
```

#### Linux/Mac/Git Bash:
```bash
chmod +x build.sh
./build.sh
```

### 4. Starting Services

#### Windows Command Prompt:
```cmd
start.bat
```

#### Linux/Mac/Git Bash:
```bash
chmod +x start.sh
./start.sh
```

### 5. Stopping Services

#### Windows Command Prompt:
```cmd
stop.bat
```

#### Linux/Mac/Git Bash:
```bash
chmod +x stop.sh
./stop.sh
```