FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY target/quencallerie_mngt_v1-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]



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