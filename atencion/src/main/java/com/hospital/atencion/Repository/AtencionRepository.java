package com.hospital.atencion.Repository;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hospital.atencion.Model.Atencion;
@Repository
public interface AtencionRepository extends JpaRepository<Atencion, Integer> {
    
    // Consulta para Reporte de atenciones por médico
    List<Atencion> findByMedicoId(Integer medicoId);

    // Consulta para Reporte de atenciones por paciente
    List<Atencion> findByPacienteId(Integer pacienteId);
    
    // Consulta para Reporte de atenciones por fecha (entre un rango)
    List<Atencion> findByFechaHoraBetween(LocalDateTime inicio, LocalDateTime fin);


}