FROM openjdk:17-alpine

COPY ./spring/target/*.jar /app/anmelde-tool.jar

ENTRYPOINT ["java","-jar","/app/anmelde-tool.jar"]

HEALTHCHECK --interval=30s --timeout=10s --start-period=10s \
  CMD wget -qO- http://localhost:8080/actuator/health/readiness || exit 1

EXPOSE 8080

