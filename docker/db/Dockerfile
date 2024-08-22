# Image containing a postgres server containing keycloak data prepared for movie-db.

FROM postgres:15.8@sha256:0836104ba0de8d09e8d54e2d6a28389fbce9c0f4fe08f4aa065940452ec61c30

# Default postgres-port
EXPOSE 5432

ENV POSTGRES_PASSWORD=password

# Copy Init-script and keycloak-dump
COPY init-scripts/database-initialization.sh /docker-entrypoint-initdb.d/database-initialization.sh
