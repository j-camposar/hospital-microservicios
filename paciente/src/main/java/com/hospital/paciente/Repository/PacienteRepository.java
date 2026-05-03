package com.hospital.paciente.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hospital.paciente.Model.Paciente;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Integer>  {
    List<Paciente> findByApellido(String apellido);
    @Query(value="Select p from paciente p nombre= :nombre", nativeQuery = true)
    List<Paciente> buscarPorNombre(String nombre);
}
