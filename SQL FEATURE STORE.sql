create schema featurestore;
create sequence featurestore.id_dataset start with 1 increment by 1;
create sequence featurestore.id_feature start with 1 increment by 1;
create sequence featurestore.id_versao start with 1 increment by 1;

create table featurestore.usuario(
	email varchar(160)	not null,
	nome_usuario varchar(160) not null,
	senha	varchar(160)	not null,

	constraint pk_usuario primary key(email)
);

create table featurestore.data_set(
	id integer not null default nextval('featurestore.id_dataset'),
	nome_dataset varchar(120) not null,
	descricao text,
	data date not null,
	hora time not null,
	email_usuario varchar(160) not null,
	
	constraint pk_dataset primary key(id),
	constraint fk_dataset_usuario foreign key(email_usuario) references featurestore.usuario(email)
		ON DELETE CASCADE
		ON UPDATE CASCADE
);


create table featurestore.dataset_possui_fontes(
	id_dataset integer not null,
	fonte varchar(160) not null,
	
	constraint pk_fontes primary key (id_dataset,fonte),
	constraint fk_fontes_dataset foreign key(id_dataset) references featurestore.data_set(id) 
		ON DELETE CASCADE
);



create table featurestore.versao(
	id_versao	integer not null default nextval('featurestore.id_versao'),
	email_usuario varchar(160) not null,
	id_dataset	  integer not null,
	data date not null,
	hora time not null,
	csv  text not null,
	nome_versao varchar(120) not null,
	descricao text,
	id_versao_base integer,

    constraint pk_versao
        primary key (id_versao),

    constraint fk_versao_usuario
        foreign key (email_usuario)
        references featurestore.usuario(email)
			ON DELETE CASCADE
			ON UPDATE CASCADE,

    constraint fk_versao_dataset
        foreign key (id_dataset)
        references featurestore.data_set(id)
			ON DELETE CASCADE,

    constraint fk_versao_base
        foreign key (id_versao_base)
        references featurestore.versao(id_versao)
			ON DELETE SET NULL,

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


create table featurestore.usuario_acessa_dataset(
    email_usuario varchar(160) not null,
	id_dataset integer not null,
	data date not null,
	hora time not null,

    constraint pk_usuario_acessa_dataset
        primary key (email_usuario, id_dataset, data, hora),

    constraint fk_acessa_usuario
        foreign key (email_usuario)
        references featurestore.usuario(email)
		ON DELETE CASCADE
		ON UPDATE CASCADE,

    constraint fk_acessa_dataset
        foreign key (id_dataset)
        references featurestore.data_set(id)
		ON DELETE CASCADE
);



CREATE TABLE featurestore.usuario_faz_download_versao (
    email_usuario varchar(160) not null,
    id_versao integer not null,
    data date not null,
    hora time not null,

    constraint pk_usuario_faz_download_versao
        primary key (email_usuario, id_versao, data, hora),

    constraint fk_download_usuario
        foreign key (email_usuario)
        references featurestore.usuario(email)
			ON DELETE CASCADE
			ON UPDATE CASCADE,

    constraint fk_download_versao
        foreign key (id_versao)
        references featurestore.versao(id_versao)
			ON DELETE CASCADE
);


create table featurestore.feature(
	id_feature	integer not null default nextval('featurestore.id_feature'),
	nome_feature varchar(120) not null,
	tipo varchar(20) not null,
	descricao text,
	
	constraint pk_feature primary key (id_feature)
);


create table featurestore.versao_possui_feature(
	id_versao integer not null,
	id_feature integer not null,

	constraint pk_versao_possui_feature primary key (id_versao,id_feature),
	constraint fk_possui_versao foreign key (id_versao) references featurestore.versao(id_versao)
		ON DELETE CASCADE,
	constraint fk_possu_feature foreign key (id_feature) references featurestore.feature(id_feature)
		ON DELETE CASCADE
);

SELECT * FROM featurestore.usuario;
SELECT * FROM featurestore.data_set;
SELECT * FROM featurestore.versao;
SELECT * FROM featurestore.feature;
SELECT * FROM featurestore.dataset_possui_fontes;
SELECT * FROM featurestore.usuario_acessa_dataset;
SELECT * FROM featurestore.usuario_faz_download_versao;
SELECT * FROM featurestore.versao_possui_feature;