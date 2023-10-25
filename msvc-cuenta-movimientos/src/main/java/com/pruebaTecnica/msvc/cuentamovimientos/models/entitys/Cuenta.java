package com.pruebaTecnica.msvc.cuentamovimientos.models.entitys;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

/**
 * Clase que representa una cuenta.
 * Autor: JuanPabloTorres
 */
@Entity
@Table(name = "cuentas")
public class Cuenta {

    // Identificador único de la cuenta
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cuentaId")
    private Long cuentaId;

    //numero de cuenta
    @Column(name = "numeroCuenta")
    private Long numeroCuenta;

    //Tipo de cuenta
    @NotEmpty
    @Column(name = "tipoCuenta")
    private String tipoCuenta;

    //Saldo inicial
    @Column(name = "saldoInicial")
    private Long saldoInicial;

    //Estado
    @NotEmpty
    @Column(name = "estado")
    private String estado;

    public Long getCuentaId() {
        return cuentaId;
    }

    public void setCuentaId(Long cuentaId) {
        this.cuentaId = cuentaId;
    }

    public Long getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(Long numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
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
}
