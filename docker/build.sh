#!/bin/bash

echo "Building all services..."

# Build discovery service
echo "Building discovery-service..."
cd ../discovery-service
./gradlew build -x test
cd ../docker

# Build gateway service
echo "Building gateway-service..."
cd ../gateway-service
./gradlew build -x test
cd ../docker

# Build auth service
echo "Building auth-service..."
cd ../auth-service
./gradlew build -x test
cd ../docker

# Build user service
echo "Building user-service..."
cd ../user-service
./gradlew build -x test
cd ../docker

# Build file service
echo "Building file-service..."
cd ../file-service
./gradlew build -x test
cd ../docker

# Build media service
echo "Building media-service..."
cd ../media-service
./gradlew build -x test
cd ../docker

# Build product service
echo "Building product-service..."
cd ../product-service
./gradlew build -x test
cd ../docker

# Build product gallery service
echo "Building product-gallery-service..."
cd ../product-gallery-service
./gradlew build -x test
cd ../docker

echo "All services built successfully!" 