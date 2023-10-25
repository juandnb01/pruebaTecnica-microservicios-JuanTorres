package com.pruebaTecnica.msvc.clientepersona.models.entitys;

import com.pruebaTecnica.msvc.clientepersona.models.Cuenta;
import com.pruebaTecnica.msvc.clientepersona.models.Persona;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

// Autor: JuanPabloTorres
// Clase que representa a un cliente y hereda de la clase Persona
@Entity
@Table(name = "clientes")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Cliente extends Persona {

    // Identificador único del cliente
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    // Relación uno a muchos con la tabla "cliente_cuentas" para almacenar las cuentas del cliente
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cliente_id")
    private List<ClienteCuenta> clienteCuentas;

    // Lista de cuentas del cliente
    @Transient
    private List<Cuenta> cuentas;

    public Cliente() {
        clienteCuentas = new ArrayList<>();
        cuentas = new ArrayList<>();
    }

    // Contraseña del cliente
    @NotEmpty
    @Column(name = "contrasena")
    private String contrasena;

    // Estado del cliente
    @NotEmpty
    @Column(name = "estado")
    private String estado;

    //getters y setters
    public Long id() {
        return id;
    }

    public void id(Long id) {
        this.id = id;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void addClienteCuenta(ClienteCuenta clienteCuenta){
        clienteCuentas.add(clienteCuenta);
    }

    public void removeClienteCuenta(ClienteCuenta clienteCuenta){
        clienteCuentas.remove(clienteCuenta);
    }

    public List<ClienteCuenta> getClienteCuentas() {
        return clienteCuentas;
    }

    public void setClienteCuentas(List<ClienteCuenta> clienteCuentas) {
        this.clienteCuentas = clienteCuentas;
    }

    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }
}
