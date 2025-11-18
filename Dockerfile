# Build Stage
FROM gradle:8.6-jdk17-alpine AS build
WORKDIR /app

# Projektdateien kopieren
COPY . .

# Jar bauen
RUN ./gradlew bootJar --no-daemon

# Run Stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# gebautes Jar aus dem Build Container holen
COPY --from=build /app/build/libs/*.jar app.jar

# Standard Spring Boot Port
EXPOSE 8080

# App starten
ENTRYPOINT ["java", "-jar", "app.jar"]
