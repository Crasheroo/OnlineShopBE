FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app/cart-service
COPY . .
RUN mvn clean package -DskipTests -pl :cart-adapters -am

FROM openjdk:21-jdk
WORKDIR /app
COPY --from=build /app/cart-service/adapters/target/cart-adapters-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]