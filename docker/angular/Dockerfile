FROM nginx:1.27.0-alpine@sha256:208b70eefac13ee9be00e486f79c695b15cef861c680527171a27d253d834be9

ENV CONFIG=e2e

# Copy the build output to replace the default nginx contents.
COPY ./angular/dist/scoutventure-spa /usr/share/nginx/html
# Copy custom config for nginx
COPY ./docker/angular/frontend.conf /etc/nginx/conf.d/default.conf

# Expose port 80
EXPOSE 80
