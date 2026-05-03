  create table atencion (
        id integer not null auto_increment,
        costo integer not null,
        medico_id integer not null,
        paciente_id integer not null,
        fecha_hora datetime,
        comentarios varchar(255),
        primary key (id)
    ) 