package com.pruebaTecnica.msvc.cuentamovimientos.services;

import com.pruebaTecnica.msvc.cuentamovimientos.models.entitys.Cuenta;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz que define las operaciones disponibles para gestionar cuentas.
 *  Autor: JuanPabloTorres
 */
public interface CuentaService {

    /**
     * Obtiene una lista de todas las cuentas.
     *
     * @return Una lista de cuentas.
     */
    List<Cuenta> listar();

    /**
     * Obtiene los detalles de una cuenta específica por su ID.
     *
     * @param id El ID de la cuenta que se desea consultar.
     * @return Los detalles de la cuenta o un valor vacío si no se encuentra.
     */
    Optional<Cuenta> porId(Long id);

    /**
     * Guarda una nueva cuenta en la base de datos o actualiza una existente.
     *
     * @param cuenta La cuenta que se va a guardar o actualizar.
     * @return La cuenta guardada o actualizada.
     */
    Cuenta guardar(Cuenta cuenta);

    /**
     * Elimina una cuenta por su ID.
     *
     * @param id El ID de la cuenta que se desea eliminar.
     */
    void eliminar(Long id);
}
