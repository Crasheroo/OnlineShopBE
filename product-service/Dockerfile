FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

EXPOSE 8081

COPY /product-adapters/target/product-adapters-0.0.1-SNAPSHOT.jar app/product-adapters-0.0.1-SNAPSHOT.jar

RUN rm -rf /var/cache/apk/*

ENTRYPOINT ["java", "-jar", "app/product-adapters-0.0.1-SNAPSHOT.jar"]
