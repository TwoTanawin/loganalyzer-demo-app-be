# ---- Build Stage ----
FROM maven:3.9.6-eclipse-temurin-21 as builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# ---- Run Stage ----
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copy jar from builder stage
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]