FROM maven:3.8.3-jdk-11 AS builder
VOLUME /tmp
COPY . .
RUN mvn clean install compile package -DskipTests

FROM openjdk:11
VOLUME /tmp
EXPOSE 8001
COPY --from=builder ./target/controle-tarefa-0.0.1-SNAPSHOT.jar /
ENTRYPOINT ["java","-jar","/controle-tarefa-0.0.1-SNAPSHOT.jar"]