#!/bin/bash

echo "Starting e-commerce backend services..."

# Copy environment file if it doesn't exist
if [ ! -f .env ]; then
    echo "Creating .env file from env.example..."
    cp env.example .env
fi

# Build and start all services
docker-compose up --build -d

echo "Services are starting up..."
echo "You can check the status with: docker-compose ps"
echo "View logs with: docker-compose logs -f [service-name]"
echo ""
echo "Service URLs:"
echo "- Discovery Service: http://localhost:8761"
echo "- Gateway Service: http://localhost:8080"
echo "- Auth Service: http://localhost:9091"
echo "- User Service: http://localhost:9090"
echo "- File Service: http://localhost:9092"
echo "- Media Service: http://localhost:9093"
echo "- Product Service: http://localhost:9094"
echo "- Product Gallery Service: http://localhost:9095"
echo "- Kibana (Product): http://localhost:5601"
echo "- Kibana (Logging): http://localhost:5602"
echo "- Zipkin: http://localhost:9411" 