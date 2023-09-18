FROM mailhog/mailhog:latest@sha256:8d76a3d4ffa32a3661311944007a415332c4bb855657f4f6c57996405c009bea

COPY ./docker/mailhog/authfile.txt authfile.txt

ENV MH_AUTHFILE=authfile.txt

EXPOSE 1025 8025