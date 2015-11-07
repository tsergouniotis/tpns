-- *********************************************************************
-- Update Database Script
-- *********************************************************************
-- Change Log: src/main/db/db.changelog-master.xml
-- Ran at: 11/7/15 8:57 PM
-- Against: postgres@jdbc:postgresql://localhost:5432/tpns
-- Liquibase version: 3.3.5
-- *********************************************************************

-- Create Database Lock Table
CREATE TABLE public.databasechangeloglock (ID INT NOT NULL, LOCKED BOOLEAN NOT NULL, LOCKGRANTED TIMESTAMP WITHOUT TIME ZONE, LOCKEDBY VARCHAR(255), CONSTRAINT PK_DATABASECHANGELOGLOCK PRIMARY KEY (ID));

-- Initialize Database Lock Table
DELETE FROM public.databasechangeloglock;

INSERT INTO public.databasechangeloglock (ID, LOCKED) VALUES (1, FALSE);

-- Lock Database
UPDATE public.databasechangeloglock SET LOCKED = TRUE, LOCKEDBY = 'fe80:0:0:0:fc05:39ff:fe4a:d574%vethcde8b73 (fe80:0:0:0:fc05:39ff:fe4a:d574%vethcde8b73)', LOCKGRANTED = '2015-11-07 20:57:59.370' WHERE ID = 1 AND LOCKED = FALSE;

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
CREATE TABLE public.articles (article_id BIGINT NOT NULL, subject VARCHAR(255), content VARCHAR(2096), category_id SMALLINT, image VARCHAR(2096), video VARCHAR(2096), created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(), updated_at TIMESTAMP WITHOUT TIME ZONE);

ALTER TABLE public.articles ADD CONSTRAINT articles_pk PRIMARY KEY (article_id);

CREATE SEQUENCE public.articleseq START WITH 1 INCREMENT BY 1;

ALTER TABLE public.articles ADD CONSTRAINT article_category_fk FOREIGN KEY (category_id) REFERENCES public.categories (category_id);

INSERT INTO public.databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('create-articles-table', 'thanasis.sergouniotis', 'src/main/db/1.0.0/ddl/ddl.xml', NOW(), 5, '7:d2dfb90150fb3a4094e51b74ca2900a2', 'createTable, addPrimaryKey, createSequence, addForeignKeyConstraint', '', 'EXECUTED', '3.3.5');

-- Changeset src/main/db/1.0.0/dml/dml.xml::insert-roles::thanasis.sergouniotis
INSERT INTO public.roles (role_id, role_name) VALUES ('1', 'ADMIN');

INSERT INTO public.roles (role_id, role_name) VALUES ('2', 'AUTHOR');

INSERT INTO public.roles (role_id, role_name) VALUES ('3', 'CHIEF_EDITOR');

INSERT INTO public.databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('insert-roles', 'thanasis.sergouniotis', 'src/main/db/1.0.0/dml/dml.xml', NOW(), 6, '7:53b9929afc81e836e68f6a21c1563d90', 'insert (x3)', '', 'EXECUTED', '3.3.5');

-- Changeset src/main/db/1.0.0/dml/dml.xml::insert-users::thanasis.sergouniotis
INSERT INTO public.users (user_id, username, password) VALUES (nextval('userseq'), 'admin', 'admin');

INSERT INTO public.users (user_id, username, password) VALUES (nextval('userseq'), 'author', 'author');

INSERT INTO public.users (user_id, username, password) VALUES (nextval('userseq'), 'chief', 'chief');

INSERT INTO public.databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('insert-users', 'thanasis.sergouniotis', 'src/main/db/1.0.0/dml/dml.xml', NOW(), 7, '7:81006e7d715a32eb2161aa1f08b6de15', 'insert (x3)', '', 'EXECUTED', '3.3.5');

-- Changeset src/main/db/1.0.0/dml/dml.xml::insert-users-roles::thanasis.sergouniotis
INSERT INTO public.users_roles (role_id, user_id) VALUES ((select role_id from roles where role_name='ADMIN'), (select user_id from users where username='admin'));

INSERT INTO public.users_roles (role_id, user_id) VALUES ((select role_id from roles where role_name='AUTHOR'), (select user_id from users where username='author'));

INSERT INTO public.users_roles (role_id, user_id) VALUES ((select role_id from roles where role_name='CHIEF_EDITOR'), (select user_id from users where username='chief'));

INSERT INTO public.databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('insert-users-roles', 'thanasis.sergouniotis', 'src/main/db/1.0.0/dml/dml.xml', NOW(), 8, '7:4553bbc59dece2684b63571db8b73a39', 'insert (x3)', '', 'EXECUTED', '3.3.5');

-- Changeset src/main/db/1.0.0/dml/dml.xml::insert-categories::thanasis.sergouniotis
INSERT INTO public.categories (category_id, category_name) VALUES (nextval('CATEGORYSEQ'), 'politics');

INSERT INTO public.categories (category_id, category_name) VALUES (nextval('CATEGORYSEQ'), 'economy');

INSERT INTO public.categories (category_id, category_name) VALUES (nextval('CATEGORYSEQ'), 'sports');

INSERT INTO public.categories (category_id, category_name) VALUES (nextval('CATEGORYSEQ'), 'technology');

INSERT INTO public.categories (category_id, category_name) VALUES (nextval('CATEGORYSEQ'), 'social');

INSERT INTO public.databasechangelog (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('insert-categories', 'thanasis.sergouniotis', 'src/main/db/1.0.0/dml/dml.xml', NOW(), 9, '7:04faa6e77909a5aaf773983a306a9c3c', 'insert (x5)', '', 'EXECUTED', '3.3.5');

-- Release Database Lock
UPDATE public.databasechangeloglock SET LOCKED = FALSE, LOCKEDBY = NULL, LOCKGRANTED = NULL WHERE ID = 1;

