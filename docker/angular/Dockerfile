FROM nginx:1.25.4-alpine@sha256:db56449ba92783faa6f526d4ef1f837f64ba727e5c2660ec3fe83a1df04550b9

ENV CONFIG=e2e

# Copy the build output to replace the default nginx contents.
COPY ./angular/dist/scoutventure-spa /usr/share/nginx/html
# Copy custom config for nginx
COPY ./docker/angular/frontend.conf /etc/nginx/conf.d/default.conf

# Expose port 80
EXPOSE 80
