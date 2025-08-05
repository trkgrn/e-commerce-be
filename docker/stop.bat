@echo off
echo Stopping e-commerce backend services...

REM Stop all services
docker-compose down

echo All services stopped.
pause 