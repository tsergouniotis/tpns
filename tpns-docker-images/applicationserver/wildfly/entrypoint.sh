#!/bin/sh
echo "Trying to connect postgres"
while ! curl http://$POSTGRES_PORT_5432_TCP_ADDR:$POSTGRES_PORT_5432_TCP_PORT/
do
  echo "$(date) - still trying"
  sleep 1
done
echo "$(date) - connected successfully"
exec "/opt/jboss/wildfly/bin/standalone.sh -b 0.0.0.0"