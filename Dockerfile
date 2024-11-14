# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim 
#use JRE alphine for ligheter

WORKDIR /app

COPY target/classifier-0.0.1-SNAPSHOT.jar classifier.jar

EXPOSE 9090

ENTRYPOINT ["java", "-jar", "classifier.jar"]
