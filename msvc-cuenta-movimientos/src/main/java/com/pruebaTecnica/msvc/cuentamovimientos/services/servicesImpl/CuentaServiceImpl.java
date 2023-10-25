package com.pruebaTecnica.msvc.cuentamovimientos.services.servicesImpl;

import com.pruebaTecnica.msvc.cuentamovimientos.models.entitys.Cuenta;
import com.pruebaTecnica.msvc.cuentamovimientos.repositories.CuentaRepository;
import com.pruebaTecnica.msvc.cuentamovimientos.services.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementación de la interfaz CuentaService que proporciona operaciones relacionadas con cuentas.
 * Autor: JuanPabloTorres
 */
@Service
public class CuentaServiceImpl implements CuentaService {

    @Autowired
    private CuentaRepository repository;

    //Obtiene una lista de todas las cuentas.
    @Override
    @Transactional(readOnly = true)
    public List<Cuenta> listar() {
        return (List<Cuenta>) repository.findAll();
    }

    //Obtiene los detalles de una cuenta específica por su ID.
    @Override
    @Transactional(readOnly = true)
    public Optional<Cuenta> porId(Long id) {
        return repository.findById(id);
    }

    //Guarda una nueva cuenta en la base de datos o actualiza una existente.
    @Override
    @Transactional
    public Cuenta guardar(Cuenta cuenta) {
        return repository.save(cuenta);
    }

    //Elimina una cuenta por su ID.
    @Override
    @Transactional
    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
