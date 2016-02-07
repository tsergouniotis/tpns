FROM postgres:9.4

# TPNS Data
ENV POSTGRES_DB tpns
ENV POSTGRES_USER tpns
ENV POSTGRES_PASSWORD tpns

COPY dbscripts/tpns-data.sql /docker-entrypoint-initdb.d/

