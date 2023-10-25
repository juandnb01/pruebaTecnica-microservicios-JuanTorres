FROM openjdk:17-jdk-alpine as builder

WORKDIR /app/msvc-cliente-persona

COPY ./pom.xml /app
COPY ./msvc-cliente-persona/.mvn ./.mvn
COPY ./msvc-cliente-persona/mvnw .
COPY ./msvc-cliente-persona/pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

COPY ./msvc-cliente-persona/src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk-alpine

WORKDIR /app
RUN mkdir ./logs
COPY --from=builder /app/msvc-cliente-persona/target/msvc-cliente-persona-0.0.1-SNAPSHOT.jar .
ENV PORT 8001
EXPOSE $PORT

CMD ["java", "-jar", "msvc-cliente-persona-0.0.1-SNAPSHOT.jar"]