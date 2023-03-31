package com.jeanrivera.evaluacion1.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "acopios")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Acopio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String fecha;
    private String turno;
    private String proveedor;
    private String kilos;

}
