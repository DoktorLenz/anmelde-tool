FROM cockroachdb/cockroach:v23.2.9@sha256:ffb246a699a8963cb5e18f77e572e281bd31de03f687231d8efefa0d89682248

ADD ./docker/cockroach/data.tar.gz /cockroach

CMD ["start-single-node", "--insecure"]