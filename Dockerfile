FROM openjdk:11
ADD build/libs/vote-0.0.1-SNAPSHOT.jar /opt
WORKDIR /opt
EXPOSE 8080
ENTRYPOINT ["java","-jar","/vote-0.0.1-SNAPSHOT.jar"]