#!/bin/bash
set -e
POSTGRES="psql --username postgres"

echo setting up databases...
$POSTGRES <<-EOSQL
CREATE USER anmelde_tool WITH PASSWORD 'anmelde_tool';
CREATE DATABASE anmelde_tool WITH OWNER 'anmelde_tool';

EOSQL

echo set-up complete.
echo import complete.
