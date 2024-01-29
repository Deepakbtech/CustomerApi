FROM openjdk:17-jdk-slim-buster
CMD ["./gradlew", "clean", "bootJar"]
COPY build/libs/*.jar app.jar
WORKDIR /customer

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]