FROM nginx:1.25.2-alpine@sha256:16164a43b5faec40adb521e98272edc528e74f31c1352719132b8f7e53418d70

ENV CONFIG=e2e

# Copy the build output to replace the default nginx contents.
COPY ./angular/dist/anmelde-tool-frontend /usr/share/nginx/html
# Copy custom config for nginx
COPY ./docker/angular/frontend.conf /etc/nginx/conf.d/default.conf

# Expose port 80
EXPOSE 80
