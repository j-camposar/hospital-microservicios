package com.hospital.atencion.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.hospital.atencion.DTO.AtencionDTO;
import com.hospital.atencion.DTO.MedicoDTO;
import com.hospital.atencion.DTO.PacienteDTO;
import com.hospital.atencion.Model.Atencion;
import com.hospital.atencion.Repository.AtencionRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor 
@Transactional
public class AtencionService {
    @Autowired
    private AtencionRepository atencionRepository;
    @Autowired
    @Qualifier("webClientMedico")
    private WebClient webClientMedico;
    @Autowired
    @Qualifier("webClientPaciente")
    private WebClient webClientPaciente;

    public Atencion guardarAtencion(AtencionDTO atencionDTO) {
        // findById  trae un Optional, pero como tenemos una relacion generamos
        //  una Excepcion en vez de guardarlo nulo 
        MedicoDTO medico = webClientMedico.get().
        uri("/medico/{id}", atencionDTO.getMedicoId())
        .retrieve()
        .onStatus(HttpStatusCode::is4xxClientError,response ->
            Mono.error(new RuntimeException("medico no encontrado")))
        .bodyToMono(MedicoDTO.class)
        .block();

        PacienteDTO paciente= webClientPaciente.get()
        .uri("/paciente/{id}", atencionDTO.getPacienteId())
        .retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, response ->
            Mono.error(new RuntimeException("paciente no encontrado"))
        )
        .bodyToMono(PacienteDTO.class)
        .block();
        // Paciente paciente= pacienteRepository.findById(atencionDTO.getPaciente())
        // .orElseThrow(() -> new RuntimeException("Paciente no encontrado con ID: " + atencionDTO.getPaciente()));
        Atencion atencion = new Atencion();
        atencion.setComentarios(atencionDTO.getComentarios());
        atencion.setCosto(atencionDTO.getCosto());
        atencion.setFechaHora(atencionDTO.getFechaHora());
        atencion.setMedicoId(medico.getId());
        atencion.setPacienteId(paciente.getId());
        return atencionRepository.save(atencion);
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