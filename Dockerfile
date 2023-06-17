FROM maven:3.8.4-openjdk-17-slim
#FROM openjdk:17-slim
#RUN mvn clean package
COPY . .
COPY target/contacts-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8888
CMD ["java", "-jar", "app.jar"]


##FROM maven:3.8.2-jdk-8
##
##WORKDIR /
##COPY . .
##RUN mvn clean install
##
##CMD mvn spring-boot:run
#
#
### Stage 1: Build the application with Maven
## Use a base image with Java and Maven installed
#FROM maven:3.8.4-openjdk-17-slim AS build
#
## Set the working directory in the container
##WORKDIR .
#
## Copy the project files to the working directory
#COPY . .
##COPY src ./src
#
## Build the application using Maven
#RUN mvn clean package
#
#
### Stage 2: Create a lightweight Docker image
## Use a lightweight base image with Java installed
#FROM openjdk:17-slim
#
## Set the working directory in the container
##WORKDIR /contacts
#
## Copy the built JAR file from the build stage to the container
#COPY --from=build /target/contacts-0.0.1-SNAPSHOT.jar ./app.jar
#
### Set the environment variables
##ENV SPRING_DATASOURCE_URL="jdbc:mysql://mysql:3308/contactsdb?useSSL=false&createDatabaseIfNotExist=true"
##ENV SPRING_DATASOURCE_USERNAME=root
##ENV SPRING_DATASOURCE_DRIVER_CLASS_NAME=com.mysql.cj.jdbc.Driver
##ENV SPRING_JPA_DATABASE_PLATFORM=org.hibernate.dialect.MySQL8Dialect
##ENV SPRING_JPA_HIBERNATE_DDL_AUTO=create
###ENV docker=true
#
## Expose the port on which your Spring Boot application listens
#EXPOSE 8888
## Set the command to run the application
#CMD ["java", "-jar", "app.jar"]
