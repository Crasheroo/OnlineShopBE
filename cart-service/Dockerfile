FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

EXPOSE 8080

COPY /adapters/target/cart-adapters-0.0.1-SNAPSHOT.jar app/cart-adapters-0.0.1-SNAPSHOT.jar

RUN rm -rf /var/cache/apk/*

ENTRYPOINT ["java", "-jar", "app/cart-adapters-0.0.1-SNAPSHOT.jar"]
