# Build stage
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copy only the project folder with pom.xml and src
COPY vybes /app

# Build the app
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:17-jdk
COPY --from=build /app/target/vybes-api-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]