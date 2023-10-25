package com.pruebaTecnica.msvc.cuentamovimientos.models.entitys.DTO;

import java.util.Date;

/**
 * Clase que representa un DTO (Data Transfer Object) para reportes de movimientos de cuentas.
 * Autor: JuanPabloTorres
 */
public class MovientoCuentasReporteDTO {

    // Fecha del movimiento
    private Date fecha;

    // Nombre del cliente
    private String cliente;

    // Número de cuenta
    private Long numeroCuenta;

    // Tipo de cuenta
    private String tipo;

    // Saldo inicial
    private Long saldoInicial;

    // Estado de la cuenta
    private String estado;

    // Valor del movimiento
    private Long movimiento;

    // Saldo disponible
    private Long saldoDisponible;

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public Long getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(Long numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Long getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(Long saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(Long movimiento) {
        this.movimiento = movimiento;
    }

    public Long getSaldoDisponible() {
        return saldoDisponible;
    }

    public void setSaldoDisponible(Long saldoDisponible) {
        this.saldoDisponible = saldoDisponible;
    }
}
