FROM openjdk:8u111-jdk-alpine
VOLUME /tmp
ADD /target/supervision-server-data.jar
ENTRYPOINT ["java", "-Djava.security.edg=file:/dev/./urandom", "-jar", "/supervision-server-data.jar"]
EXPOSE 8092
