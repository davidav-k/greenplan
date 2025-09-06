# Simple runtime image
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Install curl for healthcheck
RUN apk add --no-cache curl

# Copy the pre-built JAR from host
COPY frontend/target/frontend-*.jar app.jar

# Expose port
EXPOSE 8081

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD curl -f http://localhost:8081/ || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]


