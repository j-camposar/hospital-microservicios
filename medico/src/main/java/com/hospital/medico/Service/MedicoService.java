package com.hospital.medico.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.medico.DTO.MedicoDTO;
import com.hospital.medico.Model.Medico;
import com.hospital.medico.Repository.MedicoRepository;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class MedicoService {
    @Autowired
    private MedicoRepository medicoRepository;
   
    public void crear(MedicoDTO medicoDTO){
        Medico medico= new Medico();
        medico.setNombreCompleto(medicoDTO.getNombre());
        medico.setRunMedico(medicoDTO.getRunMedico());
        medico.setJefeTurno(medicoDTO.getJefeTurno());
        medico.setEspecialidades(medicoDTO.getEspecialidades());
        medicoRepository.save(medico);
    }   

    public Optional<Medico> buscarPorId(int id){
        return medicoRepository.findById(id);
    }
}
