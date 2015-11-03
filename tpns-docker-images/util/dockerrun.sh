# DATABASE
docker run -it --name=tpns_db  -e POSTGRES_PASSWORD=123456 -e TPNS_PASS=123456 pzografos/tpns-database

# APPLICATION SERVER
docker run --name=tpns_be_server -d pzografos/tpns-applicationserver