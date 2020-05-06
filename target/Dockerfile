FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD movies-1.0.0.jar movies.jar
COPY moviedata.json moviedata.json
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-jar","/movies.jar"]