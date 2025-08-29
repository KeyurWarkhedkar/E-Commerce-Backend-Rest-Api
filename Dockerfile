#import base image(os and jdk)
FROM openjdk:21-jdk-slim

#set working directory inside the container
WORKDIR /app

#copy jar package from host to the container
COPY target/E-Commerce-0.0.1-SNAPSHOT.jar app.jar

#set the entry point(the command to start your app when the container is instantiated)
ENTRYPOINT ["java", "-jar", "app.jar"]

