package com.hospital.tipoUsuario.DTO;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ErrorResponse {
    public String mensaje;
    public String detalle;
    public int status;
    public LocalDateTime timeStamp;
}
