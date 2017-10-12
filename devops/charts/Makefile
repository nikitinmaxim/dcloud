.PHONY: repo-dir all clean build

all: build

clean:
	find . -name "*.tgz" -exec rm '{}' + && rm -rf repo/*

build:
	mkdir -p repo
	sh -c '\
	  helm serve repo & \
	  HELMPID=$$!; \
	  trap "kill $$HELMPID; echo done" EXIT; \
	  until curl -s http://localhost:8879/charts; do sleep 1; done &>/dev/null; \
	  helm repo add commonRepo http://localhost:8879; \
	  $(MAKE) repo \
	'

repo: package-templates package-common package-bootstrap package-cicd package-platform package-demo package-meta
	find src -name '*.tgz' -delete

package-%:
	find src/$* -maxdepth 1 -mindepth 1 -type d -exec /bin/sh -c \
	  'helm dep up {} && helm package {} -d repo' \;
