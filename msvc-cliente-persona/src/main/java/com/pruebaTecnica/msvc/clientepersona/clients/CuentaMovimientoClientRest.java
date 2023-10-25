package com.pruebaTecnica.msvc.clientepersona.clients;

import com.pruebaTecnica.msvc.clientepersona.models.Cuenta;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * Autor: JuanPabloTorres
 * La interfaz CuentaMovimientoClientRest proporciona métodos para interactuar con el microservicio de cuentas y movimientos.
 * Permite realizar operaciones como consultar detalles de cuentas y crear nuevas cuentas.
 */
@FeignClient(name = "msvc-cuenta-movimientos", url="${msvc.cuentamovimientos.url}")
public interface CuentaMovimientoClientRest {

    /**
     * Obtiene los detalles de una cuenta específica.
     *
     * @param id El ID de la cuenta que se desea consultar.
     * @return Los detalles de la cuentA.
     */
    @GetMapping("api/cuentas/{id}")
    Cuenta detalleCuenta(@PathVariable Long id);

    /**
     * Crea una nueva cuenta.
     *
     * @param cuenta La cuenta que se va a crear.
     * @return La cuenta creada.
     */
    @PostMapping("api/cuentas")
    Cuenta crearCuenta(@RequestBody Cuenta cuenta);

    /**
     * Obtiene todas las cuentas de la base datos
     *
     * @return Los detalles de cada cuenta
     */
    @GetMapping("api/cuentas")
    Cuenta cuentasDetalles();


}
