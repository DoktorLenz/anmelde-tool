FROM cockroachdb/cockroach:v23.2.0@sha256:d4cae01f1db1b4056c75ada36cb6c3e4994fc84e38cf28930c4a9c8b191c9085

ADD ./docker/cockroach/data.tar.gz /cockroach

CMD ["start-single-node", "--insecure"]