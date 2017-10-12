FROM registry.zotona.com/devops/images/baseimage:master
ADD https://storage.googleapis.com/kubernetes-release/release/v1.7.1/bin/linux/amd64/kubectl /usr/bin/kubectl
RUN \
  apk --no-cache add git docker openssh make openjdk8 maven; \
  curl -L https://storage.googleapis.com/kubernetes-helm/helm-v2.6.2-linux-amd64.tar.gz | tar -xzf- linux-amd64/helm --strip-components=1 -C /usr/bin && \
  chmod +x /usr/bin/kubectl && \
  chmod +x /usr/bin/helm && \
  helm init --client-only
