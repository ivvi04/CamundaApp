FROM maven:3-eclipse-temurin-25 as builder
WORKDIR .
COPY . .
RUN mvn clean package -DskipTests -q

FROM eclipse-temurin:25
COPY --from=builder /target/camunda-app-0.0.1-SNAPSHOT.jar camunda-app.jar
CMD ["java","-jar","camunda-app.jar"]