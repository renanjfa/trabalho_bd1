-- SELECT * FROM para todas as tabelas --
SELECT * FROM f.usuario;
SELECT * FROM f.data_set;
SELECT * FROM f.versao;
SELECT * FROM f.feature;
SELECT * FROM f.dataset_possui_fontes;
SELECT * FROM f.usuario_acessa_versao;
SELECT * FROM f.usuario_faz_download_versao;
SELECT * FROM f.versao_possui_feature;



-- #####   INSERTS in f.usuario   ##### --

INSERT INTO f.usuario (email, nome_usuario, senha)
VALUES ('adm@uel.br', 'adm1', '123');

-- ERRO ESPERADO: duplicate key value violates unique constraint "pk_usuario" --
INSERT INTO f.usuario (email, nome_usuario, senha)
VALUES ('adm@uel.br', 'rafa', '123');

INSERT INTO f.usuario (email, nome_usuario, senha)
VALUES ('renan@uel.br', 'renan', '789');

INSERT INTO f.usuario (email, nome_usuario, senha)
VALUES ('juan@uel.br', 'juan', '456');




-- #####   INSERTS in f.data_set   ##### --

INSERT INTO f.data_set (nome_dataset, descricao, data, hora, email_usuario)
VALUES ('human-vs-ai-text', 'Dataset para diferenciar texto humano ou gerado por IAs generativas', '2026-05-10', '11:45', 'renan@uel.br');

INSERT INTO f.data_set (nome_dataset, descricao, data, hora, email_usuario)
VALUES ('brasileirao-2025', 'Dataset Estatisticas Brasileirao 2025', '2026-05-10', '19:12', 'renan@uel.br');

-- ERRO ESPERADO: insert or update on table "data_set" violates foreign key constraint "fk_dataset_usuario" --
INSERT INTO f.data_set (nome_dataset, descricao, data, hora, email_usuario)
VALUES ('vendas-empresa', 'Dataset vendas de uma empresa', '2026-05-10', '22:45', 'rafa@uel.br');

INSERT INTO f.data_set (nome_dataset, descricao, data, hora, email_usuario)
VALUES ('vendas-empresa', 'Dataset vendas de uma empresa', '2026-05-10', '22:45', 'juan@uel.br');




-- #####   INSERTS in f.versao   ##### --

INSERT INTO f.versao (email_usuario, id_dataset, data, hora, csv, nome_versao, descricao)
VALUES ('renan@uel.br', 3, '2026-05-22', '9:00', 'brasileirao-fla-2025.csv', 'flamengo-estatisticas', 'Estatisticas somente para o time campeao do brasileirao 2025');


INSERT INTO f.versao (email_usuario, id_dataset, data, hora, csv, nome_versao, descricao)
VALUES ('juan@uel.br', 4, '2026-05-22', '13:00', 'vendas_estagio.csv', 'vendas-estagio', 'Dataset modificado com as vendas e dados do estagio');

INSERT INTO f.versao (email_usuario, id_dataset, data, hora, csv, nome_versao, descricao, id_versao_base)
VALUES ('juan@uel.br', 4, '2026-05-25', '23:00', 'vendas_estagio.csv', 'vendas-estagio-organized', 'Somente vendas em 2026', 2);




-- #####   INSERTS in f.feature   ##### --

INSERT INTO f.feature(nome_feature, descricao)
VALUES ('ano', 'Ano do Campeonato Brasileirao');

INSERT INTO f.feature(nome_feature, descricao)
VALUES ('campeao', 'Campeao Campeonato Brasileiro');

INSERT INTO f.feature(nome_feature, descricao)
VALUES ('gols', 'Total de gols do Campeonato Brasileirao');

INSERT INTO f.feature(nome_feature, descricao)
VALUES ('text', 'Texto redigido por IA ou por humanos');

INSERT INTO f.feature(nome_feature, descricao)
VALUES ('class', 'Humano (1) ou IA (0)');




-- #####   INSERTS in f.dataset_possui_fontes   ##### --

INSERT INTO f.dataset_possui_fontes (id_dataset, fonte)
VALUES (1, 'kaggle');

INSERT INTO f.dataset_possui_fontes (id_dataset, fonte)
VALUES (3, 'kaggle');

INSERT INTO f.dataset_possui_fontes (id_dataset, fonte)
VALUES (1, 'NCBI GEO');



-- #####   INSERTS in f.usuario_acessa_versao   ##### --

INSERT INTO f.usuario_acessa_versao (email_usuario, id_versao, data, hora)
VALUES ('renan@uel.br', 4, '2026-05-25', '23:00');

INSERT INTO f.usuario_acessa_versao (email_usuario, id_versao, data, hora)
VALUES ('renan@uel.br', 1, '2026-05-26', '11:00');




-- #####   INSERTS in f.usuario_faz_download_versao   ##### --

INSERT INTO f.usuario_faz_download_versao (email_usuario, id_versao, data, hora)
VALUES ('juan@uel.br', 4, '2026-05-27', '23:00');

INSERT INTO f.usuario_faz_download_versao (email_usuario, id_versao, data, hora)
VALUES ('juan@uel.br', 1, '2026-05-28', '13:45');




-- #####   INSERTS in f.versao_possui_feature   ##### --

INSERT INTO f.versao_possui_feature (id_versao, id_feature)
VALUES (1, 1);

INSERT INTO f.versao_possui_feature (id_versao, id_feature)
VALUES (1, 2);

INSERT INTO f.versao_possui_feature (id_versao, id_feature)
VALUES (1, 3);