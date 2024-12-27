FROM openjdk:11-jre-slim
WORKDIR /myapp
COPY target/spring-boot_security-demo-0.0.1-SNAPSHOT.jar /myapp/spring-boot.jar
ENTRYPOINT ["java", "-jar", "spring-boot.jar"]