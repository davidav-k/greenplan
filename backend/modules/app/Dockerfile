FROM maven:3-eclipse-temurin-21 AS build
WORKDIR /app
RUN java -version && javac -version
COPY pom.xml ./
COPY frontend ./frontend
COPY backend/modules ./backend/modules
RUN mvn -q -DskipTests -pl backend/modules/app -am package

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/backend/modules/app/target/app-0.1.0-SNAPSHOT.jar app.jar
EXPOSE 8181
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
