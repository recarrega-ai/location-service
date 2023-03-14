FROM openjdk:17-jdk-alpine
VOLUME /tmp
COPY target/location-service-0.0.1-SNAPSHOT.jar app.jar
COPY src/main/resources/application.properties application.properties
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
