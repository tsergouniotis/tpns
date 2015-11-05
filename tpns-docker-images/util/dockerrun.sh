# DATABASE
docker run -d --name=tpns_db  -e POSTGRES_PASSWORD=123456 -e TPNS_PASS=123456 -p 5432:5432 pzografos/tpns-database

# APPLICATION SERVER
docker run -it --name=tpns_appserver --link tpns_db:postgres pzografos/tpns-applicationserver