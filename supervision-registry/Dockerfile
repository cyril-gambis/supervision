FROM openjdk:8u111-jdk-alpine
VOLUME /tmp
ADD /target/supervision-registry.jar
ENTRYPOINT ["java", "-Djava.security.edg=file:/dev/./urandom", "-jar", "/supervision-registry.jar"]
EXPOSE 8092
