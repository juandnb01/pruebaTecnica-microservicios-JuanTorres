package com.pruebaTecnica.msvc.cuentamovimientos.models.entitys.DTO;

import java.util.Date;

/**
 * Clase que representa un DTO (Data Transfer Object) para información de movimientos.
 * Autor: JuanPabloTorres
 */
public class MovimientoDTO {

    // Fecha del movimiento
    public Date fecha;

    // Tipo de movimiento
    public String tipoMoviento;

    // Valor del movimiento
    public Long valor;


    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTipoMoviento() {
        return tipoMoviento;
    }

    public void setTipoMoviento(String tipoMoviento) {
        this.tipoMoviento = tipoMoviento;
    }

    public Long getValor() {
        return valor;
    }

    public void setValor(Long valor) {
        this.valor = valor;
    }
}
