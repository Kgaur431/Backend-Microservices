FROM openjdk:20-slim

COPY build/libs/Backend_Microservices-0.0.1-SNAPSHOT.jar ./app.jar

CMD ["java", "-jar", "/app.jar"]

