FROM cockroachdb/cockroach:v23.1.10@sha256:86066f1566a3412afe8054ec658b3627377eeeb8e3ff8402989acf996ac1cf1a

ADD ./docker/cockroach/data.tar.gz /cockroach

CMD ["start-single-node", "--insecure"]