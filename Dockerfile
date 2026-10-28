FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml .
# Кэшируем зависимости
RUN mvn -B -q dependency:go-offline
COPY . .
RUN mvn -B -q clean package -DskipTests

FROM eclipse-temurin:21-jre AS runtime
WORKDIR /app
ENV JAVA_TOOL_OPTIONS="-Xms256m -Xmx512m"
COPY --from=builder /app/target/camunda-app-0.0.1-SNAPSHOT.jar camunda-app.jar
CMD ["java","-jar","camunda-app.jar"]
