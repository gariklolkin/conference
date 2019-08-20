FROM openjdk:8-jdk-alpine
COPY build/libs/sponsorship*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
EXPOSE 8080