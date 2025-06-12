#!/bin/bash

docker compose down app
docker rmi api-sms-app:latest
git pull
docker compose up -d app