FROM cockroachdb/cockroach:v23.1.1

ADD ./docker/cockroach/data.tar.gz /cockroach

CMD ["start-single-node", "--insecure"]