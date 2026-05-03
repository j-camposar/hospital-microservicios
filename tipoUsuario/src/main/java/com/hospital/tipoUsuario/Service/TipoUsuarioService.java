package com.hospital.tipoUsuario.Service;

import java.util.List;
import java.util.Optional;

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

    public void Crear(TipoUsuario tipoUsuario){
        tipoUsuarioRepository.save(tipoUsuario);
    }

    public List<TipoUsuario> buscarTodos(){
        return tipoUsuarioRepository.findAll();
    }
     public Optional<TipoUsuario> buscarPorId(int id ){
        return tipoUsuarioRepository.findById(id);
    }
}
