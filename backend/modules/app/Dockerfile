FROM maven:3-eclipse-temurin-21 AS build
WORKDIR /app
RUN java -version && javac -version
COPY pom.xml ./
COPY modules ./modules
RUN mvn -q -DskipTests -pl modules/app -am package

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/modules/app/target/app-0.1.0-SNAPSHOT.jar app.jar
EXPOSE 8181
ENTRYPOINT ["java", "-jar", "/app/app.jar"]