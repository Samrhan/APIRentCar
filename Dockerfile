FROM maven:latest AS maven

WORKDIR /usr/src/app
COPY . /usr/src/app
# Compile and package the application to an executable JAR
RUN mvn package

FROM openjdk:18

EXPOSE 8080

WORKDIR /opt/app
COPY --from=maven /usr/src/app/application/target/application-0.0.1-SNAPSHOT.jar /opt/app/

ENTRYPOINT ["java","-jar", "application-0.0.1-SNAPSHOT.jar"]