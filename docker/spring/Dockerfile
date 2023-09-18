FROM openjdk:17-alpine@sha256:4b6abae565492dbe9e7a894137c966a7485154238902f2f25e9dbd9784383d81

COPY ./spring/target/*.jar /app/anmelde-tool.jar

ENTRYPOINT ["java","-jar","/app/anmelde-tool.jar"]

HEALTHCHECK --interval=30s --timeout=10s --start-period=10s \
  CMD wget -qO- http://localhost:38080/actuator/health/readiness || exit 1

EXPOSE 38080

