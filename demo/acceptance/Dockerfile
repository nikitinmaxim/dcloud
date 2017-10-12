FROM maven:3.3.9-jdk-8
WORKDIR /test
COPY src src
COPY pom.xml pom.xml
VOLUME /test/target
ENV MAVEN_OPTS -Dmaven.repo.local=/.m2/repository -Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=WARN -Dorg.slf4j.simpleLogger.showDateTime=true -Djava.awt.headless=true
CMD mvn --batch-mode --errors --fail-at-end --show-version -DinstallAtEnd=true -DdeployAtEnd=true verify
