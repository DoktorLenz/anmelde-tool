FROM cockroachdb/cockroach:v23.2.4@sha256:04d9c32aa3acfc8261c62eb8748cf99dcaade563fb43f42b806df1f63757bd65

ADD ./docker/cockroach/data.tar.gz /cockroach

CMD ["start-single-node", "--insecure"]