package com.jeanrivera.evaluacion1.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pagos")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Pagos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String codigo_proveedor;
    private String nombre_proveedor;
    private Integer totalKl;
    private Integer dias;
    private Integer promedio;
    private Integer variacion_leche;
    private Integer grasa;
    private Integer variacion_grasa;
    private Integer solidos;
    private Integer variacion_solidos;
    private Integer pago_leche;
    private Integer pago_grasa;
    private Integer pago_solido;
    private Integer bonificacion;
    private Integer descuento_varLeche;
    private Integer descuento_varGrasa;
    private Integer descuento_varSolidos;
    private Integer pago_total;
    private Integer monto_retencion;
    private Integer monto_final;

}
