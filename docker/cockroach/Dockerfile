FROM cockroachdb/cockroach:v23.1.9

ADD ./docker/cockroach/data.tar.gz /cockroach

CMD ["start-single-node", "--insecure"]