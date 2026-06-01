package com.hospital.tipoUsuario.Service;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.tipoUsuario.Model.TipoUsuario;
import com.hospital.tipoUsuario.Repository.TipoUsuarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TipoUsuarioService {
    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;
    
    private static final Logger log = LoggerFactory.getLogger(TipoUsuarioService.class);

    public TipoUsuario Crear(TipoUsuario tipoUsuario){
        TipoUsuario tipoUsuarioNuevo= tipoUsuarioRepository.save(tipoUsuario);
        log.info("Tipo de usuario credo correctamente", tipoUsuario);
        return tipoUsuarioNuevo;
    }

    public List<TipoUsuario> buscarTodos(){
        return tipoUsuarioRepository.findAll();
    }
     public Optional<TipoUsuario> buscarPorId(int id ){
        return tipoUsuarioRepository.findById(id);
    }
}
