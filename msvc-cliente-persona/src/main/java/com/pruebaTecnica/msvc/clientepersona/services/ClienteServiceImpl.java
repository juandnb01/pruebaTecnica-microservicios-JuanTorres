package com.pruebaTecnica.msvc.clientepersona.services;

import com.pruebaTecnica.msvc.clientepersona.clients.CuentaMovimientoClientRest;
import com.pruebaTecnica.msvc.clientepersona.models.Cuenta;
import com.pruebaTecnica.msvc.clientepersona.models.entitys.Cliente;
import com.pruebaTecnica.msvc.clientepersona.models.entitys.ClienteCuenta;
import com.pruebaTecnica.msvc.clientepersona.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Autor: JuanPabloTorres
 * Clase implementacion que define los métodos disponibles en el servicio de cliente
 */
@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private CuentaMovimientoClientRest clientRest;

    private AtomicLong idPersonaGenerator = new AtomicLong();

    // Método para listar todos los clientes
    @Override
    @Transactional(readOnly = true)
    public List<Cliente> listar() {
        return (List<Cliente>) repository.findAll();
    }

    // Método para buscar un cliente por su identificador único
    @Transactional(readOnly = true)
    @Override
    public Optional<Cliente> porId(Long id) {
        return repository.findById(id);
    }

    // Método para guardar un nuevo cliente
    @Override
    @Transactional
    public Cliente guardar(Cliente cliente) {
        // Si el cliente ya tiene un ID (idPersona), se trata de una actualización.
        // No es necesario validar el número de identificación en este caso.
        if (cliente.getIdPersona() != null) {
            return repository.save(cliente);
        }

        // Generar un nuevo idPersona único
        Long idPersonaNuevo = idPersonaGenerator.incrementAndGet();

        // Establecer el idPersona generado en el cliente
        cliente.setIdPersona(idPersonaNuevo);

        // Realizar la validación para asegurarse de que el número de identificación no se repita
        if (numeroIdentificacionRepetido(cliente.getIdentificacion())) {
            throw new RuntimeException("El número de identificación ya está en uso.");
        }
        return repository.save(cliente);
    }

    // Método para eliminar un cliente por su identificador único
    @Override
    @Transactional
    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    // Método para asignar una cuenta a un cliente
    @Override
    @Transactional
    public Optional<Cuenta> asignarCuenta(Cuenta cuenta, Long clienteId) {
        Optional<Cliente> o = repository.findById(clienteId);
        if (o.isPresent()) {
            Cuenta cuentaeMsv = clientRest.detalleCuenta(cuenta.getCuentaId());

            Cliente cliente = o.get();
            ClienteCuenta clienteCuenta = new ClienteCuenta();
            clienteCuenta.setCuentaId(cuentaeMsv.getCuentaId());

            cliente.addClienteCuenta(clienteCuenta);
            repository.save(cliente);
            return Optional.of(cuentaeMsv);
        }
        return Optional.empty();
    }

    // Método para crear una cuenta para un cliente
    @Override
    @Transactional
    public Optional<Cuenta> crearCuenta(Cuenta cuenta, Long clienteId) {
        Optional<Cliente> o = repository.findById(clienteId);
        if (o.isPresent()) {
            Cuenta cuentaNuevaMsv = clientRest.crearCuenta(cuenta);

            Cliente cliente = o.get();
            ClienteCuenta clienteCuenta = new ClienteCuenta();
            clienteCuenta.setCuentaId(cuentaNuevaMsv.getCuentaId());

            cliente.addClienteCuenta(clienteCuenta);
            repository.save(cliente);
            return Optional.of(cuentaNuevaMsv);
        }
        return Optional.empty();
    }

    // Método para eliminar una cuenta de un cliente
    @Override
    @Transactional
    public Optional<Cuenta> eliminarCuenta(Cuenta cuenta, Long clienteId) {
        Optional<Cliente> o = repository.findById(clienteId);
        if (o.isPresent()) {
            Cuenta cuentaeMsv = clientRest.detalleCuenta(cuenta.getCuentaId());

            Cliente cliente = o.get();
            ClienteCuenta clienteCuenta = new ClienteCuenta();
            clienteCuenta.setCuentaId(cuentaeMsv.getCuentaId());

            cliente.removeClienteCuenta(clienteCuenta);
            repository.save(cliente);
            return Optional.of(cuentaeMsv);
        }
        return Optional.empty();
    }

    // Método para verificar si el número de identificación de un cliente ya está en uso
    private boolean numeroIdentificacionRepetido(String numeroIdentificacion) {
        System.out.println(numeroIdentificacion);
        // Realiza una consulta en la base de datos para verificar si ya existe un cliente con el mismo número de identificación
        Optional<Cliente> clienteExistente = repository.findByIdentificacion(numeroIdentificacion);
        return clienteExistente.isPresent();
    }
}
