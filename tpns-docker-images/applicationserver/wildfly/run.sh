#!/bin/bash
set -e

sleep 20

/opt/jboss/wildfly/bin/standalone.sh -b '0.0.0.0'
exec "$@"
