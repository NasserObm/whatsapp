#!/bin/bash

docker compose down app
docker rmi kazie-app:latest
git pull
docker compose up -d app