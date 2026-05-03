package com.hospital.atencion.DTO;

import lombok.Data;

@Data
public class PacienteDTO {
    private Integer id ;
    private String run;
    private String nombre;
    private String apellido;
    private Integer tipoUsuarioId; 
    private String correo;
}
