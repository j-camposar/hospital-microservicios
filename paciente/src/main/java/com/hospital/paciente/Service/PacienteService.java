package com.hospital.paciente.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.hospital.paciente.Config.WebClientConfig;
import com.hospital.paciente.DTO.TipoUsuarioDTO;
import com.hospital.paciente.Model.Paciente;
import com.hospital.paciente.Repository.PacienteRepository;

import jakarta.transaction.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class PacienteService {
    
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    @Qualifier("webClientUsuarios") //anotacion para decir a webclient cual usara 
    private WebClient webClient;

    public List<Paciente> buscarTodos(){
        return pacienteRepository.findAll();
    }

    public Paciente crear(Paciente pacienteDTO){
        Paciente paciente= new Paciente();
        paciente.setApellido(pacienteDTO.getApellido());
        paciente.setNombre(pacienteDTO.getNombre());
        paciente.setRun(pacienteDTO.getRun());
        paciente.setFechaNacimiento(pacienteDTO.getFechaNacimiento());
        paciente.setCorreo(pacienteDTO.getCorreo());

        TipoUsuarioDTO tipo = webClient.get() 
                .uri("/tipo-usuario/"+pacienteDTO.getTipoUsuario(), pacienteDTO.getTipoUsuario()) 
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> 
                    Mono.error(new RuntimeException("Tipo usuario no encontrado")))
                .bodyToMono(TipoUsuarioDTO.class) 
                .block();//sincronico 
        System.out.println(tipo);
        paciente.setTipoUsuario(tipo.getId());
        Paciente pacienteSave=pacienteRepository.save(paciente);
 
        return pacienteSave;
    }

    public Paciente guardarPaciente(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    // Obtener historial completo (Paciente + Ficha + Atenciones)
    public Paciente obtenerHistorialCompleto(Integer id) {
        return pacienteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
    }

    public List<Paciente> listarTodos() {
        return pacienteRepository.findAll();
    }
}

