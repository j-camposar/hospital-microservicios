package com.hospital.paciente.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.paciente.Model.Paciente;
import com.hospital.paciente.Service.PacienteService;

@RestController
@RequestMapping("/api/v1/paciente")
public class PacienteController {
    @Autowired
    private PacienteService pacienteService;

    @GetMapping
    public ResponseEntity<List<Paciente>> buscarTodos() {
        List<Paciente> listaPacientes = pacienteService.buscarTodos();
        if(listaPacientes.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listaPacientes);
    }
    @PostMapping
    public Paciente postMethodName(@RequestBody Paciente paciente) {
        return pacienteService.crear(paciente);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPaciente(@PathVariable Integer id) {
        Paciente pacientes = pacienteService.obtenerHistorialCompleto(id);
        // Si el objeto es null o no tiene ID, devolvemos 404
        if (pacientes == null || pacientes.getId() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pacientes);
    }
}