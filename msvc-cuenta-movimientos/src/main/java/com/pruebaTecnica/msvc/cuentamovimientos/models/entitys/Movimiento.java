package com.pruebaTecnica.msvc.cuentamovimientos.models.entitys;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.Date;

/**
 * Clase que representa un movimiento financiero.
 * Autor: JuanPabloTorres
 */
@Entity
@Table(name = "movientos")
public class Movimiento {

    // Identificador único del movimiento
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movientoId")
    private Long movientoId;

    //Fecha del movimiento
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;

    //Tipo de movimiento
    @NotEmpty
    @Column(name = "tipoMoviento")
    private String tipoMoviento;

    //Valor del movimiento
    @Column(name = "valor")
    private Long valor;

    //Saldo de la cuenta
    @Column(name = "saldo")
    private Long saldo;

    @ManyToOne
    @JoinColumn(name = "cuenta_id")
    private Cuenta cuentaId;

    public Long getMovientoId() {
        return movientoId;
    }

    public void setMovientoId(Long movientoId) {
        this.movientoId = movientoId;
    }

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

    public Long getSaldo() {
        return saldo;
    }

    public void setSaldo(Long saldo) {
        this.saldo = saldo;
    }

    public Cuenta getCuentaId() {
        return cuentaId;
    }

    public void setCuentaId(Cuenta cuentaId) {
        this.cuentaId = cuentaId;
    }
}
