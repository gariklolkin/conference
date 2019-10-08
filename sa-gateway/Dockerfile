FROM openjdk:8-jdk-alpine
COPY build/libs/sa-gateway*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
EXPOSE 8082