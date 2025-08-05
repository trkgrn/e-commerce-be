@echo off
echo Building all services...

REM Build discovery service
echo Building discovery-service...
cd ..\discovery-service
call ..\gradlew.bat build -x test
cd ..\docker

REM Build gateway service
echo Building gateway-service...
cd ..\gateway-service
call ..\gradlew.bat build -x test
cd ..\docker

REM Build auth service
echo Building auth-service...
cd ..\auth-service
call ..\gradlew.bat build -x test
cd ..\docker

REM Build user service
echo Building user-service...
cd ..\user-service
call ..\gradlew.bat build -x test
cd ..\docker

REM Build file service
echo Building file-service...
cd ..\file-service
call ..\gradlew.bat build -x test
cd ..\docker

REM Build media service
echo Building media-service...
cd ..\media-service
call ..\gradlew.bat build -x test
cd ..\docker

REM Build product service
echo Building product-service...
cd ..\product-service
call ..\gradlew.bat build -x test
cd ..\docker

REM Build product gallery service
echo Building product-gallery-service...
cd ..\product-gallery-service
call ..\gradlew.bat build -x test
cd ..\docker

echo All services built successfully!
pause 