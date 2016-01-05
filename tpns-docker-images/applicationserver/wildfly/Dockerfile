# Use latest jboss/base-jdk:7 image as the base
FROM jboss/base-jdk:8
MAINTAINER Panos Zografos
EXPOSE 8080

# Set the WILDFLY_VERSION env variable
ENV WILDFLY_VERSION 9.0.2.Final
ENV WILDFLY_SHA1 75738379f726c865d41e544e9b61f7b27d2853c7
ENV JBOSS_HOME /opt/jboss/wildfly

# Add the WildFly distribution to /opt, and make wildfly the owner of the extracted tar content
# Make sure the distribution is available from a well-known place
RUN cd $HOME \
    && curl -O https://download.jboss.org/wildfly/$WILDFLY_VERSION/wildfly-$WILDFLY_VERSION.tar.gz \
    && sha1sum wildfly-$WILDFLY_VERSION.tar.gz | grep $WILDFLY_SHA1 \
    && tar xf wildfly-$WILDFLY_VERSION.tar.gz \
    && mv $HOME/wildfly-$WILDFLY_VERSION $JBOSS_HOME \
    && rm wildfly-$WILDFLY_VERSION.tar.gz

# Ensure signals are forwarded to the JVM process correctly for graceful shutdown
ENV LAUNCH_JBOSS_IN_BACKGROUND true

#RUN mkdir -p /opt/jboss/wildfly/modules/org/postgresql/main

COPY lib/postgresql-9.4-1202-jdbc41.jar /
COPY ear/tpns.ear /

COPY cli-scripts/tpns.cli /tpns.cli

COPY run.sh /

# Set the default command to run on boot
# This will boot WildFly in the standalone mode and bind to all interface
CMD ["/bin/sh","/run.sh"]
