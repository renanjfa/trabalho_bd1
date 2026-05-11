create schema f;
create sequence f.id_dataset start with 1 increment by 1;
create sequence f.id_feature start with 1 increment by 1;
create sequence f.id_versao start with 1 increment by 1;

create table f.usuario(
	email varchar(160)	not null,
	nome_usuario varchar(160) not null,
	senha	varchar(160)	not null,

	constraint pk_usuario primary key(email)
);

create table f.data_set(
	id integer not null default nextval('f.id_dataset'),
	nome_dataset varchar(120) not null,
	descricao text,
	data date not null,
	hora time not null,
	email_usuario varchar(160) not null,
	constraint pk_dataset primary key(id),
	constraint fk_dataset_usuario foreign key(email_usuario) references f.usuario(email)
);
create table f.fontes(
	id_dataset integer not null,
	fonte varchar(160) not null,
	constraint pk_fontes primary key (id_dataset,fonte),
	constraint fk_fontes_dataset foreign key(id_dataset) references f.data_set(id)
);
create table f.versao(
	id_versao	integer not null default nextval('f.id_versao'),
	email_usuario varchar(160) not null,
	id_dataset	  integer not null,
	data date not null,
	hora time not null,
	descricao text,
	csv  varchar(255) not null,
	nome_versao varchar(120) not null,

	id_versao_base integer,

    constraint pk_versao
        primary key (id_versao),

    constraint fk_versao_usuario
        foreign key (email_usuario)
        references f.usuario(email),

    constraint fk_versao_dataset
        foreign key (id_dataset)
        references f.data_set(id),

    constraint fk_versao_base
        foreign key (id_versao_base)
        references f.versao(id_versao),

    constraint uq_versao_identificao
        unique (email_usuario, id_dataset, data, hora),
		
	constraint uq_versao_nome_por_dataset
		unique (id_dataset,nome_versao),
		
	constraint ck_versao_nao_base_dela_mesma
        check (
            id_versao_base is null 
            OR id_versao_base <> id_versao
        )
);

create table f.usuario_acessa_versao(
    email_usuario varchar(160) not null,
	id_versao integer not null,
	data date not null,
	hora time not null,

    constraint pk_usuario_acessa_versao
        primary key (email_usuario, id_versao, data, hora),

    constraint fk_acessa_usuario
        foreign key (email_usuario)
        references f.usuario(email),

    constraint fk_acessa_versao
        foreign key (id_versao)
        references f.versao(id_versao)
);

CREATE TABLE f.usuario_faz_download_versao (
    email_usuario varchar(160) not null,
    id_versao integer not null,
    data date not null,
    hora time not null,

    constraint pk_usuario_faz_download_versao
        primary key (email_usuario, id_versao, data, hora),

    constraint fk_download_usuario
        foreign key (email_usuario)
        references f.usuario(email),

    constraint fk_download_versao
        foreign key (id_versao)
        references f.versao(id_versao)
);

create table f.feature(
	id_feature	integer not null default nextval('f.id_feature'),
	nome_feature varchar(120) not null,
	descricao text,
	constraint pk_feature primary key (id_feature)
);

create table f.versao_possui_feature(
	id_versao integer not null,
	id_feature integer not null,

	constraint pk_versao_possui_feature primary key (id_versao,id_feature),
	constraint fk_possui_versao foreign key (id_versao) references f.versao(id_versao),
	constraint fk_possu_feature foreign key (id_feature) references f.feature(id_feature)
);
