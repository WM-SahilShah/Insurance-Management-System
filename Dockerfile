# ---- Build stage ----
FROM maven:3.9-eclipse-temurin-17 AS builder
WORKDIR /app

# Pre-fetch dependencies for faster incremental builds
COPY pom.xml .
# Pre-fetch dependencies (removed BuildKit cache mount for Railway compatibility)
RUN mvn -B -q -DskipTests dependency:go-offline

# Copy sources and build
COPY src ./src
# Build application (removed BuildKit cache mount for Railway compatibility)
RUN mvn -B -q -DskipTests package

# ---- Runtime stage ----
FROM eclipse-temurin:17-jre
WORKDIR /app

# Create non-root user
RUN useradd -u 10001 spring && mkdir -p /app && chown -R spring:spring /app
USER spring

# Default port
EXPOSE 8080

# Sensible defaults for DB env vars (override in compose/env)
ENV JAVA_OPTS=""
ENV DB_HOST=db
ENV DB_PORT=3306
ENV DB_NAME=insurance
ENV DB_USERNAME=root
ENV DB_PASSWORD=changeme

# Copy fat jar from builder (supports version changes)
COPY --from=builder /app/target/*.jar app.jar

# Run
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar app.jar"]
