#!/bin/bash

docker build -t tcpatter/sentimenty ./
wd=$(pwd)
docker run -v "${wd}/app":/home/sentimenty -p 6060:8080 --name sentimenty -d tcpatter/sentimenty
