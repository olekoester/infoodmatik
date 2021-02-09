#!/bin/sh
mvn clean package && docker build -t de.hsb.app/ifm .
docker rm -f ifm || true && docker run -d -p 8080:8080 -p 4848:4848 --name ifm de.hsb.app/ifm 
