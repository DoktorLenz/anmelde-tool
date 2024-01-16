# Image containing a postgres server containing keycloak data prepared for movie-db.

FROM postgres:15.5@sha256:89d430f5f7e680e67f81de534eb459a3b86afd818e3330d9b18d29a6b9ec98cd

# Default postgres-port
EXPOSE 5432

ENV POSTGRES_PASSWORD=password

# Copy Init-script and keycloak-dump
COPY init-scripts/database-initialization.sh /docker-entrypoint-initdb.d/database-initialization.sh
