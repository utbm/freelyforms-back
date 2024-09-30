#Official OpenJDK runtime as a parent image
FROM openjdk:21-jdk-slim
WORKDIR /app

# Copy the project's build files (for Maven)
COPY ./pom.xml ./
COPY ./src ./src

# Install the Maven wrapper
COPY .mvn/ .mvn
COPY mvnw ./
RUN chmod +x mvnw

# Package the application (this will also run tests)
RUN ./mvnw package -DskipTests

# Run the app
CMD ["./mvnw", "spring-boot:run"]
