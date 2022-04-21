FROM openjdk:17
EXPOSE 8080
CMD java $JAVA_OPTS -jar /service.jar
COPY "build/libs/*.jar" /service.jar

