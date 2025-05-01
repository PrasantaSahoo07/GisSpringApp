# Stage 1: Build
FROM maven:3.8.5-openjdk-17 AS build

WORKDIR /app

# Copy only pom.xml first to cache dependencies
COPY pom.xml .

# Pre-download dependencies
RUN mvn dependency:go-offline -B

# Copy the rest of the project
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Stage 2: Run
FROM openjdk:17.0.1-jdk-slim

WORKDIR /app

# Copy the built jar
COPY --from=build /app/target/GisLocationApp-0.0.1-SNAPSHOT.jar GisLocationApp.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "GisLocationApp.jar"]
