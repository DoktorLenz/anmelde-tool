FROM cockroachdb/cockroach:v23.1.13@sha256:24c67d8e5bfc843289f2250e2e46d54b0c5dc89683d90dc37319c355f0c34839

ADD ./docker/cockroach/data.tar.gz /cockroach

CMD ["start-single-node", "--insecure"]