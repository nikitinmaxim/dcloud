eval $(minikube docker-env)

/opt/apache-maven-3.5.0/bin/mvn clean package

docker build --no-cache -t registry.cicd.zotona.com:80/dcloud/platform/config:latest .

docker images | grep config

eval $(minikube docker-env -u)
