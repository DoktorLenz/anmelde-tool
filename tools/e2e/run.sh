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

if ! docker ps -f name=backend_e2e | grep -q backend_e2e; then
    echo "Container backend_e2e is not running"
    exit 1
fi

backend_container_id=$(docker ps -f name=backend_e2e -q)

docker events --filter "container=$container_id" --filter "event=health_status" --format '{{.Status}}' > /tmp/docker-events.log 2>&1 &

# read the event log file and check for container health status
while read event; do
  if [ "$event" = "health_status: healthy" ]; then
    echo "Container backend_e2e is healthy"
    kill %1 # kill the background process that's reading the event log file
    break
  else
    echo "Container backend_e2e is not healthy"
  fi
done < <(tail -n 0 -f /tmp/docker-events.log)