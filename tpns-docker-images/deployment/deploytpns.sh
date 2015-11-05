#!/bin/bash

echo "Pulling tpns images ... "
docker pull pzografos\/tpns-database
docker pull pzografos\/tpns-appserver

echo "Done!"

exec "$0";
