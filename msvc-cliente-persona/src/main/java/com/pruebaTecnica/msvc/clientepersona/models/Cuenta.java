package com.pruebaTecnica.msvc.clientepersona.models;

// Autor: JuanPabloTorres
// Clase que representa una cuenta de cliente
public class Cuenta {

    // Identificador único de la cuenta
    private Long cuentaId;

    // Número de cuenta
    private Long numeroCuenta;

    //Tipo de cuenta
    private String tipoCuenta;

    //Saldo Inicial
    private Long saldoInicial;

    //Estado
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
