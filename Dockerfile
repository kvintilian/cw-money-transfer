FROM openjdk:11
ADD target/moneytransfer-0.0.1-SNAPSHOT.jar moneytransfer.jar
EXPOSE 5500
ENTRYPOINT ["java","-jar","/moneytransfer.jar"]