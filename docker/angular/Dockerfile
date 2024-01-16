FROM nginx:1.25.3-alpine@sha256:a59278fd22a9d411121e190b8cec8aa57b306aa3332459197777583beb728f59

ENV CONFIG=e2e

# Copy the build output to replace the default nginx contents.
COPY ./angular/dist/anmelde-tool-frontend /usr/share/nginx/html
# Copy custom config for nginx
COPY ./docker/angular/frontend.conf /etc/nginx/conf.d/default.conf

# Expose port 80
EXPOSE 80
