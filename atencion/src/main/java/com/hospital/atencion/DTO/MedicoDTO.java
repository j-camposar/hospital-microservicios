package com.hospital.atencion.DTO;

import lombok.Data;

@Data
public class MedicoDTO {
    private Integer id;
    private String nombre;
    private String especialidades;
    private char jefeTurno;
    private String runMedico;
}
