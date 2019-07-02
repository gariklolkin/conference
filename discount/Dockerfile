FROM openjdk:11.0.3-jdk

ARG JAR_FILE=./build/libs/discount-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} discount.jar

EXPOSE 8080

CMD java -jar /discount.jar