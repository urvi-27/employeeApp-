# Stage 1: build the JAR with Maven
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app

# Copy only what we need first for better cache
COPY pom.xml .
COPY src ./src

# Build the project (skip tests for faster builds; remove -DskipTests to run tests)
RUN mvn -DskipTests clean package

# Stage 2: runtime image (smaller)
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copy the built jar from the builder stage (wildcard handles changing artifactId/version)
COPY --from=builder /app/target/*.jar app.jar

# Expose port (informational)
EXPOSE 8080

# Use Render's $PORT environment variable (falls back to 8080 if not set)
ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT:-8080} -jar /app/app.jar"]