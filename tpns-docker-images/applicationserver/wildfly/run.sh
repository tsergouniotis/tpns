#!/bin/bash

set -e

JBOSS_HOME=/opt/jboss/wildfly
JBOSS_CLI=$JBOSS_HOME/bin/jboss-cli.sh
JBOSS_MODE=${1:-"standalone"}
JBOSS_CONFIG=${2:-"$JBOSS_MODE.xml"}

function wait_for_server() {
  #until `$JBOSS_CLI -c "ls /deployment" &> /dev/null`; do
  #  sleep 1
  #done
  sleep 5
}

echo "=> Starting WildFly server"
$JBOSS_HOME/bin/standalone.sh -c standalone-full.xml &

echo "=> Waiting for the server to boot"
wait_for_server

echo "=> Executing the commands"
$JBOSS_HOME/bin/jboss-cli.sh --file=/tpns.cli

/bin/bash

#echo "=> Shutting down WildFly"
#if [ "$JBOSS_MODE" = "standalone" ]; then
#  $JBOSS_CLI -c ":shutdown"
#else
#  $JBOSS_CLI -c "/host=*:shutdown"
#fi