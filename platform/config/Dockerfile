FROM registry.zotona.com/devops/images/basejava:master
ADD target/config-*.jar /app.jar
CMD java $JAVA_MEM $JAVA_EXT -jar /app.jar
