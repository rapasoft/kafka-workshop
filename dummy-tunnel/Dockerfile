FROM openjdk:17.0.1-oraclelinux7
LABEL maintainer="pavol.rajzak@itera.no"
ARG JAR_FILE=target/dummy-tunnel-*.jar
COPY ${JAR_FILE} dummy-tunnel.jar
ENTRYPOINT ["java", "-XX:+UseZGC" ,"-jar","/dummy-tunnel.jar","--spring.config.location=application.properties", "-Dfile.encoding=UTF-8"]