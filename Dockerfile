FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8080
ADD  target/courier-billing-app-2.0-SNAPSHOT.jar courier-billing-app-2.0-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","courier-billing-app-2.0-SNAPSHOT.jar"]
