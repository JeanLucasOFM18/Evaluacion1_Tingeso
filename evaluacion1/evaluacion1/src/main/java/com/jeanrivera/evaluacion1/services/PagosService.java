package com.jeanrivera.evaluacion1.services;

import com.jeanrivera.evaluacion1.entity.Acopio;
import com.jeanrivera.evaluacion1.entity.Pagos;
import com.jeanrivera.evaluacion1.entity.Porcentaje;
import com.jeanrivera.evaluacion1.entity.Proveedor;
import com.jeanrivera.evaluacion1.repositories.AcopioRepository;
import com.jeanrivera.evaluacion1.repositories.PagosRepository;
import com.jeanrivera.evaluacion1.repositories.PorcentajeRepository;
import com.jeanrivera.evaluacion1.repositories.ProveedorRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.print.DocFlavor;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class PagosService {

    private Integer i = 0;

    @Autowired
    private PagosRepository pagosRepository;

    @Autowired
    private AcopioServices acopioServices;

    @Autowired
    private ProveedorServices proveedorServices;

    @Autowired
    private PorcentajeServices porcentajeServices;


    public List<Pagos> findByCodigo_proveedor(String codigo){
        return pagosRepository.findByCodigo_proveedor(codigo);
    }

    public List<Pagos> findAll(){
        return pagosRepository.findAll();
    }

    public void obtencionDatos(){
        List<Proveedor> proveedores = proveedorServices.listadoProveedores();
        Integer cantidad_proveedores = proveedores.size();
        while (i < cantidad_proveedores){
            Pagos newPago = new Pagos();
            newPago.setCodigo_proveedor(obtenerCodigo(proveedores, i));
            List<Pagos> pagoAntiguo = findByCodigo_proveedor(newPago.getCodigo_proveedor());
            Proveedor proveedor = proveedorServices.obtenerPorCodigo(newPago.getCodigo_proveedor());
            List<Acopio> listado = acopioServices.findByProveedor(newPago.getCodigo_proveedor());
            List<Date> listado_dias = acopioServices.findAllDistinctDates(newPago.getCodigo_proveedor());
            Porcentaje porcentaje = porcentajeServices.findByProveedor(newPago.getCodigo_proveedor());
            List<Date> fechas = acopioServices.findAllByFecha(newPago.getCodigo_proveedor());
            List<Date> turnosM = acopioServices.contarTurnos(newPago.getCodigo_proveedor(), "M");
            List<Date> turnosT = acopioServices.contarTurnos(newPago.getCodigo_proveedor(), "T");
            newPago.setNombre_proveedor(obtenerNombre(proveedor));
            newPago.setTotalKl(obtenerTotalKilos(listado));
            newPago.setDias(obtenerDias(listado_dias));
            newPago.setPromedio(obtenerPromedioKilos(newPago.getTotalKl(), newPago.getDias()));
            newPago.setVariacion_leche(obtenerVariacionLeche(pagoAntiguo, newPago.getTotalKl()));
            newPago.setGrasa(obtenerGrasa(porcentaje));
            newPago.setVariacion_grasa(obtenerVariacionGrasa(pagoAntiguo, newPago.getGrasa()));
            newPago.setSolidos(obtenerSolidos(porcentaje));
            newPago.setVariacion_solidos(obtenerVariacionSolidos(pagoAntiguo, newPago.getSolidos()));
            newPago.setPago_leche(obtenerPagoLeche(newPago.getTotalKl(), proveedor.getCategoria()));
            newPago.setPago_grasa(obtenerPagoGrasa(newPago.getTotalKl(), newPago.getGrasa()));
            newPago.setPago_solido(obtenerPagoSolidos(newPago.getTotalKl(), newPago.getSolidos()));
            newPago.setBonificacion(obtenerBonificacion(fechas, turnosM, turnosT));
            newPago.setDescuento_varLeche(obtenerDescuentoLeche(newPago.getVariacion_leche()));
            newPago.setDescuento_varGrasa(obtenerDescuentoGrasa(newPago.getVariacion_grasa()));
            newPago.setDescuento_varSolidos(obtenerDescuentoSolido(newPago.getVariacion_solidos()));
            newPago.setPago_total(obtenerPagoTotal(newPago));
            newPago.setMonto_retencion(obtenerMontoRetencion(newPago.getPago_total(), proveedor));
            newPago.setMonto_final(obtenerMontoFinal(newPago.getPago_total(), newPago.getMonto_retencion()));
            guardarPago(newPago);
            i = i + 1;
        }
    }

    public String obtenerCodigo (List<Proveedor> proveedores, Integer posicion){
        return proveedores.get(posicion).getCodigo();
    }

    public String obtenerNombre (Proveedor proveedor){
        return proveedor.getNombre();
    }

    public Integer obtenerTotalKilos (List<Acopio> listado){
        Integer j = 0;
        Integer total_kilos = 0;
        Integer largo_acopio = listado.size();
        while (j < largo_acopio){
            total_kilos = total_kilos + Integer.parseInt(listado.get(j).getKilos());
            j = j + 1;
        }
        return total_kilos;
    }

    public Integer obtenerDias (List<Date> listado_dias){
        return listado_dias.size();
    }

    public Integer obtenerPromedioKilos (Integer totalKl, Integer dias){
        return totalKl / dias;
    }

    public Integer obtenerGrasa (Porcentaje porcentaje){
        return Integer.parseInt(porcentaje.getGrasa());
    }

    public Integer obtenerSolidos (Porcentaje porcentaje){
        return Integer.parseInt(porcentaje.getSolidototal());
    }

    public Integer obtenerVariacionLeche (List<Pagos> pagoAntiguo, Integer totalKl){
        if(pagoAntiguo.size() == 0){
            return 0;
        }
        else{
            Integer largo_pagos = pagoAntiguo.size() - 1;
            Integer kilos_pasados = pagoAntiguo.get(largo_pagos).getTotalKl();
            Integer variacion_leche = kilos_pasados - totalKl;
            Double variacion = (double) variacion_leche / totalKl;
            variacion_leche = (int) (variacion * 100);
            return variacion_leche;
        }
    }

    public Integer obtenerVariacionGrasa (List<Pagos> pagoAntiguo, Integer grasa){
        if(pagoAntiguo.size() == 0){
            return 0;
        }
        else{
            Integer largo_pagos = pagoAntiguo.size() - 1;
            Integer grasa_pasados = pagoAntiguo.get(largo_pagos).getGrasa();
            Integer variacion_grasa = grasa_pasados - grasa;
            Double variacion = (double) variacion_grasa / grasa;
            variacion_grasa = (int) (variacion * 100);
            return variacion_grasa;
        }
    }

    public Integer obtenerVariacionSolidos (List<Pagos> pagoAntiguo, Integer solidos){
        if(pagoAntiguo.size() == 0){
            return 0;
        }
        else{
            Integer largo_pagos = pagoAntiguo.size() - 1;
            Integer solidos_pasados = pagoAntiguo.get(largo_pagos).getSolidos();
            Integer variacion_solidos = solidos_pasados - solidos;
            Double variacion = (double) variacion_solidos / solidos;
            variacion_solidos = (int) (variacion * 100);
            return variacion_solidos;
        }
    }

    public Integer obtenerPagoLeche(Integer kilos, String categoria){
        Integer categoriaA = 700;
        Integer categoriaB = 550;
        Integer categoriaC = 400;
        Integer categoriaD = 250;
        if(Objects.equals(categoria, "A")){
            return kilos * categoriaA;
        }
        else if(Objects.equals(categoria, "B")){
            return kilos * categoriaB;
        }
        else if(Objects.equals(categoria, "C")){
            return kilos * categoriaC;
        }
        else{
            return kilos * categoriaD;
        }
    }

    public Integer obtenerPagoGrasa(Integer kilos, Integer grasa){
        Integer grasa0a20 = 30;
        Integer grasa21a45 = 80;
        Integer grasa46oMas = 120;
        if(grasa >= 0 && grasa <= 20){
            return kilos * grasa0a20;
        }
        else if(grasa >= 21 && grasa <= 45){
            return kilos * grasa21a45;
        }
        else{
            return kilos * grasa46oMas;
        }
    }

    public Integer obtenerPagoSolidos(Integer kilos, Integer solidos){
        Integer solido0a7 = -130;
        Integer solido8a18 = -90;
        Integer solido19a35 = 95;
        Integer solido36oMas = 150;
        if(solidos >= 0 && solidos <= 7){
            return kilos * solido0a7;
        }
        else if(solidos >= 8 && solidos <= 18){
            return kilos * solido8a18;
        }
        else if(solidos >= 19 && solidos <= 35){
            return kilos * solido19a35;
        }
        else{
            return kilos * solido36oMas;
        }
    }

    public Integer obtenerBonificacion (List<Date> fechas, List<Date> turnosM, List<Date> turnosT){
        Collections.sort(fechas);
        Integer cantidadFechasRepetidas = 0;
        Integer k = 0;
        Integer MyT = 20;
        Integer soloM = 12;
        Integer soloT = 8;
        for (k = 0; k < fechas.size() - 1; k++) {
            Date fecha1 = fechas.get(k);
            Date fecha2 = fechas.get(k + 1);
            if (fecha1.equals(fecha2)) {
                cantidadFechasRepetidas = cantidadFechasRepetidas + 1;
                k = k + 1;
            }
        }
        if(cantidadFechasRepetidas >= 10){
           return MyT;
        }
        else{
            if(turnosM.size() >= 10){
                return soloM;
            }
            else if (turnosT.size() >= 10){
                return soloT;
            }
            else {
                return 0;
            }
        }
    }

    public Integer obtenerDescuentoLeche (Integer variacion_leche){
        Integer variacion0a8 = 0;
        Integer variacion9a25 = 7;
        Integer variacion26a45 = 15;
        Integer variacion46oMas = 30;
        if(variacion_leche > 0){
            return variacion0a8;
        }
        else{
            variacion_leche = variacion_leche * -1;
            if(variacion_leche >= 0 && variacion_leche <= 8){
                return variacion0a8;
            }
            else if(variacion_leche >= 9 && variacion_leche <= 25){
                return variacion9a25;
            }
            else if(variacion_leche >= 26 && variacion_leche <= 45){
                return variacion26a45;
            }
            else{
                return variacion46oMas;
            }
        }
    }

    public Integer obtenerDescuentoGrasa (Integer variacion_grasa){
        Integer variacion0a15 = 0;
        Integer variacion16a25 = 12;
        Integer variacion26a40 = 20;
        Integer variacion41oMas = 30;
        if(variacion_grasa > 0){
            return variacion0a15;
        }
        else {
            variacion_grasa = variacion_grasa * -1;
            if(variacion_grasa >= 0 && variacion_grasa <= 15){
                return variacion0a15;
            }
            else if(variacion_grasa >= 16 && variacion_grasa <= 25){
                return variacion16a25;
            }
            else if(variacion_grasa >= 26 && variacion_grasa <= 40){
                return variacion26a40;
            }
            else{
                return variacion41oMas;
            }
        }
    }

    public Integer obtenerDescuentoSolido (Integer variacion_solido){
        Integer variacion0a6 = 0;
        Integer variacion7a12 = 18;
        Integer variacion13a35 = 27;
        Integer variacion36oMas = 45;
        if(variacion_solido > 0){
            return variacion0a6;
        }
        else {
            variacion_solido = variacion_solido * -1;
            if(variacion_solido >= 0 && variacion_solido <= 6){
                return variacion0a6;
            }
            else if(variacion_solido >= 7 && variacion_solido <= 12){
                return variacion7a12;
            }
            else if(variacion_solido >= 13 && variacion_solido <= 35){
                return variacion13a35;
            }
            else{
                return variacion36oMas;
            }
        }
    }

    public Integer obtenerPagoTotal (Pagos newPago){
        Integer pago_acopio = newPago.getPago_leche() + newPago.getPago_grasa() + newPago.getPago_solido();
        Integer beneficio = (newPago.getBonificacion() * pago_acopio) / 100;
        pago_acopio = pago_acopio + beneficio;
        Integer pago_total = pago_acopio - calcularDescuentos(newPago, pago_acopio);
        return pago_total;
    }

    public Integer calcularDescuentos(Pagos newPago, Integer pago_acopio){
        Integer descuento_leche = (newPago.getDescuento_varLeche() * pago_acopio) / 100;
        Integer descuento_grasa = (newPago.getDescuento_varGrasa() * pago_acopio) / 100;
        Integer descuento_solido = (newPago.getDescuento_varSolidos() * pago_acopio) / 100;
        Integer descuento_total = descuento_leche + descuento_grasa + descuento_solido;
        return descuento_total;
    }

    public Integer obtenerMontoRetencion(Integer pago_total, Proveedor proveedor){
        if(Objects.equals(proveedor.getRetencion(), "Si") && pago_total > 950000){
            Integer retencion = 13;
            Integer monto_retencion = (retencion * pago_total) / 100;
            return monto_retencion;
        }
        else{
            return 0;
        }
    }

    public Integer obtenerMontoFinal(Integer pago_total, Integer monto_retencion){
        return pago_total - monto_retencion;
    }

    public void guardarPago (Pagos pago){
        pagosRepository.save(pago);
    }
}
