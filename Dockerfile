FROM openjdk:17
ARG JAR_FILE=./build/libs/*.jar
COPY ./src/main/resources/config-file/Wallet_sixteens /var/Wallet_sixteens
COPY ${JAR_FILE} /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]