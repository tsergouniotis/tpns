FROM jboss/wildfly

COPY lib/postgresql-9.4-1202-jdbc41.jar /
COPY ear/tpns-article.ear /
COPY ear/tpns-user.ear /
COPY cli-scripts/tpns.cli /tpns.cli
COPY run.sh /

CMD ["/bin/sh","/run.sh"]
