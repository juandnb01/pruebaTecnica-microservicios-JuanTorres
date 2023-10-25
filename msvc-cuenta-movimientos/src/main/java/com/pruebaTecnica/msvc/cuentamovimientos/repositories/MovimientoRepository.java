package com.pruebaTecnica.msvc.cuentamovimientos.repositories;

import com.pruebaTecnica.msvc.cuentamovimientos.models.entitys.Movimiento;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

/**
 * Interfaz que proporciona métodos para acceder y gestionar los movimientos financieros en la base de datos.
 * Autor: JuanPabloTorres
 */
public interface MovimientoRepository extends CrudRepository<Movimiento, Long> {

    List<Object[]> findMovimientosByFecha(Date fecha);
}
