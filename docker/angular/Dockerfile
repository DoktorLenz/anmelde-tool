FROM nginx:1.27.1-alpine@sha256:c04c18adc2a407740a397c8407c011fc6c90026a9b65cceddef7ae5484360158

ENV CONFIG=e2e

# Copy the build output to replace the default nginx contents.
COPY ./angular/dist/scoutventure-spa /usr/share/nginx/html
# Copy custom config for nginx
COPY ./docker/angular/frontend.conf /etc/nginx/conf.d/default.conf

# Expose port 80
EXPOSE 80
