package com.hospital.tipoUsuario.Excepciones;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.hospital.tipoUsuario.DTO.ErrorResponse;
import com.hospital.tipoUsuario.Service.TipoUsuarioService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

import static net.logstash.logback.argument.StructuredArguments.kv;
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(TipoUsuarioService.class);


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(EntityNotFoundException ex){
        ErrorResponse error = new ErrorResponse();
        error.setMensaje("Recurso no encontrado");
        error.setDetalle(ex.getMessage());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setTimeStamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex){
        String detalle= ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(err->err.getField() + ":" + err.getDefaultMessage())
        .collect(Collectors.joining(","));
        // 2. Registramos la advertencia de validación en nuestros logs de bajo nivel
        // Usamos 'kv' para que las herramientas como Loki puedan indexar la variable 'detalle'
        log.warn("Fallo de validación en tiempo de persistencia", kv("invalid_fields", detalle));

        ErrorResponse error = new ErrorResponse();
        error.setMensaje("Errores de validacion");
        error.setDetalle(detalle);
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setTimeStamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex, HttpServletRequest request){
        log.warn("Fallo Generico en la aplicacion");
        ErrorResponse error = new ErrorResponse();
        error.setMensaje("Errores interno del servidor");
        error.setDetalle(ex.getMessage());
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setTimeStamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFound(NoResourceFoundException ex, HttpServletRequest request) {
        
        HttpStatus status = HttpStatus.NOT_FOUND; // Código HTTP 404

        // Registramos la advertencia estructurada para Grafana Loki
        log.warn("Ruta o recurso estático no encontrado", 
            kv("http_method", request.getMethod()),
            kv("request_path", request.getRequestURI()),
            kv("resource_name", ex.getResourcePath())
        );

        // Construimos la respuesta homogénea para el cliente
        ErrorResponse error = new ErrorResponse();
        error.setMensaje("Ruta no encontrada");
        error.setDetalle("El endpoint '" + request.getRequestURI() + "' no existe en este servidor o el recurso estático no fue encontrado.");
        error.setStatus(status.value()); // 404
        error.setTimeStamp(LocalDateTime.now());

        return ResponseEntity.status(status).body(error);
    }
        
}
