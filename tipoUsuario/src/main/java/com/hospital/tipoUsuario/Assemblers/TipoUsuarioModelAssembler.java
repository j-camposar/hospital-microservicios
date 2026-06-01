package com.hospital.tipoUsuario.Assemblers;

import com.hospital.tipoUsuario.Controller.TipoUsuarioController;
import com.hospital.tipoUsuario.Model.TipoUsuario;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component // Importante para poder inyectarlo luego en el Controller
public class TipoUsuarioModelAssembler implements RepresentationModelAssembler<TipoUsuario, EntityModel<TipoUsuario>> {
    @Override
    public EntityModel<TipoUsuario> toModel(TipoUsuario entity) {
        // listamos las rutas GET que tenemos
        return EntityModel.of(entity,
                linkTo(methodOn(TipoUsuarioController.class).buscarPorId(entity.getId())).withSelfRel(),
                linkTo(methodOn(TipoUsuarioController.class).buscarTodos()).withRel("tipos-usuario")
        );
    }
}