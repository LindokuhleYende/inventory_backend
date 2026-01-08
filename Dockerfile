# 1) Build stage
FROM maven:3.9.9-eclipse-temurin-24 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -q -B -e -DskipTests dependency:go-offline
COPY src ./src
RUN mvn -q -B -DskipTests package

# 2) Runtime stage
FROM eclipse-temurin:24-jre
WORKDIR /app
# Create non-root user
RUN useradd -r -u 1001 appuser
# Copy built jar
COPY --from=build /app/target/*.jar /app/app.jar

# JVM tuning for containers
ENV JAVA_TOOL_OPTIONS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75 -XX:InitialRAMPercentage=50 -XX:+ExitOnOutOfMemoryError"

# Spring Boot config via env (overrides application.yaml when provided)
ENV SPRING_DATASOURCE_URL=""
ENV SPRING_DATASOURCE_USERNAME=""
ENV SPRING_DATASOURCE_PASSWORD=""
ENV SERVER_PORT=8080

EXPOSE 8080
USER appuser

ENTRYPOINT ["sh","-c","java $JAVA_TOOL_OPTIONS -jar /app/app.jar"]