FROM openjdk:8
VOLUME /tmp
EXPOSE 8022
ADD ./target/spring-boot-webflu-ms-cuenta-credito-0.0.1-SNAPSHOT.jar ms.cuenta.credito.jar
ENTRYPOINT ["java","-jar","/ms.cuenta.credito.jar"]