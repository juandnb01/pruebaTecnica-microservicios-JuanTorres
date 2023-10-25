package com.pruebaTecnica.msvc.clientepersona.models.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;

// Autor: JuanPabloTorres
// Clase que representa un objeto de transferencia de datos (DTO) para representar un cliente
public class ClienteDTO {

    // Identificador único del cliente
    private Long id;

    // Nombre del cliente
    private String nombre;

    // Dirección del cliente
    private String direccion;

    // Número de teléfono del cliente
    private String telefono;

    // Contraseña del cliente
    private String contrasena;

    // Estado del cliente
    private String estado;

    // Constructor para crear un objeto ClienteDTO con todos los campos
    public ClienteDTO(Long id, String nombre, String direccion, String telefono, String contrasena, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.contrasena = contrasena;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
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
}
