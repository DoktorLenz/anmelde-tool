#! /bin/sh
docker-compose down

cd ../../angular
npm run ci:clean
npm run config
npm run ci:build

cd ../spring
mvn clean package -DskipTests

cd ../tools/e2e
docker-compose build
docker-compose up -d