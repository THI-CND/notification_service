FROM maven:3-eclipse-temurin-23 AS build

WORKDIR /app

COPY pom.xml .

# Workaround für das Problem, dass Java 21+ auf ARM64 mit MacOS Sequoia 15.2 nicht funktioniert
ARG TARGETPLATFORM
RUN if [ "$TARGETPLATFORM" = "linux/arm64" ]; then export JAVA_TOOL_OPTIONS="-XX:UseSVE=0"; fi && \
    mvn dependency:go-offline

COPY src ./src
RUN if [ "$TARGETPLATFORM" = "linux/arm64" ]; then export JAVA_TOOL_OPTIONS="-XX:UseSVE=0"; fi && \
    mvn clean package

FROM eclipse-temurin:23-alpine

ARG configuration=development
ENV SPRING_PROFILES_ACTIVE=$configuration

WORKDIR /app

COPY --from=build /app/target/*.jar notification_service.jar

EXPOSE 8080
EXPOSE 9090

CMD ["java", "-jar", "notification_service.jar"]
