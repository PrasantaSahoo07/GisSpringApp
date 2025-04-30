FROM maven:3.8.5-openjdk-17 AS build

# Set working directory inside container
WORKDIR /app

# Copy pom.xml separately to leverage Docker layer caching
COPY pom.xml .

# Download dependencies (optional but efficient)
RUN mvn dependency:go-offline -B

# Copy the rest of the source code
COPY src ./src

# Package the application
RUN mvn clean package -DskipTests

# Second stage: runtime
FROM openjdk:17.0.1-jdk-slim

# Copy built jar from the previous stage
COPY --from=build /app/target/GisLocationApp-0.0.1-SNAPSHOT.jar GisLocationApp.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "GisLocationApp.jar"]

