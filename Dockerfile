FROM gradle:6.6.1-jdk8 as build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:8-alpine
VOLUME /tmp
ARG JAVA_OPTS
ENV JAVA_OPTS=$JAVA_OPTS
COPY --from=build /home/gradle/src/build/libs/*.jar calculator-service.jar
EXPOSE 8081
#ENTRYPOINT exec java $JAVA_OPTS -jar calculator-service.jar
# For Spring-Boot project, use the entrypoint below to reduce Tomcat startup time.
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar calculator-service.jar
