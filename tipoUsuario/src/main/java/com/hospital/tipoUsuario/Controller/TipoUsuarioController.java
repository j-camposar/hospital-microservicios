package com.hospital.tipoUsuario.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.tipoUsuario.Model.TipoUsuario;
import com.hospital.tipoUsuario.Service.TipoUsuarioService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/v1/tipo-usuario")
public class TipoUsuarioController {
    @Autowired
    private TipoUsuarioService tipoUsuarioService;

    @PostMapping
    public ResponseEntity<String> crearTipoUsuario(@RequestBody TipoUsuario tipoUsuario) {
        tipoUsuarioService.Crear(tipoUsuario);
        return ResponseEntity.ok("creado correctamente");
    }
    @GetMapping
    public ResponseEntity<List<TipoUsuario>> buscarTodos() {
        List <TipoUsuario> tipoUsuarios= tipoUsuarioService.buscarTodos();
        return ResponseEntity.ok(tipoUsuarios);
    }
    @GetMapping("{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) { 
        Optional<TipoUsuario> tipoUsuarios = tipoUsuarioService.buscarPorId(id);
        
        if(tipoUsuarios.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body("No existe el tipo de usuario con ID: " + id);
        }
        
        return ResponseEntity.ok(tipoUsuarios.get()); 
    }
}
