\set dbname `echo "$TPNS_DBNAME"`
\set uname `echo "$TPNS_USER"`
\set passw `echo "$TPNS_PASS"`

CREATE ROLE :uname WITH LOGIN ENCRYPTED PASSWORD :'passw' CREATEDB;
CREATE DATABASE :dbname WITH OWNER :uname TEMPLATE template0 ENCODING 'UTF8';
GRANT ALL PRIVILEGES ON DATABASE :dbname TO :uname;
