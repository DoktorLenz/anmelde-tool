FROM openjdk:17

COPY ./spring/target/*.jar /app/anmelde-tool.jar

ENTRYPOINT ["java","-jar","/app/anmelde-tool.jar"]

EXPOSE 8080

