FROM alpine
RUN apk add --no-cache openjdk17-jre
COPY rest-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "rest-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080

