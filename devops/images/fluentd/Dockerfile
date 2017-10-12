FROM fluent/fluentd:debian
MAINTAINER your_name <...>
USER root
WORKDIR /home/fluent
ENV PATH /home/fluent/.gem/ruby/2.2.0/bin:$PATH
RUN test -n "$HTTP_PROXY" && echo "'Acquire::http::Proxy' \"$HTTP_PROXY\";" > /etc/apt/apt.conf || true
RUN apt-get update && apt-get install -y --allow-unauthenticated build-essential ruby-dev
RUN gem install fluent-plugin-secure-forward fluent-plugin-elasticsearch fluent-plugin-burrow fluent-plugin-grep fluent-plugin-systemd fluent-plugin-rewrite-tag-filter fluent-plugin-kubernetes_metadata_filter fluent-plugin-kubernetes fluent-plugin-parser
EXPOSE 24284
ADD fluent.conf /fluentd/etc/$FLUENTD_CONF
CMD fluentd -c /fluentd/etc/$FLUENTD_CONF -p /fluentd/plugins $FLUENTD_OPT

