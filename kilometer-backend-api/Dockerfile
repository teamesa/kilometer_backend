FROM openjdk:11
ARG JAR_FILE=build/libs/kilometer-backend-api-1.0.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT java ${JAVA_OPTS} -jar app.jar
EXPOSE 8080