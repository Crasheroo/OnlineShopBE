FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

EXPOSE 8084

COPY user-adapters/target/user-adapters-0.0.1-SNAPSHOT.jar app/user-adapters-0.0.1-SNAPSHOT.jar

RUN rm -rf /var/cache/apk/*

ENTRYPOINT ["java", "-jar", "app/user-adapters-0.0.1-SNAPSHOT.jar"]
