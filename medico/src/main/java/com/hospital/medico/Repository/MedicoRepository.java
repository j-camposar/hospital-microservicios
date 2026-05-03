package com.hospital.medico.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.medico.Model.Medico;


public interface MedicoRepository extends JpaRepository<Medico, Integer>{

}
