# Image containing a postgres server containing keycloak data prepared for movie-db.

FROM postgres:15.6@sha256:54569911ee29b01ced34f3d9d90723b1c6ac71828eec06598e312b36377d3226

# Default postgres-port
EXPOSE 5432

ENV POSTGRES_PASSWORD=password

# Copy Init-script and keycloak-dump
COPY init-scripts/database-initialization.sh /docker-entrypoint-initdb.d/database-initialization.sh
