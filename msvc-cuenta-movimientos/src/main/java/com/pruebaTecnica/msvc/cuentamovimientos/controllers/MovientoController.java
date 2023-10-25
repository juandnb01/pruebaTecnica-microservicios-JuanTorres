package com.pruebaTecnica.msvc.cuentamovimientos.controllers;

import com.pruebaTecnica.msvc.cuentamovimientos.models.entitys.Cuenta;
import com.pruebaTecnica.msvc.cuentamovimientos.models.entitys.DTO.MovientoCuentasReporteDTO;
import com.pruebaTecnica.msvc.cuentamovimientos.models.entitys.DTO.MovimientoDTO;
import com.pruebaTecnica.msvc.cuentamovimientos.models.entitys.Movimiento;
import com.pruebaTecnica.msvc.cuentamovimientos.services.CuentaService;
import com.pruebaTecnica.msvc.cuentamovimientos.services.MovientoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Clase controladora para gestionar las operaciones relacionadas con movimientos y cuentas.
 * <p>
 * Autor: Juan Pablo Torres
 */
@RestController
@RequestMapping("/api")
public class MovientoController {

    @Autowired
    private MovientoService movientoService;

    @Autowired
    private CuentaService cuentaService;

    /**
     * Obtiene una lista de todos los movimientos.
     *
     * @return ResponseEntity con una lista de movimientos y estado HTTP OK.
     */
    @GetMapping("/movimientos")
    public ResponseEntity<List<Movimiento>> listar() {
        return ResponseEntity.ok(movientoService.listar());
    }

    /**
     * Obtiene los detalles de un movimiento por su ID.
     *
     * @param id El ID del movimiento.
     * @return ResponseEntity con el movimiento encontrado y estado HTTP OK, o estado HTTP Not Found si no se encuentra.
     */
    @GetMapping("/movimientos/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id) {
        Optional<Movimiento> oMovimiento = movientoService.porId(id);
        if (oMovimiento.isPresent()) {
            return ResponseEntity.ok(oMovimiento.get());
        }
        return ResponseEntity.notFound().build();
    }


    /**
     * Crea un nuevo movimiento.
     *
     * @param movimiento El movimiento a crear.
     * @param result     Resultado de la validación.
     * @return ResponseEntity con el movimiento creado y estado HTTP Created, o errores de validación y estado HTTP Bad Request.
     */
    @PostMapping("/movimientos")
    public ResponseEntity<?> crear(@Valid @RequestBody Movimiento movimiento, BindingResult result) {
        if (result.hasErrors()) {
            return validarResultado(result);
        }
        Movimiento movimientoDb = movientoService.guardar(movimiento);
        return ResponseEntity.status(HttpStatus.CREATED).body(movimientoDb);
    }

    /**
     * Edita un movimiento existente por su ID.
     *
     * @param movimiento El movimiento con los datos actualizados.
     * @param result     Resultado de la validación.
     * @param id         El ID del movimiento a editar.
     * @return ResponseEntity con el movimiento editado y estado HTTP Created, o errores de validación y estado HTTP Bad Request, o estado HTTP Not Found si el movimiento no existe.
     */
    @PutMapping("/movimientos/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Movimiento movimiento, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return validarResultado(result);
        }
        Optional<Movimiento> oMovimiento = movientoService.porId(id);
        if (oMovimiento.isPresent()) {
            Movimiento movimientoDb = oMovimiento.get();
            movimientoDb.setFecha(movimiento.getFecha());
            movimientoDb.setTipoMoviento(movimiento.getTipoMoviento());
            movimientoDb.setValor(movimiento.getValor());
            movimientoDb.setSaldo(movimiento.getSaldo());
            return ResponseEntity.status(HttpStatus.CREATED).body(movientoService.guardar(movimientoDb));
        }
        return ResponseEntity.notFound().build();
    }


    /**
     * Elimina un movimiento por su ID.
     *
     * @param id El ID del movimiento a eliminar.
     * @return ResponseEntity con estado HTTP No Content si el movimiento se elimina con éxito, o estado HTTP Not Found si el movimiento no existe.
     */
    @DeleteMapping("/movimientos/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Movimiento> oMovimiento = movientoService.porId(id);
        if (oMovimiento.isPresent()) {
            movientoService.eliminar(oMovimiento.get().getMovientoId());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Crea un nuevo movimiento asociado a una cuenta.
     *
     * @param movimiento El movimiento a crear.
     * @param cuentaId   El ID de la cuenta asociada al movimiento.
     * @param result     Resultado de la validación.
     * @return ResponseEntity con el movimiento creado y estado HTTP Created, o errores de validación y estado HTTP Bad Request, o estado HTTP Bad Request si el saldo no está disponible.
     */
    @PostMapping("/cuentas/{cuentaId}/movimientos")
    public ResponseEntity<?> crearMovimiento(@Valid @RequestBody MovimientoDTO movimiento, @PathVariable Long cuentaId,
                                             BindingResult result) {

        if (result.hasErrors()) {
            return validarResultado(result);
        }

        Optional<Cuenta> oCuenta = cuentaService.porId(cuentaId);
        Movimiento movimientoNew = new Movimiento();

        if (oCuenta.isPresent()) {
            Cuenta cuenta = oCuenta.get();
            // Realizar la lógica para verificar el saldo disponible y actualizarlo
            if (movimiento.getValor() < 0 && Math.abs(movimiento.getValor()) > cuenta.getSaldoInicial()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Saldo no disponible");
            }
            // Actualizar el saldo de la cuenta
            Long nuevoSaldo = cuenta.getSaldoInicial() + movimiento.getValor();
            cuenta.setSaldoInicial(nuevoSaldo);
            cuentaService.guardar(cuenta);

            movimientoNew.setFecha(movimiento.getFecha());
            movimientoNew.setTipoMoviento(movimiento.tipoMoviento);
            movimientoNew.setValor(movimiento.getValor());
            movimientoNew.setSaldo(nuevoSaldo);
            movimientoNew.setCuentaId(cuenta);


            // Guardar el movimiento
            Movimiento movimientoDb = movientoService.guardar(movimientoNew);
            return ResponseEntity.status(HttpStatus.CREATED).body(movimientoNew);
        }

        return ResponseEntity.notFound().build();
    }

    /**
     * Obtiene un informe de movimientos de cuentas en una fecha específica.
     *
     * @param fecha La fecha para la cual se desea obtener el informe de movimientos.
     * @return ResponseEntity con una lista de informe de mov
     */
    @GetMapping("/reportes/{fecha}")
    public ResponseEntity<List<MovientoCuentasReporteDTO>> movimientosPorFecha(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fecha) {
        List<Object[]> movimientos = movientoService.findMovimientosByFecha(fecha);

        if (movimientos.isEmpty()) {
            // Manejo de caso en que no se encontraron movimientos
            return ResponseEntity.notFound().build();
        }

        List<MovientoCuentasReporteDTO> movimientosDTOList = new ArrayList<>();

        for (Object[] movimiento : movimientos) {
            MovientoCuentasReporteDTO movientoCuentasReporteDTO = new MovientoCuentasReporteDTO();
            movientoCuentasReporteDTO.setFecha((Date) movimiento[0]);
            movientoCuentasReporteDTO.setCliente((String) movimiento[1]);
            movientoCuentasReporteDTO.setNumeroCuenta((Long) movimiento[2]);
            movientoCuentasReporteDTO.setTipo((String) movimiento[3]);
            movientoCuentasReporteDTO.setSaldoInicial((Long) movimiento[4]);
            movientoCuentasReporteDTO.setEstado((String) movimiento[5]);
            movientoCuentasReporteDTO.setMovimiento((Long) movimiento[6]);
            movientoCuentasReporteDTO.setSaldoDisponible((Long) movimiento[7]);

            movimientosDTOList.add(movientoCuentasReporteDTO);
        }

        return ResponseEntity.ok(movimientosDTOList);
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
