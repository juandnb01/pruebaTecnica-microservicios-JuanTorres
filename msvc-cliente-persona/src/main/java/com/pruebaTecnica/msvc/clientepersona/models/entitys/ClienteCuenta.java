package com.pruebaTecnica.msvc.clientepersona.models.entitys;

import jakarta.persistence.*;

// Autor: JuanPabloTorres
// Clase que representa la relación entre clientes y cuentas en la base de datos
@Entity
@Table(name = "clientes_cuentas")
public class ClienteCuenta {

    // Identificador único de la relación cliente-cuenta
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Identificador único de la cuenta (en la tabla "cuenta")
    @Column(name="cuenta_id", unique = true)
    private Long cuentaId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCuentaId() {
        return cuentaId;
    }

    public void setCuentaId(Long cuentaId) {
        this.cuentaId = cuentaId;
    }

    // Método para comparar si dos objetos ClienteCuenta son iguales
    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return  true;
        }
        if(!(obj instanceof ClienteCuenta)){
            return false;
        }
        ClienteCuenta o = (ClienteCuenta) obj;
        return  this.cuentaId != null && this.cuentaId.equals(o.cuentaId);
    }
}
