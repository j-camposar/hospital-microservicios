package com.hospital.atencion.Controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.atencion.DTO.AtencionDTO;
import com.hospital.atencion.Model.Atencion;
import com.hospital.atencion.Service.AtencionService;


@RestController
@RequestMapping("/api/v1/atenciones")
public class AtencionController {
    @Autowired
    private AtencionService atencionService;

    @PostMapping("/crear-atencion")
    public Atencion crearAtencion (@RequestBody AtencionDTO  atencion) {
        return atencionService.guardarAtencion(atencion);
    }
    @GetMapping("/buscar-paciente/{pacienteId}")
    public List<Atencion> buscarPorPaciente (@PathVariable Integer pacienteId) {
        return atencionService.obtenerPorPaciente(pacienteId);
    }
     @GetMapping("/buscar-medico/{medicoId}")
    public List<Atencion> buscarPorMedico (@PathVariable Integer medicoId) {
        return atencionService.obtenerPorMedico(medicoId);
    }
}