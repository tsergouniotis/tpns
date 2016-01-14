FROM jenkins

COPY plugins.txt /usr/share/jenkins/plugins.txt
RUN /usr/local/bin/plugins.sh /usr/share/jenkins/plugins.txt

COPY jobs/ /tpns/jobs/
COPY jenkins-cli.jar /tpns/jenkins-cli.jar
COPY updateJobs.sh /tpns/updateJobs.sh

WORKDIR /tpns

ENTRYPOINT ["/bin/tini", "--", "/tpns/updateJobs.sh"]





