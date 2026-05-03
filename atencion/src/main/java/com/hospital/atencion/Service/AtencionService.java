package com.hospital.atencion.Service;

import java.lang.foreign.Linker.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.hospital.atencion.DTO.AtencionDTO;
import com.hospital.atencion.DTO.MedicoDTO;
import com.hospital.atencion.DTO.PacienteDTO;
import com.hospital.atencion.Model.Atencion;
import com.hospital.atencion.Repository.AtencionRepository;

import jakarta.transaction.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class AtencionService {
    @Autowired
    private AtencionRepository atencionRepository;
    @Autowired
    @Qualifier("webClientMedicos") //anotacion para decir a webclient cual usara 
    private WebClient webClientMedico;
    @Autowired
    @Qualifier("webClientPacientes") //anotacion para decir a webclient cual usara 
    private WebClient webClientPaciente;


    public Boolean guardarAtencion(AtencionDTO atencionDTO) {
        // 1. Buscamos al Médico (Bloqueante)
        MedicoDTO medico = webClientMedico.get()
                .uri("/medico/{id}", atencionDTO.getMedicoId()) 
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> 
                    Mono.error(new RuntimeException("Médico no encontrado")))
                .bodyToMono(MedicoDTO.class) 
                .block(); // Espera la respuesta aquí
        if (medico == null) {
            return false;
        }
        // 2. Buscamos al Paciente (Bloqueante)
        PacienteDTO paciente = webClientPaciente.get()
                .uri("/paciente/{id}", atencionDTO.getPacienteId()) // Corregido el path a /paciente
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> 
                    Mono.error(new RuntimeException("Paciente no encontrado")))
                .bodyToMono(PacienteDTO.class) 
                .block(); // Espera la respuesta aquí
        if (paciente == null) {
            return false;
        }
        // 3. Mapeo y Guardado
        Atencion atencion = new Atencion();
        atencion.setComentarios(atencionDTO.getComentarios());
        atencion.setCosto(atencionDTO.getCosto());
        atencion.setFechaHora(atencionDTO.getFechaHora());
        // Guardamos los IDs que validamos externamente
        atencion.setMedicoId(medico.getId());
        atencion.setPacienteId(paciente.getId());
        atencionRepository.save(atencion);
        return true;
    }

    // Reporte de atenciones por paciente
    public List<Atencion> obtenerPorPaciente(Integer pacienteId) {
        return atencionRepository.findByPacienteId(pacienteId);
    }

    // Reporte de atenciones por médico
    public List<Atencion> obtenerPorMedico(Integer medicoId) {
        return atencionRepository.findByMedicoId(medicoId);
    }

    // Reporte de atenciones por fecha
    public List<Atencion> obtenerPorRangoFecha(LocalDateTime inicio, LocalDateTime fin) {
        return atencionRepository.findByFechaHoraBetween(inicio, fin);
    }
  
}