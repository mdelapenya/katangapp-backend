FROM mdelapenya/jdk:8-openjdk
MAINTAINER Manuel de la Peña <manuel.delapenya@liferay.com>

RUN apt-get update -y

ENV PATH $PATH:/app

EXPOSE 9000 8888

RUN mkdir /app
WORKDIR /app
ADD . /app
RUN activator clean compile

ENTRYPOINT ["activator", "run"]