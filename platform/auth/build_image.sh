eval $(minikube docker-env)

docker build --no-cache -t registry.gitlab.bcs.ru:80/dcloud/platform/auth:latest .

docker images | grep auth

eval $(minikube docker-env -u)
