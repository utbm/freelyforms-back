#Official OpenJDK runtime as a parent image
FROM openjdk:21-jdk-slim

ENV HOME=/app
RUN mkdir -p $HOME
WORKDIR $HOME

# Copy the project's build files (for Maven)
COPY ./pom.xml ./
COPY ./src ./src

# Install the Maven wrapper
COPY .mvn/ .mvn
COPY mvnw ./
RUN chmod +x mvnw

# Package the application
RUN ./mvnw package

# Run the app
CMD ["./mvnw", "spring-boot:run"]
