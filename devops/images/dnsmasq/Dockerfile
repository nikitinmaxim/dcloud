FROM alpine:3.4
MAINTAINER Girish Kalele <gkalele@google.com>

RUN sed 's/dl-cdn/dl-4/' -i /etc/apk/repositories && apk --no-cache -vv add dnsmasq && echo user=root > /etc/dnsmasq.conf
# Replace SIGTERM with SIGCONT as stop signal to support graceful termination
STOPSIGNAL SIGCONT
ENTRYPOINT ["/usr/sbin/dnsmasq", "--keep-in-foreground"]
