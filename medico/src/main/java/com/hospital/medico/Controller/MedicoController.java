package com.hospital.medico.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.hospital.medico.DTO.MedicoDTO;
import com.hospital.medico.Model.Medico;
import com.hospital.medico.Service.MedicoService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/v1/medico")
public class MedicoController {
    @Autowired
    private MedicoService medicoService;

    @PostMapping
    public ResponseEntity<String> crearMedico (@RequestBody MedicoDTO medico) {
        medicoService.crear(medico);
        return ResponseEntity.ok("Medico creado correctamente");
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) {
        Optional<Medico> medico = medicoService.buscarPorId(id);
        
        if (medico.isEmpty()) {
            // Devolvemos 404 para que el WebClient del otro microservicio capture el error
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body("Médico con ID " + id + " no encontrado.");
        }
        
        // Retornamos el objeto Medico directamente 
        return ResponseEntity.ok(medico.get());
    }
    

}
