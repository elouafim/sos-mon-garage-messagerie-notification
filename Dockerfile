# Étape 1 : Build avec Maven
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app
COPY . /app
RUN mvn clean install -DskipTests

# Étape 2 : Exécution avec OpenJDK
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]