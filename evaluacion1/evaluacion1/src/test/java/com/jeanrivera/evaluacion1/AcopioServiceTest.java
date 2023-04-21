package com.jeanrivera.evaluacion1;

import com.jeanrivera.evaluacion1.entity.Acopio;
import com.jeanrivera.evaluacion1.repositories.AcopioRepository;
import com.jeanrivera.evaluacion1.services.AcopioServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AcopioServiceTest {

    @Autowired
    private AcopioServices acopioServices;

    /*
    @Test
    void findByProveedorTest(){
        Acopio acopio1 = new Acopio();
        Acopio acopio2 = new Acopio();
        String fecha1 = "2023/03/15";
        String fecha2 = "2023/03/16";
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate fecha1date = LocalDate.parse(fecha1, formato);
        LocalDate fecha2date = LocalDate.parse(fecha2, formato);
        Date fechaDate1 = Date.from(fecha1date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate2 = Date.from(fecha2date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        acopio1.setFecha(fechaDate1);
        acopio2.setFecha(fechaDate2);
        acopio1.setTurno("M");
        acopio2.setTurno("T");
        acopio1.setProveedor("01011");
        acopio2.setProveedor("00001");
        acopio1.setKilos("100");
        acopio2.setKilos("234");
        acopioServices.guardarData(acopio1);
        acopioServices.guardarData(acopio2);
        String codigo = "00001";

        List<Acopio> acopios = acopioServices.findByProveedor(codigo);
        assertEquals("00001", acopios.get(0).getProveedor());
    }*/
}
