#!/bin/bash
if [ "$CONFIG" == "e2e" ]; then
    cp /usr/share/nginx/config/e2e.conf /etc/nginx/conf.d/default.conf
elif [ "$CONFIG" == "prod" ]; then
    cp /usr/share/nginx/config/prod.conf /etc/nginx/conf.d/default.conf
else
    echo "Unknown configuration value: $CONFIG"
    exit 1
fi