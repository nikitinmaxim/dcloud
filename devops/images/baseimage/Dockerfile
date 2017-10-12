FROM alpine:3.6
RUN \
  sed -i 's/dl-cdn/dl-4/g' -i /etc/apk/repositories && \
  apk --no-cache add curl ca-certificates
