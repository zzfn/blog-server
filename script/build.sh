#!/usr/bin/env sh
docker build -t 127.0.0.1:5000/jello .
docker push 127.0.0.1:5000/jello:latest
docker image prune -f