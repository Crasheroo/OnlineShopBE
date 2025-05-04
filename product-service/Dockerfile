FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app/product-service
COPY . .
RUN mvn clean package -DskipTests -pl :product-adapters -am

FROM openjdk:21-jdk
WORKDIR /app
COPY --from=build /app/product-service/product-adapters/target/product-adapters-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]