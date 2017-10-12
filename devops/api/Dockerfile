FROM alpine:3.6
RUN \
  apk --no-cache add git go libc-dev && \
  go get gitlab.zotona.com/devops/api && \
  go build -o /jsonget gitlab.zotona.com/devops/api && \
  rm -rf go && \
  apk --no-cache del git go libc-dev
CMD /jsonget -gitlab $JSONGET_GITLAB -token $JSONGET_TOKEN $JSONGET_OPTS
