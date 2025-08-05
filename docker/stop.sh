#!/bin/bash

echo "Stopping e-commerce backend services..."

# Stop all services
docker-compose down

echo "All services stopped." 