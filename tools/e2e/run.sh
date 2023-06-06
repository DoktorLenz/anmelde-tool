#! /bin/sh
docker-compose down

cd ../../angular
npm ci
npm run ci:clean
npm run config
npm run ci:build

cd ../spring
mvn clean package -DskipTests

cd ../tools/e2e
docker-compose build
docker-compose up -d

container_name="backend_e2e"

if ! docker ps -f name=$container_name | grep -q $container_name; then
    echo "Container $container_name is not running"
    exit 1
fi

while true; do
    health=$(docker inspect --format='{{json .State.Health.Status}}' $container_name | jq -r '.')
    if [ "$health" = "healthy" ]; then
        echo "Container is healthy!"
        break
    fi

    echo "Container is still starting up, health status: $health"
    sleep 5
done