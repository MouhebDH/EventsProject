# Use OpenJDK image as base
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the built JAR file from the target directory into the container
COPY target/eventsProject-1.0.0-SNAPSHOT.jar app.jar

# Expose port 8080
EXPOSE 8080

# Command to run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]

