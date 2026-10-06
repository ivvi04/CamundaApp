FROM maven:3-eclipse-temurin-25 as builder
WORKDIR /app
COPY pom.xml .
# Кэшируем зависимости
RUN mvn dependency:resolve
COPY . .
RUN mvn clean package -DskipTests -q

FROM eclipse-temurin:25-jre as runtime
WORKDIR /app
ENV JAVA_OPTS="-Xms256m -Xmx512m"
COPY --from=builder /app/target/camunda-app-0.0.1-SNAPSHOT.jar camunda-app.jar
CMD ["java","-jar","camunda-app.jar"]