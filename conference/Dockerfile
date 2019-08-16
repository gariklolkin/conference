FROM openjdk:8-jre-alpine

ARG JAR_FILE=./build/libs/conference-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} /usr/src/conference.jar

EXPOSE 8080

CMD java -jar /usr/src/conference.jar