# Official OpenJDK runtime as a parent image
FROM openjdk:21-jdk-slim

# Set the home directory
ENV HOME=/app

# Create the home directory and set it as the working directory
RUN mkdir -p $HOME
WORKDIR $HOME

# Install dos2unix to handle line ending conversions
RUN apt-get update && apt-get install -y dos2unix

# Copy the project's build files (for Maven)
COPY ./pom.xml ./
COPY ./src ./src

# Install the Maven wrapper
COPY .mvn/ .mvn
COPY mvnw ./
COPY mvnw.cmd ./

# Ensure that the mvnw script uses LF line endings
RUN dos2unix mvnw mvnw.cmd

# Make the mvnw script executable
RUN chmod +x mvnw

# Package the application
RUN ./mvnw package

# Run the application
CMD ["./mvnw", "spring-boot:run"]
