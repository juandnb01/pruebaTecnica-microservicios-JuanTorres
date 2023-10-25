package com.pruebaTecnica.msvc.clientepersona.models;

import jakarta.persistence.MappedSuperclass;

// Autor: JuanPabloTorres
// Clase base para representar una persona
@MappedSuperclass
public class Persona {

    // Identificador único de la persona
    private Long idPersona;

    //Nombre
    private String nombre;

    //Genero
    private String genero;

    //Edad
    private int edad;

    //Identificacion
    private String identificacion;

    //Direccion
    private String direccion;

    //Telefono
    private String telefono;

    public Long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
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
}
