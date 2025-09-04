# Multi-stage Dockerfile to build and run the Bond Cinema API

# ---- Build stage ----
FROM eclipse-temurin:17-jdk AS builder

WORKDIR /workspace

# Cache dependencies by copying only metadata first
COPY settings.gradle build.gradle ./
COPY gradlew ./gradlew
COPY gradle ./gradle

RUN chmod +x gradlew

# Copy sources
COPY src ./src

# Build a self-contained distribution (includes app + libs + start scripts)
RUN ./gradlew --no-daemon clean installDist

# ---- Runtime stage ----
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copy the built distribution from the builder image
COPY --from=builder /workspace/build/install/booking-api /app

EXPOSE 8080

# Start the application using the generated script
CMD ["/app/bin/booking-api"]
