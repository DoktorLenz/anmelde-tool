FROM mailhog/mailhog:latest

COPY ./docker/mailhog/authfile.txt authfile.txt

ENV MH_AUTHFILE=authfile.txt

EXPOSE 1025 8025