package com.pruebaTecnica.msvc.cuentamovimientos.services.servicesImpl;

import com.pruebaTecnica.msvc.cuentamovimientos.models.entitys.Movimiento;
import com.pruebaTecnica.msvc.cuentamovimientos.repositories.MovimientoRepository;
import com.pruebaTecnica.msvc.cuentamovimientos.services.MovientoService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Implementación de servicio para gestionar movimientos de cuentas.
 * Autor: JuanPabloTorres
 */
@Service
public class MovimientoServiceImpl implements MovientoService {

    @Autowired
    private MovimientoRepository repository;

    @PersistenceContext
    private EntityManager em;

    //Obtiene una lista de todos los movimientos de cuentas.
    @Override
    @Transactional(readOnly = true)
    public List<Movimiento> listar() {
        return (List<Movimiento>) repository.findAll();
    }

    //Obtiene los detalles de un movimiento de cuenta específico por su ID.
    @Override
    @Transactional(readOnly = true)
    public Optional<Movimiento> porId(Long id) {
        return repository.findById(id);
    }

    //Guarda un nuevo movimiento de cuenta en la base de datos o actualiza uno existente.
    @Override
    @Transactional
    public Movimiento guardar(Movimiento movimiento) {
        return repository.save(movimiento);
    }

    //Elimina un movimiento de cuenta por su ID.
    @Override
    @Transactional
    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    //Busca movimientos de cuentas por fecha y devuelve una lista de resultados.
    public List<Object[]> findMovimientosByFecha(Date fecha) {
        EntityManager entityManager = em.unwrap(EntityManager.class);

        // Formatear la fecha en el formato "AAAA-MM-DD"
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String fechaFormateada = dateFormat.format(fecha);

        String sql = "SELECT m.fecha, cl.nombre, c.numero_cuenta, c.tipo_cuenta, c.saldo_inicial, c.estado, m.valor, m.saldo " +
                "FROM db_clientes_cuentasMovimientos.movientos m " +
                "INNER JOIN db_clientes_cuentasMovimientos.cuentas c ON m.cuenta_id = c.cuenta_id " +
                "INNER JOIN db_clientes_cuentasMovimientos.clientes_cuentas cc ON c.cuenta_id = cc.cuenta_id " +
                "INNER JOIN db_clientes_cuentasMovimientos.clientes cl ON cc.cliente_id = cl.id " +
                "WHERE m.fecha = :fecha";

        Query query = entityManager.createNativeQuery(sql);

        // Establecer el parámetro formateado en la consulta
        query.setParameter("fecha", fechaFormateada);
        return query.getResultList();
    }
}
