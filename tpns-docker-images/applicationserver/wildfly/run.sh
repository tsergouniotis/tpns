#!/bin/bash
set -e

/opt/jboss/wildfly/bin/standalone.sh -b '0.0.0.0'
exec "$@"
