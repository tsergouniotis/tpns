create table articles(

	article_id numeric(11) not null,
	subject varchar(255),
	text varchar(2048),
	created_at timestamp not null,
	updated_at timestamp

);

alter table articles add constraint article_pk primary key(article_id);

create sequence articleseq increment by 1 start with 1;


create table users(

	user_id numeric(11) not null,
	username varchar(255) not null,
	password varchar(255) not null

);

alter table users add constraint user_pk primary key(user_id);

create sequence userseq increment by 1 start with 4;


create table roles(

	role_id numeric(11) not null,
	name varchar(255) not null

);
alter table roles add constraint role_pk primary key (role_id);

create table users_roles(

	user_id numeric(11),
	role_id numeric(11)

);

alter table users_roles add constraint user_roles_userfk foreign key (user_id) references users(user_id);
alter table users_roles add constraint user_roles_rolefk foreign key (role_id) references roles(role_id);

insert into roles values ( 1, 'ADMIN');
insert into roles values ( 2, 'AUTHOR');
insert into roles values (3, 'CHIEF_EDITOR');

insert into users values ( 1, 'admin', 'admin' );
insert into users values ( 2, 'author','author');
insert into users values (3, 'chief','chief');

insert into users_roles values (1,1);
insert into users_roles values (2,2);
insert into users_roles values (3,3);
