FROM openjdk:17-jdk-slim

RUN apt-get update && \
    apt-get install -y maven && \
    mvn -version  # check install

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

EXPOSE 8080

ARG JAR_FILE=target/keycloak-auth-service-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]