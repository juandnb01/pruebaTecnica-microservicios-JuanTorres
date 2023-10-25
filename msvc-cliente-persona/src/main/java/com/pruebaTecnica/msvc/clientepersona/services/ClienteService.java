package com.pruebaTecnica.msvc.clientepersona.services;

import com.pruebaTecnica.msvc.clientepersona.models.Cuenta;
import com.pruebaTecnica.msvc.clientepersona.models.entitys.Cliente;

import java.util.List;
import java.util.Optional;

/**
 *  Autor: JuanPabloTorres
 * Interfaz que define los métodos disponibles en el servicio de cliente
 */

public interface ClienteService {

    /**
     * Obtiene la lista de todos los clientes.
     *
     * @return Lista de clientes.
     */
    List<Cliente> listar();

    /**
     * Obtiene los detalles de un cliente por su identificador único.
     *
     * @param id El ID del cliente que se desea consultar.
     * @return Los detalles del cliente si existe, de lo contrario, un valor vacío.
     */
    Optional<Cliente> porId(Long id);

    /**
     * Guarda un nuevo cliente o actualiza uno existente en la base de datos.
     *
     * @param cliente El cliente a guardar o actualizar.
     * @return El cliente guardado o actualizado.
     */
    Cliente guardar(Cliente cliente);

    /**
     * Elimina un cliente por su identificador único.
     *
     * @param id El ID del cliente que se desea eliminar.
     */
    void eliminar(Long id);


    /**
     * Asigna una cuenta a un cliente.
     *
     * @param cuenta     La cuenta que se desea asignar al cliente.
     * @param clienteId  El ID del cliente al que se asignará la cuenta.
     * @return La cuenta asignada si se ha realizado con éxito, de lo contrario, un valor vacío.
     */
    Optional<Cuenta> asignarCuenta(Cuenta cuenta, Long clienteId);

    /**
     * Crea una nueva cuenta para un cliente.
     *
     * @param cuenta     La cuenta que se desea crear para el cliente.
     * @param clienteId  El ID del cliente para el que se creará la cuenta.
     * @return La cuenta creada si se ha realizado con éxito, de lo contrario, un valor vacío.
     */
    Optional<Cuenta> crearCuenta(Cuenta cuenta, Long clienteId);

    /**
     * Elimina una cuenta de un cliente.
     *
     * @param cuenta     La cuenta que se desea eliminar del cliente.
     * @param clienteId  El ID del cliente del que se eliminará la cuenta.
     * @return La cuenta eliminada si se ha realizado con éxito, de lo contrario, un valor vacío.
     */
    Optional<Cuenta> eliminarCuenta(Cuenta cuenta, Long clienteId);
}
