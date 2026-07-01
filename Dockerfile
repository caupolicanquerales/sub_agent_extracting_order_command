# ── Build stage ────────────────────────────────────────────────
FROM maven:3.9-eclipse-temurin-21-alpine AS build

WORKDIR /app

# Copy pom first for layer-cache efficiency
COPY pom.xml ./

# Download dependencies (cached unless pom.xml changes)
RUN mvn dependency:go-offline -q

# Copy source and build
COPY src ./src
RUN mvn package -DskipTests -q

# ── Runtime stage ───────────────────────────────────────────────
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=build /app/target/sub_agent_extracting_order_command-*.jar app.jar

EXPOSE 8093

ENTRYPOINT ["java", "-jar", "app.jar"]
