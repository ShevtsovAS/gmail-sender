@echo off
set /p IMAGE_NAME="Image name: "
set /p TAG="Tag: "
set /p DOCKER_USER="Docker username: "
set /p GMAIL_USER="Gmail username: "
set /p GMAIL_PASSWORD="Gmail password: "
mvn clean package