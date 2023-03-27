FROM nginx:1.21.6-alpine

ENV ENVIRONMENT local
# Copy the build output to replace the default nginx contents.
COPY  ./angular/dist/anmelde-tool-frontend /usr/share/nginx/html
# Copy custom config for nginx
COPY  ./docker/angular/frontend.conf /etc/nginx/conf.d/default.conf

# Expose port 80
EXPOSE 80
