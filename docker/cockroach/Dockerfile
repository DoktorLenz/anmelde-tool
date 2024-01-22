FROM cockroachdb/cockroach:v23.1.14@sha256:4d02d0a7dc02a5e1a2ba0dcd06a878d97693532cf0a0b0a32ee7d44521b5e958

ADD ./docker/cockroach/data.tar.gz /cockroach

CMD ["start-single-node", "--insecure"]