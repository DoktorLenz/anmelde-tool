# Update data dump
## Step 1 - Update the stack
Add the following volume to the crdb service in the `docker-compose.yaml`
```yaml
services:
  ...
  crdb:
    ...
    volumes:
      - "../../docker/cockroach/data.tar.gz:/cockroach/data.tar.gz:rw"
      - "../../docker/cockroach/dump.sh:/cockroach/dump.sh:ro"
```
Update the stack by running `docker-compose up`

## Step 2 - Make changes
Now you can update the zitadel data or whatever you want to update.

## Step 3 - Dump the data
Execute the mounted `dump.sh` script. \
The dump will fail if there are pending sessions. \
Please close all pending sessions, by e.g. stopping the zitadel-container, and run the script again.
This will update the mounted `data.tar.gz`

## Step 4 - Apply new dump
Tear down the stack with `docker-compose down` \
Apply the new dump by executing `docker-compose build` \
Start the stack with `docker-compose up`