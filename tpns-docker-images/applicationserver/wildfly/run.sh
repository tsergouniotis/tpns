#!/bin/bash
      
JBOSS_HOME=/opt/jboss/wildfly
JBOSS_CLI=$JBOSS_HOME/bin/jboss-cli.sh
  
function wait_for_server() {
 # until `$JBOSS_CLI -c "ls /deployment" &> /dev/null`; do
 #   sleep 1
 # done
 sleep 20
}
  
echo "=> Starting WildFly server"
$JBOSS_HOME/bin/standalone.sh -c standalone.xml &> /dev/null &
  
echo "=> Waiting for the server to boot and database to be setup"
wait_for_server
  
echo "=> Executing the commands"
$JBOSS_CLI -c --file=/tpns/tpns.cli

echo "=> Restarting wildfly"
$JBOSS_HOME/bin/standalone.sh -c standalone.xml -b 0.0.0.0

