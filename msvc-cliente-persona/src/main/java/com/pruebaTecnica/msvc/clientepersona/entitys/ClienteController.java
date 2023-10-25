package com.pruebaTecnica.msvc.clientepersona.entitys;

import com.pruebaTecnica.msvc.clientepersona.models.DTO.ClienteDTO;
import com.pruebaTecnica.msvc.clientepersona.models.Cuenta;
import com.pruebaTecnica.msvc.clientepersona.models.entitys.Cliente;
import com.pruebaTecnica.msvc.clientepersona.services.ClienteService;
import feign.FeignException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

// Autor: JuanPabloTorres
// Clase controladora para gestionar las operaciones relacionadas con los clientes
@RestController
@RequestMapping("/api")
public class ClienteController {

    @Autowired
    private ClienteService service;

    // Endpoint para listar todos los clientes
    @GetMapping("/clientes")
    public List<ClienteDTO> listar() {
        List<Cliente> clientes = service.listar();

        List<ClienteDTO> clientesDTO = clientes.stream()
                .map(cliente -> new ClienteDTO(cliente.getId(), cliente.getNombre(), cliente.getDireccion(), cliente.getTelefono(), cliente.getContrasena(), cliente.getEstado()))
                .collect(Collectors.toList());

        return clientesDTO;
    }

    // Endpoint para obtener detalles de un cliente por su ID
    @GetMapping("/clientes/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id) {
        // Buscar un cliente por su ID
        Optional<Cliente> cliente = service.porId(id);
        if (cliente.isPresent()) {
            return ResponseEntity.ok().body(cliente.get());// Si se encuentra, responder con los detalles del cliente
        }
        return ResponseEntity.notFound().build();// Si no se encuentra, responder con un código de error
    }

    // Endpoint para crear un nuevo cliente
    @PostMapping("/clientes")
    public ResponseEntity<?> crear(@Valid @RequestBody Cliente cliente, BindingResult result) {
        if (result.hasErrors()) {
            return validarResultado(result);// Si hay errores de validación, responder con detalles de los errores
        }

        try {
            Cliente clienteDB = service.guardar(cliente);
            String mensaje = "Cliente creado exitosamente - Nombre: " + clienteDB.getNombre() + ", Estado: " + clienteDB.getEstado();
            return ResponseEntity.status(HttpStatus.CREATED).body(mensaje);
        } catch (RuntimeException e) {
            String mensajeError = "Error al crear el cliente: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("error", mensajeError));
        }
    }

    // Endpoint para editar un cliente existente
    @PutMapping("/clientes/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Cliente cliente, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return validarResultado(result);//Si encuentra errores de validacion lo muestra
        }
        Optional<Cliente> optionalCliente = service.porId(id);
        if (optionalCliente.isPresent()) {
            Cliente clienteDb = optionalCliente.get();
            clienteDb.setContrasena(cliente.getContrasena());
            clienteDb.setEstado(cliente.getEstado());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(clienteDb));
        }
        return ResponseEntity.notFound().build();
    }

    // Endpoint para eliminar un cliente por su ID
    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Cliente> optionalCliente = service.porId(id);
        if (optionalCliente.isPresent()) {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Endpoint para asignar una cuenta a un cliente
    @PutMapping("/clientes/asignar-cuenta/{clienteId}")
    public ResponseEntity<?> asignarCuenta(@RequestBody Cuenta cuenta, @PathVariable Long clienteId) {
        Optional<Cuenta> o;
        try {
            o = service.asignarCuenta(cuenta, clienteId);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections
                            .singletonMap("mensaje", "No existe el cliente o error en la comunicacion: " +
                                    e.getMessage()));
        }
        if (o.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    // Endpoint para crear una cuenta para un cliente
    @PostMapping("/clientes/crear-cuenta/{clienteId}")
    public ResponseEntity<?> crearCuenta(@RequestBody Cuenta cuenta, @PathVariable Long clienteId) {
        Optional<Cliente> clienteOptional = service.porId(clienteId);

        if (clienteOptional.isPresent()) {
            try {
                Optional<Cuenta> cuentaCreada = service.crearCuenta(cuenta, clienteId);

                if (cuentaCreada.isPresent()) {
                    // Obteniene el nombre del cliente
                    String nombreCliente = clienteOptional.get().getNombre();

                    // Crear un objeto JSON para el response
                    Map<String, Object> response = new HashMap<>();
                    response.put("cuentaId", cuentaCreada.get().getCuentaId());
                    response.put("numeroCuenta", cuentaCreada.get().getNumeroCuenta());
                    response.put("tipoCuenta", cuentaCreada.get().getTipoCuenta());
                    response.put("saldoInicial", cuentaCreada.get().getSaldoInicial());
                    response.put("estado", cuentaCreada.get().getEstado());
                    response.put("cliente", nombreCliente);

                    return ResponseEntity.status(HttpStatus.CREATED).body(response);
                } else {
                    return ResponseEntity.notFound().build();
                }
            } catch (FeignException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Collections.singletonMap("mensaje", "No se pudo crear una cuenta para este usuario: " + e.getMessage()));
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint para eliminar una cuenta de un cliente
    @DeleteMapping("/clientes/eliminar-cuenta/{clienteId}")
    public ResponseEntity<?> eliminarCuenta(@RequestBody Cuenta cuenta, @PathVariable Long clienteId) {
        Optional<Cuenta> o;
        try {
            o = service.eliminarCuenta(cuenta, clienteId);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections
                            .singletonMap("mensaje", "No se pudo eliminar la cuenta: " +
                                    e.getMessage()));
        }
        if (o.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(o.get());
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
