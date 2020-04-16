
FROM openjdk:11-jdk
VOLUME /tmp
ARG JAVA_OPTS
ENV JAVA_OPTS=$JAVA_OPTS
ADD build/libs/calculator-service-0.0.1-SNAPSHOT.jar calculator-service.jar
EXPOSE 8080
#ENTRYPOINT exec java $JAVA_OPTS -jar calculator-service.jar
# For Spring-Boot project, use the entrypoint below to reduce Tomcat startup time.
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar calculator-service.jar
