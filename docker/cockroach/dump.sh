#! /bin/sh

echo "Checking sessions..."

# Make sure there is no open session that may write data to db
ip_list=$(cockroach sql --insecure --host=127.0.0.1:26257 -e "SHOW SESSIONS" \
 | grep -oE '[0-9]+\.[0-9]+\.[0-9]+\.[0-9]+' \
 | grep -v '127\.0\.0\.1')

if [ -n "$ip_list" ]; then
    echo "Please terminate all pending sessions"
    exit 1
fi

echo "No pending sessions"
echo "Dumping..."

## Create tar of data (mounted in the container)
tar -czf data.tar.gz cockroach-data

echo "Done"