  create table paciente (
        id integer not null auto_increment,
        tipo_usuario integer not null,
        fecha_nacimiento datetime(6),
        run varchar(13) not null,
        apellido varchar(255),
        correo varchar(255),
        nombre varchar(255),
        primary key (id)
    ) 