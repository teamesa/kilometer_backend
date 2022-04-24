FROM openjdk:11
ARG JAR_FILE=build/libs/esa-backend-backoffice-1.0.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
RUN mkdir -p /data/image
EXPOSE 8080