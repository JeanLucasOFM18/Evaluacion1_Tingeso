package com.jeanrivera.evaluacion1.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "porcentajes")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Porcentaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String proveedor;
    private String grasa;
    private String solidototal;
}
