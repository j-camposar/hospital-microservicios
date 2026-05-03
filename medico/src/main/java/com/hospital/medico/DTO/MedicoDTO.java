package com.hospital.medico.DTO;

import lombok.Data;

@Data
public class MedicoDTO {
    private String nombre;
    private String especialidades;
    private char jefeTurno;
    private String runMedico;
}
