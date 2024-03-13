FROM cockroachdb/cockroach:v23.2.2@sha256:0fbe5bf6bf2077be0759608315b6a2f5451ce56854755fb55d53f5f483c2b274

ADD ./docker/cockroach/data.tar.gz /cockroach

CMD ["start-single-node", "--insecure"]