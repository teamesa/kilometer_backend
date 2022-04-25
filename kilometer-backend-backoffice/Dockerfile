FROM openjdk:11
ARG JAR_FILE=build/libs/kilometer-backend-backoffice-1.0.jar
COPY ${JAR_FILE} app.jar
RUN mkdir -p /data/image
ENTRYPOINT java ${JAVA_OPTS} -jar app.jar
EXPOSE 8080