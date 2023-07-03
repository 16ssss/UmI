FROM openjdk:17
ARG JAR_FILE=./build/libs/*.jar
COPY ./src/main/resources/config-file/Wallet_sixteens /var/Wallet_sixteens
COPY ./src/main/resources/MBTI_relations.json /var/resources/MBTI_relations.json
COPY ${JAR_FILE} /app.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=oracle","/app.jar"]