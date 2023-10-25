FROM openjdk:17-jdk-alpine as builder

WORKDIR /app/msvc-cuenta-movimientos

COPY ./pom.xml /app
COPY ./msvc-cuenta-movimientos/.mvn ./.mvn
COPY ./msvc-cuenta-movimientos/mvnw .
COPY ./msvc-cuenta-movimientos/pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

COPY ./msvc-cuenta-movimientos/src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk-alpine

WORKDIR /app
RUN mkdir ./logs
COPY --from=builder /app/msvc-cuenta-movimientos/target/msvc-cuenta-movimientos-0.0.1-SNAPSHOT.jar .
ENV PORT 8002
EXPOSE $PORT

CMD ["java", "-jar", "msvc-cuenta-movimientos-0.0.1-SNAPSHOT.jar"]