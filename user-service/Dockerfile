FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app
COPY . .
RUN mvn clean install -DskipTests

WORKDIR /app/user-service
RUN mvn clean package -DskipTests -pl :user-service -am

FROM openjdk:21-jdk
WORKDIR /app
COPY --from=build /app/user-service/target/user-service-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]