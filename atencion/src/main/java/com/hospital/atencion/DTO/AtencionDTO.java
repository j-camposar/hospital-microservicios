package com.hospital.atencion.DTO;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AtencionDTO {
    private LocalDateTime fechaHora; 
    private Double costo;
    private String comentarios;
    private Integer pacienteId;
    private Integer medicoId;
}
