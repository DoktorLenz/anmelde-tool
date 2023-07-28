FROM nginx:1.25.1-alpine

ENV CONFIG=e2e

# Copy the build output to replace the default nginx contents.
COPY ./angular/dist/anmelde-tool-frontend /usr/share/nginx/html
# Copy custom config for nginx
COPY ./docker/angular/frontend.conf /etc/nginx/conf.d/default.conf

# Expose port 80
EXPOSE 80
