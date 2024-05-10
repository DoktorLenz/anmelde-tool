# Image containing a postgres server containing keycloak data prepared for movie-db.

FROM postgres:15.7@sha256:5c5e4c4e2e0c00f61cb506c32041e2255d7f18b8cbff9f97c979dc3614d67ab9

# Default postgres-port
EXPOSE 5432

ENV POSTGRES_PASSWORD=password

# Copy Init-script and keycloak-dump
COPY init-scripts/database-initialization.sh /docker-entrypoint-initdb.d/database-initialization.sh
