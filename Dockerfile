FROM openjdk:8-jdk-alpine

MAINTAINER Tom Cheung <cheungtom@hotmail.com>
VOLUME /tmp
VOLUME /target

COPY ./target/app.war /target/app.war

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/target/app.war"]
