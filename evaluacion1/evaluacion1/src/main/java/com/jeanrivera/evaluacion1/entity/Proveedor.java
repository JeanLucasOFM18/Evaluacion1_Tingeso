package com.jeanrivera.evaluacion1.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "proveedores")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Proveedor {

    @Id
    private String codigo;
    private String nombre;
    private String categoria;
    private String retencion;

}
