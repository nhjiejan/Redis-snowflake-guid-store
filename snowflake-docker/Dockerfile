FROM centos:7
MAINTAINER Nabil Hjiej-Andaloussi


ENV VERSION 1.7.4
ENV OS linux
ENV ARCH amd64

RUN yum -y install git tar \
  && yum -y clean all

ADD https://storage.googleapis.com/golang/go${VERSION}.${OS}-${ARCH}.tar.gz /tmp/go${VERSION}.${OS}-${ARCH}.tar.gz

RUN tar -C /usr/local -xzf /tmp/go${VERSION}.${OS}-${ARCH}.tar.gz \
  && rm /tmp/go${VERSION}.${OS}-${ARCH}.tar.gz \
  && mkdir -p /go/src/app

ENV PATH $PATH:/usr/local/go/bin
ENV GOPATH /go


# run goSnowFlake
ADD goSnowFlake /goSnowFlake
RUN go get github.com/zheng-ji/goSnowFlake
RUN go get github.com/gin-gonic/gin

WORKDIR /goSnowFlake/httpServer/
RUN go build main.go

CMD [ "./main" ]