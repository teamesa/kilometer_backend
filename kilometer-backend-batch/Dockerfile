FROM openjdk:11
ARG JAR_FILE=build/libs/kilometer-backend-batch-1.0.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT java ${JAVA_OPTS} -jar app.jar ${JOB_PARAMETERS}
EXPOSE 8080
