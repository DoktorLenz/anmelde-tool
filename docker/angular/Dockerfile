FROM nginx:latest

ENV CONFIG=prod

# Copy the build output to replace the default nginx contents.
COPY ./angular/dist/anmelde-tool-frontend /usr/share/nginx/html
# Copy custom config for nginx
COPY ./docker/angular/frontend.prod.conf /usr/share/nginx/config/prod.conf
COPY ./docker/angular/frontend.e2e.conf /usr/share/nginx/config/e2e.conf

# Copy init script
COPY ./docker/angular/init.sh /docker-entrypoint.d/init.sh

# Expose port 80
EXPOSE 80
