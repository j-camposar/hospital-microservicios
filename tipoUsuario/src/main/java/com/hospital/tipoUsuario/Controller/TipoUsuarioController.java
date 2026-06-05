package com.hospital.tipoUsuario.Controller;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.tipoUsuario.Assemblers.TipoUsuarioModelAssembler;
import com.hospital.tipoUsuario.Model.TipoUsuario;
import com.hospital.tipoUsuario.Service.TipoUsuarioService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/tipo-usuario")
public class TipoUsuarioController {
    @Autowired
    private TipoUsuarioService tipoUsuarioService;
    
    @Autowired
    private TipoUsuarioModelAssembler tipoUsuarioModelAssembler;    
    
    @PostMapping
    public ResponseEntity<EntityModel<TipoUsuario>> crearTipoUsuario(@Valid @RequestBody TipoUsuario tipoUsuario) {
        TipoUsuario nuevo = tipoUsuarioService.Crear(tipoUsuario);
        EntityModel<TipoUsuario> model = tipoUsuarioModelAssembler.toModel(nuevo);
        
        return ResponseEntity.ok()
                .body(model);
    }

    
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<TipoUsuario>>> buscarTodos() {
          List<EntityModel<TipoUsuario>> tipoUsuarios= tipoUsuarioService.buscarTodos().stream()
         .map(tipoUsuarioModelAssembler::toModel)
         .collect(Collectors.toList());
        
        // Creamos la colección HATEOAS automática agregándole el link hacia "sí misma" (la lista completa)
        CollectionModel<EntityModel<TipoUsuario>> collectionModel = CollectionModel.of(tipoUsuarios,
                linkTo(methodOn(TipoUsuarioController.class).buscarTodos()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }
    @GetMapping("{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) { 
        Optional<TipoUsuario> tipoUsuarios = tipoUsuarioService.buscarPorId(id);
        
        if(tipoUsuarios.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body("No existe el tipo de usuario con ID: " + id);
        }
       EntityModel<TipoUsuario> model = tipoUsuarioModelAssembler.toModel(tipoUsuarios.get());
        return ResponseEntity.ok(model); 
    }
}
