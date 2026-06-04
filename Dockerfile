# Build stage
FROM maven:3.9.11-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]


#FROM eclipse-temurin:17-jdk
#
#WORKDIR /app
#
#COPY target/quencallerie_mngt_v1-0.0.1-SNAPSHOT.jar app.jar
#
#EXPOSE 8080
#
#ENTRYPOINT ["java","-jar","app.jar"]



## Use Java 17
#FROM eclipse-temurin:21-jdk
#
#LABEL authors="raph"
#
## Working directory
#WORKDIR /app
#
## Copy jar
#COPY target/*.jar app.jar
#
## Expose Spring Boot port
#EXPOSE 8080
#
## Start application
#ENTRYPOINT ["java","-jar","app.jar"]