package com.pruebaTecnica.msvc.cuentamovimientos.controllers;

import com.pruebaTecnica.msvc.cuentamovimientos.models.entitys.Cuenta;
import com.pruebaTecnica.msvc.cuentamovimientos.services.CuentaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Clase controladora para gestionar las operaciones relacionadas con cuentas.
 *
 * Autor: Juan Pablo Torres
 */
@RestController
@RequestMapping("/api")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    /**
     * Obtiene una lista de todas las cuentas.
     *
     * @return ResponseEntity con una lista de cuentas y estado HTTP OK.
     */
    @GetMapping("/cuentas")
    public ResponseEntity<List<Cuenta>> listar(){
        return ResponseEntity.ok(cuentaService.listar());
    }

    /**
     * Obtiene los detalles de una cuenta por su ID.
     *
     * @param id El ID de la cuenta.
     * @return ResponseEntity con la cuenta encontrada y estado HTTP OK, o estado HTTP Not Found si no se encuentra.
     */
    @GetMapping("/cuentas/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id){
        Optional<Cuenta> oCuenta = cuentaService.porId(id);
        if (oCuenta.isPresent()){
            return  ResponseEntity.ok(oCuenta.get());
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Crea una nueva cuenta.
     *
     * @param cuenta La cuenta a crear.
     * @param result Resultado de la validación.
     * @return ResponseEntity con la cuenta creada y estado HTTP Created, o errores de validación y estado HTTP Bad Request.
     */
    @PostMapping("/cuentas")
    public ResponseEntity<?> crear(@Valid @RequestBody Cuenta cuenta, BindingResult result){
        if(result.hasErrors()){
            return validarResultado(result);
        }
        Cuenta cuentaDb = cuentaService.guardar(cuenta);
        return ResponseEntity.status(HttpStatus.CREATED).body(cuentaDb);
    }

    /**
     * Edita una cuenta existente por su ID.
     *
     * @param cuenta La cuenta con los datos actualizados.
     * @param result Resultado de la validación.
     * @param id El ID de la cuenta a editar.
     * @return ResponseEntity con la cuenta editada y estado HTTP Created, o errores de validación y estado HTTP Bad Request, o estado HTTP Not Found si la cuenta no existe.
     */
    @PutMapping("/cuentas/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Cuenta cuenta,BindingResult result, @PathVariable Long id){
        if(result.hasErrors()){
            return validarResultado(result);
        }
        Optional<Cuenta> oCuenta = cuentaService.porId(id);
        if (oCuenta.isPresent()){
            Cuenta cuentaDb = oCuenta.get();
            cuentaDb.setNumeroCuenta(cuenta.getNumeroCuenta());
            cuentaDb.setTipoCuenta(cuenta.getTipoCuenta());
            cuentaDb.setSaldoInicial(cuenta.getSaldoInicial());
            cuentaDb.setEstado(cuenta.getEstado());
            return  ResponseEntity.status(HttpStatus.CREATED).body(cuentaService.guardar(cuentaDb));
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Elimina una cuenta por su ID.
     *
     * @param id El ID de la cuenta a eliminar.
     * @return ResponseEntity con estado HTTP No Content si la cuenta se elimina con éxito, o estado HTTP Not Found si la cuenta no existe.
     */
    @DeleteMapping("/cuentas/{id}")
    public  ResponseEntity<?> eliminar(@PathVariable Long id){
        Optional<Cuenta> oCuenta = cuentaService.porId(id);
        if (oCuenta.isPresent()){
            cuentaService.eliminar(oCuenta.get().getCuentaId());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Método privado para generar una respuesta ResponseEntity con errores de validación.
     *
     * @param result El resultado de la validación que contiene los errores.
     * @return ResponseEntity con errores de validación y estado HTTP Bad Request.
     */
    private static ResponseEntity<Map<String, String>> validarResultado(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}
