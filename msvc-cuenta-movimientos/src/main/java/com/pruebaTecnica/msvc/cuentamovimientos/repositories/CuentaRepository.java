package com.pruebaTecnica.msvc.cuentamovimientos.repositories;

import com.pruebaTecnica.msvc.cuentamovimientos.models.entitys.Cuenta;
import org.springframework.data.repository.CrudRepository;

/**
 * Interfaz que proporciona métodos para acceder y gestionar las cuentas en la base de datos.
 *  * Autor: JuanPabloTorres
 */
public interface CuentaRepository extends CrudRepository<Cuenta, Long> {
}
