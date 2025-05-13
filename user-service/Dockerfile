FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

EXPOSE 8083

COPY /target/user-service-0.0.1-SNAPSHOT.jar app/user-service-0.0.1-SNAPSHOT.jar

RUN rm -rf /var/cache/apk/*

ENTRYPOINT ["java", "-jar", "app/user-service-0.0.1-SNAPSHOT.jar"]
