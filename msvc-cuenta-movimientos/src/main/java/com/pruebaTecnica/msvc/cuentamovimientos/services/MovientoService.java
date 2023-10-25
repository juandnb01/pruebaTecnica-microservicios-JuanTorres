package com.pruebaTecnica.msvc.cuentamovimientos.services;

import com.pruebaTecnica.msvc.cuentamovimientos.models.entitys.Movimiento;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz que define las operaciones disponibles para gestionar movimientos de cuentas.
 * Autor: JuanPabloTorres
 */
public interface MovientoService {

    /**
     * Obtiene una lista de todos los movimientos de cuentas.
     *
     * @return Una lista de movimientos de cuentas.
     */
    List<Movimiento> listar();

    /**
     * Obtiene los detalles de un movimiento de cuenta específico por su ID.
     *
     * @param id El ID del movimiento que se desea consultar.
     * @return Los detalles del movimiento de cuenta o un valor vacío si no se encuentra.
     */
    Optional<Movimiento> porId(Long id);

    /**
     * Guarda un nuevo movimiento de cuenta en la base de datos o actualiza uno existente.
     *
     * @param movimiento El movimiento de cuenta que se va a guardar o actualizar.
     * @return El movimiento de cuenta guardado o actualizado.
     */
    Movimiento guardar(Movimiento movimiento);

    /**
     * Elimina un movimiento de cuenta por su ID.
     *
     * @param id El ID del movimiento de cuenta que se desea eliminar.
     */
    void eliminar(Long id);

    /**
     * Busca movimientos de cuentas por fecha.
     *
     * @param fecha La fecha para la cual se desean buscar movimientos de cuentas.
     * @return Una lista de objetos que representan los movimientos de cuentas encontrados.
     */
    List<Object[]> findMovimientosByFecha(Date fecha);
}
