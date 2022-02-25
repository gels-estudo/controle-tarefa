-- excluir as tabelas caso exista
drop table if exists tarefa CASCADE;
drop table if exists usuario CASCADE;

create table tarefa
(
    id               bigint not null,
    data_atualizacao timestamp,
    data_cadastro    timestamp,
    descricao        varchar(255),
    resumo           varchar(255),
    status           varchar(255),
    usuario_id       bigint not null,
    primary key (id)
);

create table usuario
(
    id           bigint       not null,
    superusuario boolean,
    login        varchar(255) not null,
    senha        varchar(255),
    primary key (id)
);

alter table usuario
drop
constraint if exists UK_USER;

alter table usuario
    add constraint UK_USER unique (login);

alter table tarefa
    add constraint FK_1L
        foreign key (usuario_id)
            references usuario;

insert into usuario (id, login, senha, superusuario) values (1, 'admin@gels.com.br', 'c2VsZWNhb0AyMDIyIQ==', true);
insert into usuario (id, login, senha, superusuario) values (2, 'user.comum@gels.com.br', 'c2VsZWNhb0AyMDIyIQ==', false);