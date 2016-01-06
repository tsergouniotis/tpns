FROM jboss/wildfly

COPY lib/postgresql-9.4-1202-jdbc41.jar /tpns/
COPY ear/tpns-article.ear /tpns/
COPY ear/tpns-user.ear /tpns/
COPY cli-scripts/tpns.cli /tpns/tpns.cli
COPY run.sh /

CMD ["/bin/sh","/run.sh"]