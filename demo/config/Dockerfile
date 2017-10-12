FROM openjdk:8-jre-alpine
RUN apk add --no-cache curl
ADD target/config-*.jar /app.jar
CMD java $JAVA_MEM $JAVA_EXT -jar /app.jar
