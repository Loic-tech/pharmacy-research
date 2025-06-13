FROM openjdk:17
EXPOSE 1999
ADD target/pharmadoc.jar pharmadoc.jar
ENTRYPOINT ["java", "-jar", "/pharmadoc.jar"]