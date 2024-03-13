# Image containing a postgres server containing keycloak data prepared for movie-db.

FROM postgres:15.6@sha256:08ababf6ea4e32158b90673bc409302b3c4c38eb9aca71890966f2d28115d82f

# Default postgres-port
EXPOSE 5432

ENV POSTGRES_PASSWORD=password

# Copy Init-script and keycloak-dump
COPY init-scripts/database-initialization.sh /docker-entrypoint-initdb.d/database-initialization.sh
