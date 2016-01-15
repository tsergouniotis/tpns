FROM jenkins

USER root

RUN apt-get update \
      && apt-get install -y sudo \
      && rm -rf /var/lib/apt/lists/*
RUN echo "jenkins ALL=NOPASSWD: ALL" >> /etc/sudoers

RUN mkdir -m 755 /software
RUN mkdir -m 755 /app

#Install gradle
RUN curl -L https://services.gradle.org/distributions/gradle-2.10-bin.zip -o /software/gradle-2.10-bin.zip
RUN apt-get install -y unzip
RUN unzip /software/gradle-2.10-bin.zip -d /app

ENV GRADLE_HOME=/app/gradle-2.10
ENV PATH=$PATH:$GRADLE_HOME/bin

USER jenkins

#Install jenkins plugins
COPY plugins.txt /usr/share/jenkins/plugins.txt
RUN /usr/local/bin/plugins.sh /usr/share/jenkins/plugins.txt

#Tpns Specific
COPY jobs/ /tpns/jobs/
COPY jenkins-cli.jar /tpns/jenkins-cli.jar
COPY updateJobs.sh /tpns/updateJobs.sh

WORKDIR /tpns

ENTRYPOINT ["/bin/tini", "--", "/tpns/updateJobs.sh"]





