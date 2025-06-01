# Dockerfile
FROM maven:3.9.9-eclipse-temurin-23 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:23-jdk-slim
COPY --from=build /lec21.3-0.0.1-SNAPSHOT.jar taco.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "taco.jar"]