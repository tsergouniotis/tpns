# GENERIC
docker rm $(docker ps -a -q)
docker rmi `docker images --filter 'dangling=true' -q --no-trunc`

# DATABASE
docker run -it --name=tpnsdatabase  -e POSTGRES_PASSWORD=123456 -e TPNS_PASS=tpns -p 5432:5432 pzografos/tpns-database
docker build -t pzografos/tpns-database .

# APPLICATION SERVER
docker run -it --name=tpnsappserver --link tpnsdatabase:postgres -p 8080:8080 pzografos/tpns-appserver
docker build -t pzografos/tpns-appserver .

# CI Server
docker run -it --name=tpnsciserver -p 127.0.0.1:8090:8080 -p 50000:50000 -v /var/run/docker.sock:/var/run/docker.sock -v $(which docker):$(which docker) -v /usr/lib/x86_64-linux-gnu/libapparmor.so.1.1.0:/lib/x86_64-linux-gnu/libapparmor.so.1 pzografos/tpns-ciserver 
docker build -t pzografos/tpns-ciserver .
