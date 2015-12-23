-- *********************************************************************
-- Update Database Script
-- *********************************************************************
-- Change Log: src/main/db/db.changelog-master.xml
-- Ran at: 12/23/15 11:48 AM
-- Against: tpns@jdbc:postgresql://localhost:5432/tpns
-- Liquibase version: 3.3.5
-- *********************************************************************

-- Create Database Lock Table
CREATE TABLE public.databasechangeloglock (ID INT NOT NULL, LOCKED BOOLEAN NOT NULL, LOCKGRANTED TIMESTAMP WITHOUT TIME ZONE, LOCKEDBY VARCHAR(255), CONSTRAINT PK_DATABASECHANGELOGLOCK PRIMARY KEY (ID));

-- Initialize Database Lock Table
DELETE FROM public.databasechangeloglock;

INSERT INTO public.databasechangeloglock (ID, LOCKED) VALUES (1, FALSE);

-- Lock Database
UPDATE public.databasechangeloglock SET LOCKED = TRUE, LOCKEDBY = 'fe80:0:0:0:c82f:53ff:fefc:af62%veth8e32311 (fe80:0:0:0:c82f:53ff:fefc:af62%veth8e32311)', LOCKGRANTED = '2015-12-23 11:48:21.989' WHERE ID = 1 AND LOCKED = FALSE;

-- Create Database Change Log Table
CREATE TABLE public.databasechangelog (ID VARCHAR(255) NOT NULL, AUTHOR VARCHAR(255) NOT NULL, FILENAME VARCHAR(255) NOT NULL, DATEEXECUTED TIMESTAMP WITHOUT TIME ZONE NOT NULL, ORDEREXECUTED INT NOT NULL, EXECTYPE VARCHAR(10) NOT NULL, MD5SUM VARCHAR(35), DESCRIPTION VARCHAR(255), COMMENTS VARCHAR(255), TAG VARCHAR(255), LIQUIBASE VARCHAR(20));

-- Changeset src/main/db/1.0.0/ddl/ddl.xml::create-roles-table::thanasis.sergouniotis
CREATE TABLE public.roles (role_id SMALLINT NOT NULL, role_name VARCHAR(255));

ALTER TABLE public.roles ADD CONSTRAINT users_pk PRIMARY KEY (role_id);

INSERT INTO public.databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('create-roles-table', 'thanasis.sergouniotis', 'src/main/db/1.0.0/ddl/ddl.xml', NOW(), 1, '7:b43d1320f1002e6cb5a65af34c477fd3', 'createTable, addPrimaryKey', '', 'EXECUTED', '3.3.5');

-- Changeset src/main/db/1.0.0/ddl/ddl.xml::create-users-table::thanasis.sergouniotis
CREATE TABLE public.users (user_id BIGINT NOT NULL, username VARCHAR(255), firstname VARCHAR(255), surname VARCHAR(255), password VARCHAR(255), phone VARCHAR(255), fax VARCHAR(255), email VARCHAR(255), street VARCHAR(255), number VARCHAR(255), city VARCHAR(255), created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(), updated_at TIMESTAMP WITHOUT TIME ZONE, UNIQUE (username));

ALTER TABLE public.users ADD PRIMARY KEY (user_id);

CREATE SEQUENCE public.userseq START WITH 1 INCREMENT BY 1;

CREATE INDEX username_idx ON public.users(username);

INSERT INTO public.databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('create-users-table', 'thanasis.sergouniotis', 'src/main/db/1.0.0/ddl/ddl.xml', NOW(), 2, '7:2841630bb64525d06fb6c07c737be607', 'createTable, addPrimaryKey, createSequence, createIndex', '', 'EXECUTED', '3.3.5');

-- Changeset src/main/db/1.0.0/ddl/ddl.xml::create-users-roles-table::thanasis.sergouniotis
CREATE TABLE public.users_roles (user_id BIGINT NOT NULL, role_id BIGINT NOT NULL);

ALTER TABLE public.users_roles ADD CONSTRAINT user_roles_userfk FOREIGN KEY (user_id) REFERENCES public.users (user_id);

ALTER TABLE public.users_roles ADD CONSTRAINT user_roles_rolefk FOREIGN KEY (role_id) REFERENCES public.roles (role_id);

INSERT INTO public.databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('create-users-roles-table', 'thanasis.sergouniotis', 'src/main/db/1.0.0/ddl/ddl.xml', NOW(), 3, '7:5082264756fbe7671ac5a1126fd9a813', 'createTable, addForeignKeyConstraint (x2)', '', 'EXECUTED', '3.3.5');

-- Changeset src/main/db/1.0.0/ddl/ddl.xml::create-categories-table::thanasis.sergouniotis
CREATE TABLE public.categories (category_id SMALLINT NOT NULL, category_name VARCHAR(255));

ALTER TABLE public.categories ADD CONSTRAINT categories_pk PRIMARY KEY (category_id);

CREATE SEQUENCE public.categoryseq START WITH 1 INCREMENT BY 1;

INSERT INTO public.databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('create-categories-table', 'thanasis.sergouniotis', 'src/main/db/1.0.0/ddl/ddl.xml', NOW(), 4, '7:ffabe6dfc0a04d220624df32f9020f95', 'createTable, addPrimaryKey, createSequence', '', 'EXECUTED', '3.3.5');

-- Changeset src/main/db/1.0.0/ddl/ddl.xml::create-articles-table::thanasis.sergouniotis
CREATE TABLE public.articles (article_id BIGINT NOT NULL, subject VARCHAR(255), content VARCHAR(20480), short_description VARCHAR(1024), category_id SMALLINT, author_id BIGINT, status VARCHAR(50), created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(), updated_at TIMESTAMP WITHOUT TIME ZONE, posted_at TIMESTAMP WITHOUT TIME ZONE, version INT);

ALTER TABLE public.articles ADD CONSTRAINT articles_pk PRIMARY KEY (article_id);

CREATE SEQUENCE public.articleseq START WITH 1 INCREMENT BY 1;

ALTER TABLE public.articles ADD CONSTRAINT article_category_fk FOREIGN KEY (category_id) REFERENCES public.categories (category_id);

INSERT INTO public.databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('create-articles-table', 'thanasis.sergouniotis', 'src/main/db/1.0.0/ddl/ddl.xml', NOW(), 5, '7:8f85830a60f5b56e10f33e4e1174c57b', 'createTable, addPrimaryKey, createSequence, addForeignKeyConstraint', '', 'EXECUTED', '3.3.5');

-- Changeset src/main/db/1.0.0/ddl/ddl.xml::create-media-resources-table::panos.zografos
CREATE TABLE public.articlemediaresources (article_id BIGINT NOT NULL, resource_type VARCHAR(50), url VARCHAR(2048));

ALTER TABLE public.articlemediaresources ADD CONSTRAINT article_resource_fk FOREIGN KEY (article_id) REFERENCES public.articles (article_id);

INSERT INTO public.databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('create-media-resources-table', 'panos.zografos', 'src/main/db/1.0.0/ddl/ddl.xml', NOW(), 6, '7:96ce43a1aafcf705d84b5fdd7ded02d7', 'createTable, addForeignKeyConstraint', '', 'EXECUTED', '3.3.5');

-- Changeset src/main/db/1.0.0/ddl/ddl.xml::create-keyword-tables::thanasis.sergouniotis
CREATE TABLE public.keywords (keyword VARCHAR(1024) NOT NULL, version INT);

ALTER TABLE public.keywords ADD CONSTRAINT keyword_pk PRIMARY KEY (keyword);

CREATE TABLE public.article_keywords (keyword VARCHAR(1024) NOT NULL, article_id BIGINT NOT NULL);

ALTER TABLE public.article_keywords ADD CONSTRAINT article_keyword_keyword_fk FOREIGN KEY (keyword) REFERENCES public.keywords (keyword);

ALTER TABLE public.article_keywords ADD CONSTRAINT article_keyword_article_fk FOREIGN KEY (article_id) REFERENCES public.articles (article_id);

INSERT INTO public.databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('create-keyword-tables', 'thanasis.sergouniotis', 'src/main/db/1.0.0/ddl/ddl.xml', NOW(), 7, '7:d18b02d292144410eec720b263c97e92', 'createTable, addPrimaryKey, createTable, addForeignKeyConstraint (x2)', '', 'EXECUTED', '3.3.5');

-- Changeset src/main/db/1.0.0/ddl/ddl.xml::create-applications-table::thanasis.sergouniotis
CREATE TABLE public.applications (application_id VARCHAR(255) NOT NULL, endpoint VARCHAR(255) NOT NULL, client_id VARCHAR(255) NOT NULL, version INT);

ALTER TABLE public.applications ADD CONSTRAINT application_pk PRIMARY KEY (application_id);

INSERT INTO public.databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('create-applications-table', 'thanasis.sergouniotis', 'src/main/db/1.0.0/ddl/ddl.xml', NOW(), 8, '7:2f130c968a095893940edd6c306d187c', 'createTable, addPrimaryKey', '', 'EXECUTED', '3.3.5');

-- Changeset src/main/db/1.0.0/ddl/ddl.xml::create-application-params-table::thanasis.sergouniotis
CREATE TABLE public.application_parameters (key VARCHAR(255) NOT NULL, value VARCHAR(255) NOT NULL);

ALTER TABLE public.application_parameters ADD CONSTRAINT application_parameters_pk PRIMARY KEY (key);

INSERT INTO public.databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('create-application-params-table', 'thanasis.sergouniotis', 'src/main/db/1.0.0/ddl/ddl.xml', NOW(), 9, '7:3295fb0d14d991fd993adb8a72c8c95c', 'createTable, addPrimaryKey', '', 'EXECUTED', '3.3.5');

-- Changeset src/main/db/1.0.0/dml/dml.xml::insert-roles::thanasis.sergouniotis
INSERT INTO public.roles (role_id, role_name) VALUES ('1', 'ADMIN');

INSERT INTO public.roles (role_id, role_name) VALUES ('2', 'AUTHOR');

INSERT INTO public.roles (role_id, role_name) VALUES ('3', 'CHIEF_EDITOR');

INSERT INTO public.roles (role_id, role_name) VALUES ('4', 'APPLICATION');

INSERT INTO public.databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('insert-roles', 'thanasis.sergouniotis', 'src/main/db/1.0.0/dml/dml.xml', NOW(), 10, '7:83ec779d07abacf8da112065f0b356cf', 'insert (x4)', '', 'EXECUTED', '3.3.5');

-- Changeset src/main/db/1.0.0/dml/dml.xml::insert-users::thanasis.sergouniotis
INSERT INTO public.users (user_id, username, password) VALUES (nextval('userseq'), 'admin', 'admin');

INSERT INTO public.users (user_id, username, password, firstname, surname) VALUES (nextval('userseq'), 'author', 'author', 'Carl', 'Bernstein');

INSERT INTO public.users (user_id, username, password) VALUES (nextval('userseq'), 'chief', 'chief');

INSERT INTO public.users (user_id, username, password) VALUES (nextval('userseq'), 'onsports', 'onsports');

INSERT INTO public.databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('insert-users', 'thanasis.sergouniotis', 'src/main/db/1.0.0/dml/dml.xml', NOW(), 11, '7:ec3f6f118d59ca77b6b80ac02913d1db', 'insert (x4)', '', 'EXECUTED', '3.3.5');

-- Changeset src/main/db/1.0.0/dml/dml.xml::insert-users-roles::thanasis.sergouniotis
INSERT INTO public.users_roles (role_id, user_id) VALUES ((select role_id from roles where role_name='ADMIN'), (select user_id from users where username='admin'));

INSERT INTO public.users_roles (role_id, user_id) VALUES ((select role_id from roles where role_name='AUTHOR'), (select user_id from users where username='author'));

INSERT INTO public.users_roles (role_id, user_id) VALUES ((select role_id from roles where role_name='CHIEF_EDITOR'), (select user_id from users where username='chief'));

INSERT INTO public.users_roles (role_id, user_id) VALUES ((select role_id from roles where role_name='APPLICATION'), (select user_id from users where username='onsports'));

INSERT INTO public.databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('insert-users-roles', 'thanasis.sergouniotis', 'src/main/db/1.0.0/dml/dml.xml', NOW(), 12, '7:9add2967456fcf627ea9ddaddad2ebf5', 'insert (x4)', '', 'EXECUTED', '3.3.5');

-- Changeset src/main/db/1.0.0/dml/dml.xml::insert-categories::thanasis.sergouniotis
INSERT INTO public.categories (category_id, category_name) VALUES (nextval('CATEGORYSEQ'), 'politics');

INSERT INTO public.categories (category_id, category_name) VALUES (nextval('CATEGORYSEQ'), 'economy');

INSERT INTO public.categories (category_id, category_name) VALUES (nextval('CATEGORYSEQ'), 'sports');

INSERT INTO public.categories (category_id, category_name) VALUES (nextval('CATEGORYSEQ'), 'technology');

INSERT INTO public.categories (category_id, category_name) VALUES (nextval('CATEGORYSEQ'), 'social');

INSERT INTO public.databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('insert-categories', 'thanasis.sergouniotis', 'src/main/db/1.0.0/dml/dml.xml', NOW(), 13, '7:04faa6e77909a5aaf773983a306a9c3c', 'insert (x5)', '', 'EXECUTED', '3.3.5');

-- Changeset src/main/db/1.0.0/dml/dml.xml::insert-application-parameters::thanasis.sergouniotis
INSERT INTO public.application_parameters (key, value) VALUES ('article.lucene.directory', '/home/sergouniotis/Downloads/tpns/index');

INSERT INTO public.databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('insert-application-parameters', 'thanasis.sergouniotis', 'src/main/db/1.0.0/dml/dml.xml', NOW(), 14, '7:54ce07f0b0077ae24550d91976b2e5b5', 'insert', '', 'EXECUTED', '3.3.5');

-- Release Database Lock
UPDATE public.databasechangeloglock SET LOCKED = FALSE, LOCKEDBY = NULL, LOCKGRANTED = NULL WHERE ID = 1;

