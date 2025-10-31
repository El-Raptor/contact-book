# =================
# Stage 1: build
# =================
FROM maven:3.9.11-eclipse-temurin-25 AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package

# =================
# Stage 2: run app
# =================
FROM openjdk:25-ea-1-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/contact-list.jar contact-list.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "contact-list.jar"]