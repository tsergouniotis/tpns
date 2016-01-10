# GENERIC
docker rm $(docker ps -a -q)
docker rmi `docker images --filter 'dangling=true' -q --no-trunc`

# DATABASE
docker run -d --name=tpns_db  -e POSTGRES_PASSWORD=123456 -e TPNS_PASS=tpns -p 5432:5432 pzografos/tpns-database
docker build -t pzografos/tpns-database .

# APPLICATION SERVER
docker run -it --name=tpns_appserver --link tpns_db:postgres -p 8080:8080 pzografos/tpns-appserver
docker build -t pzografos/tpns-appserver .

