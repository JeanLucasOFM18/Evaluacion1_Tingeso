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


    public Pagos findByCodigo_proveedor(String codigo){
        return pagosRepository.findByCodigo_proveedor(codigo);
    }

    public List<Pagos> findAll(){
        return pagosRepository.findAll();
    }

    public void calculo (){
        List<Proveedor> proveedores = proveedorServices.listadoProveedores();
        Integer cantidad_proveedores = proveedores.size();
        while (i < cantidad_proveedores){
            Pagos newPago = new Pagos();
            newPago.setCodigo_proveedor(proveedores.get(i).getCodigo());
            obtenerNombre(newPago);
            System.out.println("LOOOOOOOOL: " + newPago);
            i = i + 1;
        }
    }

    public void obtenerNombre (Pagos newPago){
        Proveedor proveedor = proveedorServices.obtenerPorCodigo(newPago.getCodigo_proveedor());
        newPago.setNombre_proveedor(proveedor.getNombre());
        obtenerAcopio(newPago, proveedor);
    }

    public void obtenerAcopio (Pagos newPago, Proveedor proveedor){
        List<Acopio> listado = acopioServices.findByProveedor(newPago.getCodigo_proveedor());
        obtenerTotalKilos(newPago, listado, proveedor);
    }

    public void obtenerTotalKilos (Pagos newPago, List<Acopio> listado, Proveedor proveedor){
        Integer j = 0;
        Integer total_kilos = 0;
        Integer largo_acopio = listado.size();
        while (j < largo_acopio){
            total_kilos = total_kilos + Integer.parseInt(listado.get(j).getKilos());
            j = j + 1;
        }
        newPago.setTotalKl(total_kilos);
        obtenerDias(newPago, listado, proveedor);
    }

    public void obtenerDias (Pagos newPago, List<Acopio> listado, Proveedor proveedor){
        List<Date> listado_dias = acopioServices.findAllDistinctDates(newPago.getCodigo_proveedor());
        newPago.setDias(listado_dias.size());
        obtenerPromedioKilos(newPago, listado, listado_dias.size(), proveedor);
    }

    public void obtenerPromedioKilos (Pagos newPago, List<Acopio> listado, Integer dias, Proveedor proveedor){
        Integer promedio = newPago.getTotalKl() / dias;
        newPago.setPromedio(promedio);
        obtenerVariaciones(newPago, listado, proveedor);
    }

    public void obtenerVariaciones (Pagos newPago, List<Acopio> listado, Proveedor proveedor){
        Pagos pagoAntiguo = findByCodigo_proveedor(newPago.getCodigo_proveedor());
        Porcentaje porcentaje = porcentajeServices.findByProveedor(newPago.getCodigo_proveedor());
        newPago.setGrasa(Integer.parseInt(porcentaje.getGrasa()));
        newPago.setSolidos(Integer.parseInt(porcentaje.getSolidototal()));
        if(pagoAntiguo == null){
            newPago.setVariacion_leche(0);
            newPago.setVariacion_grasa(0);
            newPago.setVariacion_solidos(0);
        }
        else{
            Integer kilos_pasados = pagoAntiguo.getTotalKl();
            Integer grasa_pasados = pagoAntiguo.getGrasa();
            Integer solidos_pasados = pagoAntiguo.getSolidos();
            Integer variacion_leche = ((newPago.getTotalKl() - kilos_pasados) / kilos_pasados) * 100;
            Integer variacion_grasa = ((newPago.getGrasa() - grasa_pasados) / grasa_pasados) * 100;
            Integer variacion_solidos = ((newPago.getSolidos() - solidos_pasados) / solidos_pasados) * 100;
            newPago.setVariacion_leche(variacion_leche);
            newPago.setVariacion_grasa(variacion_grasa);
            newPago.setVariacion_solidos(variacion_solidos);
        }
        obtenerPagos(newPago, listado, proveedor);
    }

    public void obtenerPagos (Pagos newPago, List<Acopio> listado, Proveedor proveedor){
        Integer pago_leche = calculoPagoLeche(newPago.getTotalKl(), proveedor.getCategoria());
        Integer pago_grasa = calculoPagoGrasa(newPago.getTotalKl(), newPago.getGrasa());
        Integer pago_solidos = calculoPagoSolidos(newPago.getTotalKl(), newPago.getSolidos());
        newPago.setPago_leche(pago_leche);
        newPago.setPago_grasa(pago_grasa);
        newPago.setPago_solido(pago_solidos);
        obtenerBonificacion(newPago, proveedor);
    }

    public Integer calculoPagoLeche(Integer kilos, String categoria){
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

    public Integer calculoPagoGrasa(Integer kilos, Integer grasa){
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

    public Integer calculoPagoSolidos(Integer kilos, Integer solidos){
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

    public void obtenerBonificacion (Pagos newPago, Proveedor proveedor){
        List<Date> fechas = acopioServices.findAllByFecha(newPago.getCodigo_proveedor());
        Collections.sort(fechas);
        Integer cantidadFechasRepetidas = 0;
        Integer k = 0;
        for (k = 0; k < fechas.size() - 1; k++) {
            Date fecha1 = fechas.get(k);
            Date fecha2 = fechas.get(k + 1);
            if (fecha1.equals(fecha2)) {
                cantidadFechasRepetidas = cantidadFechasRepetidas + 1;
                k = k + 1;
            }
        }
        if(cantidadFechasRepetidas >= 10){
            newPago.setBonificacion(20);
        }
        else{
            if(acopioServices.contarTurnos(newPago.getCodigo_proveedor(), "M").size() >= 10){
                newPago.setBonificacion(12);
            }
            else if (acopioServices.contarTurnos(newPago.getCodigo_proveedor(), "T").size() >= 10){
                newPago.setBonificacion(8);
            }
            else {
                newPago.setBonificacion(0);
            }
        }
        obtenerDescuentos(newPago, proveedor);
    }

    public void obtenerDescuentos (Pagos newPago, Proveedor proveedor){
        newPago.setDescuento_varLeche(descuentoLeche(newPago.getVariacion_leche()));
        newPago.setDescuento_varGrasa(descuentoGrasa(newPago.getVariacion_grasa()));
        newPago.setDescuento_varSolidos(descuentoSolido(newPago.getVariacion_solidos()));
        obtenerPagoTotal(newPago, proveedor);
    }

    public Integer descuentoLeche (Integer variacion_leche){
        Integer variacion0a8 = 0;
        Integer variacion9a25 = 7;
        Integer variacion26a45 = 15;
        Integer variacion46oMas = 30;
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

    public Integer descuentoGrasa (Integer variacion_grasa){
        Integer variacion0a15 = 0;
        Integer variacion16a25 = 12;
        Integer variacion26a40 = 20;
        Integer variacion41oMas = 30;
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

    public Integer descuentoSolido (Integer variacion_solido){
        Integer variacion0a6 = 0;
        Integer variacion7a12 = 18;
        Integer variacion13a35 = 27;
        Integer variacion36oMas = 45;
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

    public void obtenerPagoTotal (Pagos newPago, Proveedor proveedor){
        Integer pago_acopio = newPago.getPago_leche() + newPago.getPago_grasa() + newPago.getPago_solido();
        Integer beneficio = (newPago.getBonificacion() * pago_acopio) / 100;
        pago_acopio = pago_acopio + beneficio - calcularDescuentos(newPago, pago_acopio);
        newPago.setPago_total(pago_acopio);
        obtenerMontoRetencion(newPago, proveedor);
    }

    public Integer calcularDescuentos(Pagos newPago, Integer pago_acopio){
        Integer descuento_leche = (newPago.getDescuento_varLeche() * pago_acopio) / 100;
        Integer descuento_grasa = (newPago.getDescuento_varGrasa() * pago_acopio) / 100;
        Integer descuento_solido = (newPago.getDescuento_varSolidos() * pago_acopio) / 100;
        Integer descuento_total = descuento_leche + descuento_grasa + descuento_solido;
        return descuento_total;
    }

    public void obtenerMontoRetencion(Pagos newPago, Proveedor proveedor){
        if(Objects.equals(proveedor.getRetencion(), "Si") && newPago.getPago_total() > 950000){
            Integer retencion = 13;
            Integer monto_retencion = (retencion * newPago.getPago_total()) / 100;
            newPago.setMonto_retencion(monto_retencion);
            Integer pago_final = newPago.getPago_total() - monto_retencion;
            newPago.setMonto_final(pago_final);
        }
        else{
            newPago.setMonto_retencion(0);
            newPago.setMonto_final(newPago.getPago_total());
        }
        guardarPago(newPago);
    }

    public void guardarPago (Pagos pago){
        pagosRepository.save(pago);
    }

}
