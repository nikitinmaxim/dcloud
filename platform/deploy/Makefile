.PHONY: deploy undeploy

all: deploy

init:
	mkdir -p ~/.kube
	echo "$(KUBE_CONFIG)" | base64 -d > ~/.kube/config

deploy: init
	echo "$(VALUES)" | base64 -d > values.yaml
	git clone git@gitlab.zotona.com:devops/charts.git
	helm repo remove local
	helm repo remove stable
	cd charts && $(MAKE)
	helm install --namespace ci-$(TAG) --name ci-$(TAG) charts/repo/$(CI_PROJECT_NAMESPACE)-0.1.1.tgz --wait -f values.yaml --set global.$(CI_PROJECT_NAMESPACE).repository=$(IMAGES),global.$(CI_PROJECT_NAMESPACE).imageTag=$(TAG),global.repository=$(CI_REGISTRY)/devops/images,global.imageTag=master
	timeout -t 3600 sh -c '\
	  while :; do \
	    v=$$(kubectl get pods -n ci-$(TAG) -o go-template="{{range .items}}{{range .status.containerStatuses}}{{if .ready}}+{{else}}-{{end}}{{end}}{{end}}"); \
	    total=$$(echo $$v | wc -c); \
	    ready=$$(echo $$v | sed "s/-//g" | wc -c); \
	    echo "Ready: $$ready/$$total"; \
	    [ $$total -eq $$ready ] && break; \
	    sleep 5; \
	  done \
	'

undeploy: init
	helm delete --purge ci-$(TAG)
