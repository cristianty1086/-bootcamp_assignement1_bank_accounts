FROM openjdk:11
VOLUME /tmp
EXPOSE 443
ADD ./target/bank_accounts-0.0.1-SNAPSHOT.jar ms-bank_accounts.jar
ENTRYPOINT ["java", "-jar", "ms-bank_accounts.jar"]