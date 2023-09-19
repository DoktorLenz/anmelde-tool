# Image containing a postgres server containing keycloak data prepared for movie-db.

FROM postgres:15.4@sha256:d9b1b0ec74e734772a2e76c72d36a51de21f3842a6c872cf356a26a0a6b903d2

# Default postgres-port
EXPOSE 5432

ENV POSTGRES_PASSWORD=password

# Copy Init-script and keycloak-dump
COPY init-scripts/database-initialization.sh /docker-entrypoint-initdb.d/database-initialization.sh
