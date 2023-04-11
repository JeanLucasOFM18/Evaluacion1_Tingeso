package com.jeanrivera.evaluacion1.services;

import com.jeanrivera.evaluacion1.entity.Acopio;
import com.jeanrivera.evaluacion1.entity.Porcentaje;
import com.jeanrivera.evaluacion1.entity.Proveedor;
import com.jeanrivera.evaluacion1.repositories.AcopioRepository;
import com.jeanrivera.evaluacion1.repositories.PagosRepository;
import com.jeanrivera.evaluacion1.repositories.PorcentajeRepository;
import com.jeanrivera.evaluacion1.repositories.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class PagosService {

    private String resultado = "";

    @Autowired
    private PagosRepository pagosRepository;

    @Autowired
    private AcopioRepository acopioRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private PorcentajeRepository porcentajeRepository;

    public String pruebas(String codigo){
        List<Proveedor> proveedores = proveedorRepository.findAll();
        resultado = resultado + "El codigo es: " + codigo + "\n";
        Proveedor proveedor = proveedorRepository.findByCodigo(codigo);
        resultado = resultado + "El nombre del proveedor es: " + proveedor.getNombre() + "\n";
        List<Acopio> listado = acopioRepository.findByProveedor(codigo);
        Integer largo_lista = listado.size();
        Integer i = 0;
        Integer total_kilos = 0;
        while (i < largo_lista){
            total_kilos = total_kilos + Integer.parseInt(listado.get(i).getKilos());
            i = i + 1;
        }
        resultado = resultado + "Total de kilos de leche: " + total_kilos + "\n";
        List<Date> listado_dias = acopioRepository.findAllDistinctDates(codigo);
        resultado = resultado + "Dias que envio leche: " + listado_dias.size() + "\n";
        resultado = resultado + "Promedio de kilos de leche: " + total_kilos / listado_dias.size() + "\n";
        resultado = resultado + "Variacion leche: 0% \n";
        Porcentaje porcentaje = porcentajeRepository.findByProveedor(codigo);
        resultado = resultado + "Porcentaje grasa: " + porcentaje.getGrasa() + "\n";
        resultado = resultado + "Variacion grasa: 0% \n";
        resultado = resultado + "Porcentaje solidos totales: " + porcentaje.getSolidototal() + "\n";
        resultado = resultado + "Variacion solidos: 0% \n";
        Integer pago_leche = 0;
        if(Objects.equals(proveedor.getCategoria(), "A")){
            pago_leche = 700 * total_kilos;
        }
        else if(Objects.equals(proveedor.getCategoria(), "B")){
            pago_leche = 550 * total_kilos;
        }
        else if(Objects.equals(proveedor.getCategoria(), "C")){
            pago_leche = 400 * total_kilos;
        }
        else if(Objects.equals(proveedor.getCategoria(), "D")){
            pago_leche = 250 * total_kilos;
        }
        resultado = resultado + "Pago leche: " + pago_leche + "\n";
        Integer pago_grasa = 0;
        if(Integer.parseInt(porcentaje.getGrasa()) <= 20){
            pago_grasa = 30 * total_kilos;
        }
        else if(Integer.parseInt(porcentaje.getGrasa()) >= 21 && Integer.parseInt(porcentaje.getGrasa()) <= 45){
            pago_grasa = 80 * total_kilos;
        }
        else if(Integer.parseInt(porcentaje.getGrasa()) >= 46){
            pago_grasa = 120 * total_kilos;
        }
        resultado = resultado + "Pago grasa: " + pago_grasa + "\n";
        Integer pago_solido = 0;
        if(Integer.parseInt(porcentaje.getSolidototal()) <= 7){
            pago_solido = -130 * total_kilos;
        }
        else if(Integer.parseInt(porcentaje.getSolidototal()) >= 8 && Integer.parseInt(porcentaje.getSolidototal()) <= 18){
            pago_solido = -90 * total_kilos;
        }
        else if(Integer.parseInt(porcentaje.getSolidototal()) >= 19 && Integer.parseInt(porcentaje.getSolidototal()) <= 35){
            pago_solido = 95 * total_kilos;
        }
        else if(Integer.parseInt(porcentaje.getSolidototal()) >= 36){
            pago_solido = 150 * total_kilos;
        }
        resultado = resultado + "Pago solidos: " + pago_solido + "\n";
        List<Date> fechas = acopioRepository.findAllByFecha(codigo);
        Collections.sort(fechas);
        int cantidadFechasRepetidas = 0;
        for (i = 0; i < fechas.size() - 1; i++) {
            Date fecha1 = fechas.get(i);
            Date fecha2 = fechas.get(i + 1);
            if (fecha1.equals(fecha2)) {
                cantidadFechasRepetidas++;
                // Si quieres evitar contar la misma fecha dos veces,
                // puedes incrementar el índice i en una unidad
                i++;
            }
        }
        String beneficio = "";
        if(cantidadFechasRepetidas == 10){
            beneficio = beneficio + "20%";
        }
        resultado = resultado + "Bonificacion por frecuencia: " + beneficio + "\n";
        resultado = resultado + "Dscto por variacion leche: 0%" + "\n";
        resultado = resultado + "Dscto por variacion grasa: 0%" + "\n";
        resultado = resultado + "Dscto por variacion solidos: 0%" + "\n";
        Integer pago_acopio = pago_leche + pago_grasa + pago_solido;
        Integer bonificacion = (pago_acopio * 20) / 100;
        Integer pago_total = pago_acopio + bonificacion;
        resultado = resultado + "Pago Total: " + pago_total + "\n";
        // retencion
        // monto final
        return resultado;
    }
}
