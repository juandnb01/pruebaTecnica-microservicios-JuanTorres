package com.pruebaTecnica.msvc.clientepersona.repositories;

import com.pruebaTecnica.msvc.clientepersona.models.entitys.Cliente;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


// Autor: JuanPabloTorres
// Interfaz que representa el repositorio de datos para la entidad Cliente
public interface ClienteRepository extends CrudRepository<Cliente, Long> {

    // Define el método para buscar un cliente por número de identificación
    Optional<Cliente> findByIdentificacion(String numeroIdentificacion);
}
