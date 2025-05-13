FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

EXPOSE 8082

COPY /order-adapters/target/order-adapters-0.0.1-SNAPSHOT.jar app/order-adapters-0.0.1-SNAPSHOT.jar

RUN rm -rf /var/cache/apk/*

ENTRYPOINT ["java", "-jar", "app/order-adapters-0.0.1-SNAPSHOT.jar"]
