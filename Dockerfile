FROM openjdk:11

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar"]
CMD ["key1", "key2", "key3","/app.jar"]