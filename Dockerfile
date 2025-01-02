FROM maven:3-eclipse-temurin-23 AS build
#FROM maven:3-eclipse-temurin-17 AS build

# Workaround für das Problem, dass die JVM auf ARM64-Systemen nicht startet
ENV JAVA_TOOL_OPTIONS="-XX:UseSVE=0"

WORKDIR /app

# Kopiere die pom.xml und lade die Abhängigkeiten herunter
COPY pom.xml .
RUN mvn dependency:go-offline

# Kopiere den Rest des Projekts und baue es
COPY src ./src
RUN mvn clean package
#RUN mvn clean install

# Verwende ein OpenJDK-Basis-Image zum Ausführen des Services
FROM eclipse-temurin:23-alpine
#FROM eclipse-temurin:17-jre


# Setze das Arbeitsverzeichnis
WORKDIR /app

# Kopiere das gebaute JAR-File vom vorherigen Build-Image
COPY --from=build /app/target/*.jar notification_service.jar

# Exponiere den Port, auf dem der Service läuft
EXPOSE 8080
EXPOSE 9090

# Setze den Befehl zum Starten des Notification-Service
CMD ["java", "-jar", "notification_service.jar"]
