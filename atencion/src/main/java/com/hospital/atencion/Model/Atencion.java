package com.hospital.atencion.Model;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="atencion")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Atencion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Se usa LocalDateTime para manejar fecha y hora automáticamente
    private LocalDateTime fechaHora; 
    
    private Double costo;
    
    private String comentarios;

    private Integer pacienteId;

    private Integer medicoId;
}
