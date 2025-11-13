FROM eclipse-temurin:21-jre-alpine

RUN addgroup -S nobody && adduser -S nobody -G nobody

WORKDIR /app

COPY build/libs/*.jar app.jar

USER nobody

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
