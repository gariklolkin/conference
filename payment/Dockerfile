FROM openjdk:11.0.3-jdk

ARG JAR_FILE=./build/libs/payment-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} payment.jar

EXPOSE 8080

CMD java -jar /payment.jar