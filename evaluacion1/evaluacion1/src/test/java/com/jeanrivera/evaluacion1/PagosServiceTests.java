package com.jeanrivera.evaluacion1;

import com.jeanrivera.evaluacion1.entity.Acopio;
import com.jeanrivera.evaluacion1.entity.Pagos;
import com.jeanrivera.evaluacion1.entity.Porcentaje;
import com.jeanrivera.evaluacion1.entity.Proveedor;
import com.jeanrivera.evaluacion1.services.PagosService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PagosServiceTests {

    Proveedor proveedor = new Proveedor();
    PagosService pagosService = new PagosService();

    @Test
    void obtenerCodigoTest() {
        Proveedor proveedor2 = new Proveedor();
        proveedor.setCodigo("00001");
        proveedor2.setCodigo("00002");
        proveedor.setNombre("Jose Fernandez");
        proveedor2.setNombre("Ignacio Soto");
        proveedor.setCategoria("A");
        proveedor2.setCategoria("C");
        proveedor.setRetencion("Si");
        proveedor2.setRetencion("No");
        List<Proveedor> proveedores = Arrays.asList(proveedor, proveedor2);

        String codigo = pagosService.obtenerCodigo(proveedores, 1);
        assertEquals("00002", codigo);
    }

    @Test
    void obtenerNombreTest() {
        proveedor.setCodigo("00001");
        proveedor.setNombre("Jose Fernandez");
        proveedor.setCategoria("A");
        proveedor.setRetencion("Si");

        String nombre = pagosService.obtenerNombre(proveedor);
        assertEquals("Jose Fernandez", nombre);
    }

    @Test
    void obtenerTotalKilosTest() {
        String fecha1 = "2023/03/15";
        String fecha2 = "2023/03/18";
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate fecha1date = LocalDate.parse(fecha1, formato);
        LocalDate fecha2date = LocalDate.parse(fecha2, formato);
        Date fechaDate1 = Date.from(fecha1date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate2 = Date.from(fecha2date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

        Acopio acopio = new Acopio();
        Acopio acopio2 = new Acopio();
        acopio.setId(0);
        acopio.setFecha(fechaDate1);
        acopio.setTurno("M");
        acopio.setProveedor("00001");
        acopio.setKilos("45");
        acopio2.setId(1);
        acopio2.setFecha(fechaDate2);
        acopio2.setTurno("T");
        acopio2.setProveedor("00001");
        acopio2.setKilos("55");

        List<Acopio> acopios = Arrays.asList(acopio, acopio2);
        Integer total_kilos = pagosService.obtenerTotalKilos(acopios);
        assertEquals(100, total_kilos);
    }

    @Test
    void obtenerDiasTest() {
        String fecha1 = "2023/03/15";
        String fecha2 = "2023/03/18";
        String fecha3 = "2023/03/20";
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate fecha1date = LocalDate.parse(fecha1, formato);
        LocalDate fecha2date = LocalDate.parse(fecha2, formato);
        LocalDate fecha3date = LocalDate.parse(fecha3, formato);
        Date fechaDate1 = Date.from(fecha1date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate2 = Date.from(fecha2date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate3 = Date.from(fecha3date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

        List<Date> listado_dias = Arrays.asList(fechaDate1, fechaDate2,fechaDate3);
        Integer dias = pagosService.obtenerDias(listado_dias);
        assertEquals(3, dias);
    }

    @Test
    void obtenerPromedioKilosTest() {
        Integer total_kilos = 100;
        Integer dias = 3;

        Integer promedio = pagosService.obtenerPromedioKilos(total_kilos, dias);
        assertEquals(33, promedio);
    }

    @Test
    void obtenerGrasaTest() {
        Porcentaje porcentaje = new Porcentaje();
        porcentaje.setId(0);
        porcentaje.setProveedor("00001");
        porcentaje.setGrasa("23");
        porcentaje.setSolidototal("14");

        Integer grasa = pagosService.obtenerGrasa(porcentaje);
        assertEquals(23, grasa);
    }

    @Test
    void obtenerSolidosTest() {
        Porcentaje porcentaje = new Porcentaje();
        porcentaje.setId(0);
        porcentaje.setProveedor("00001");
        porcentaje.setGrasa("23");
        porcentaje.setSolidototal("14");

        Integer solidos = pagosService.obtenerSolidos(porcentaje);
        assertEquals(14, solidos);
    }

    @Test
    void obtenerVariacionLecheNuevoProveedorTest() {
        List<Pagos> pagoAntiguo = new ArrayList<>();
        Integer total_kilos = 100;

        Integer variacion_leche = pagosService.obtenerVariacionLeche(pagoAntiguo, total_kilos);
        assertEquals(0, variacion_leche);
    }

    @Test
    void obtenerVariacionLecheAntiguoProveedorTest() {
        List<Pagos> pagosAntiguos = new ArrayList<>();
        Pagos pagoAntiguo = new Pagos();
        Pagos pagoAntiguo2 = new Pagos();
        pagoAntiguo.setTotalKl(80);
        pagoAntiguo2.setTotalKl(50);
        pagosAntiguos.add(pagoAntiguo);
        pagosAntiguos.add(pagoAntiguo2);
        Integer total_kilos = 100;

        Integer variacion_leche = pagosService.obtenerVariacionLeche(pagosAntiguos, total_kilos);
        assertEquals(-50, variacion_leche);
    }

    @Test
    void obtenerVariacionGrasaNuevoProveedorTest() {
        List<Pagos> pagoAntiguo = new ArrayList<>();
        Integer grasa = 25;

        Integer variacion_grasa = pagosService.obtenerVariacionGrasa(pagoAntiguo, grasa);
        assertEquals(0, variacion_grasa);
    }

    @Test
    void obtenerVariacionGrasaAntiguoProveedorTest() {
        List<Pagos> pagosAntiguos = new ArrayList<>();
        Pagos pagoAntiguo = new Pagos();
        Pagos pagoAntiguo2 = new Pagos();
        pagoAntiguo.setGrasa(10);
        pagoAntiguo2.setGrasa(22);
        pagosAntiguos.add(pagoAntiguo);
        pagosAntiguos.add(pagoAntiguo2);
        Integer grasa = 15;

        Integer variacion_grasa = pagosService.obtenerVariacionGrasa(pagosAntiguos, grasa);
        assertEquals(46, variacion_grasa);
    }

    @Test
    void obtenerVariacionSolidosNuevoProveedorTest() {
        List<Pagos> pagosAntiguos = new ArrayList<>();
        Integer solido = 25;

        Integer variacion_solido = pagosService.obtenerVariacionSolidos(pagosAntiguos, solido);
        assertEquals(0, variacion_solido);
    }

    @Test
    void obtenerVariacionSolidosAntiguoProveedorTest() {
        List<Pagos> pagosAntiguos = new ArrayList<>();
        Pagos pagoAntiguo = new Pagos();
        Pagos pagoAntiguo2 = new Pagos();
        pagoAntiguo.setSolidos(18);
        pagoAntiguo2.setSolidos(22);
        pagosAntiguos.add(pagoAntiguo);
        pagosAntiguos.add(pagoAntiguo2);
        Integer solido = 25;

        Integer variacion_solido = pagosService.obtenerVariacionSolidos(pagosAntiguos, solido);
        assertEquals(-12, variacion_solido);
    }

    @Test
    void obtenerPagoLecheCategoriaATest(){
        Integer kilos = 20;
        String categoria = "A";

        Integer pagoLeche = pagosService.obtenerPagoLeche(kilos, categoria);
        assertEquals(14000, pagoLeche);
    }

    @Test
    void obtenerPagoLecheCategoriaBTest(){
        Integer kilos = 20;
        String categoria = "B";

        Integer pagoLeche = pagosService.obtenerPagoLeche(kilos, categoria);
        assertEquals(11000, pagoLeche);
    }

    @Test
    void obtenerPagoLecheCategoriaCTest(){
        Integer kilos = 20;
        String categoria = "C";

        Integer pagoLeche = pagosService.obtenerPagoLeche(kilos, categoria);
        assertEquals(8000, pagoLeche);
    }

    @Test
    void obtenerPagoLecheCategoriaDTest(){
        Integer kilos = 20;
        String categoria = "D";

        Integer pagoLeche = pagosService.obtenerPagoLeche(kilos, categoria);
        assertEquals(5000, pagoLeche);
    }

    @Test
    void obtenerPagoGrasa0a20Test(){
        Integer kilos = 20;
        Integer grasa = 14;

        Integer pagoGrasa = pagosService.obtenerPagoGrasa(kilos, grasa);
        assertEquals(600, pagoGrasa);
    }

    @Test
    void obtenerPagoGrasa21a45Test(){
        Integer kilos = 20;
        Integer grasa = 24;

        Integer pagoGrasa = pagosService.obtenerPagoGrasa(kilos, grasa);
        assertEquals(1600, pagoGrasa);
    }

    @Test
    void obtenerPagoGrasa46aMasTest(){
        Integer kilos = 20;
        Integer grasa = 48;

        Integer pagoGrasa = pagosService.obtenerPagoGrasa(kilos, grasa);
        assertEquals(2400, pagoGrasa);
    }

    @Test
    void obtenerPagoSolido0a7Test(){
        Integer kilos = 20;
        Integer solido = 5;

        Integer pagoSolido = pagosService.obtenerPagoSolidos(kilos, solido);
        assertEquals(-2600, pagoSolido);
    }

    @Test
    void obtenerPagoSolido8a18Test(){
        Integer kilos = 20;
        Integer solido = 15;

        Integer pagoSolido = pagosService.obtenerPagoSolidos(kilos, solido);
        assertEquals(-1800, pagoSolido);
    }

    @Test
    void obtenerPagoSolido19a35Test(){
        Integer kilos = 20;
        Integer solido = 25;

        Integer pagoSolido = pagosService.obtenerPagoSolidos(kilos, solido);
        assertEquals(1900, pagoSolido);
    }

    @Test
    void obtenerPagoSolido36aMasTest(){
        Integer kilos = 20;
        Integer solido = 45;

        Integer pagoSolido = pagosService.obtenerPagoSolidos(kilos, solido);
        assertEquals(3000, pagoSolido);
    }

    @Test
    void obtenerBonificacionTurnoMyTTest(){
        List<Date> fechas = new ArrayList<>();
        List<Date> turnosM = new ArrayList<>();
        List<Date> turnosT = new ArrayList<>();
        String fecha1 = "2023/03/15";
        String fecha1_ = "2023/03/15";
        String fecha2 = "2023/03/16";
        String fecha2_ = "2023/03/16";
        String fecha3 = "2023/03/17";
        String fecha3_ = "2023/03/17";
        String fecha4 = "2023/03/18";
        String fecha4_ = "2023/03/18";
        String fecha5 = "2023/03/19";
        String fecha5_ = "2023/03/19";
        String fecha6 = "2023/03/20";
        String fecha6_ = "2023/03/20";
        String fecha7 = "2023/03/21";
        String fecha7_ = "2023/03/21";
        String fecha8 = "2023/03/22";
        String fecha8_ = "2023/03/22";
        String fecha9 = "2023/03/23";
        String fecha9_ = "2023/03/23";
        String fecha10 = "2023/03/24";
        String fecha10_ = "2023/03/24";
        String fecha11 = "2023/03/25";
        String fecha11_ = "2023/03/25";
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate fecha1date = LocalDate.parse(fecha1, formato);
        LocalDate fecha2date = LocalDate.parse(fecha2, formato);
        LocalDate fecha3date = LocalDate.parse(fecha3, formato);
        LocalDate fecha4date = LocalDate.parse(fecha4, formato);
        LocalDate fecha5date = LocalDate.parse(fecha5, formato);
        LocalDate fecha6date = LocalDate.parse(fecha6, formato);
        LocalDate fecha7date = LocalDate.parse(fecha7, formato);
        LocalDate fecha8date = LocalDate.parse(fecha8, formato);
        LocalDate fecha9date = LocalDate.parse(fecha9, formato);
        LocalDate fecha10date = LocalDate.parse(fecha10, formato);
        LocalDate fecha11date = LocalDate.parse(fecha11, formato);
        LocalDate fecha1_date = LocalDate.parse(fecha1_, formato);
        LocalDate fecha2_date = LocalDate.parse(fecha2_, formato);
        LocalDate fecha3_date = LocalDate.parse(fecha3_, formato);
        LocalDate fecha4_date = LocalDate.parse(fecha4_, formato);
        LocalDate fecha5_date = LocalDate.parse(fecha5_, formato);
        LocalDate fecha6_date = LocalDate.parse(fecha6_, formato);
        LocalDate fecha7_date = LocalDate.parse(fecha7_, formato);
        LocalDate fecha8_date = LocalDate.parse(fecha8_, formato);
        LocalDate fecha9_date = LocalDate.parse(fecha9_, formato);
        LocalDate fecha10_date = LocalDate.parse(fecha10_, formato);
        LocalDate fecha11_date = LocalDate.parse(fecha11_, formato);
        Date fechaDate1 = Date.from(fecha1date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate2 = Date.from(fecha2date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate3 = Date.from(fecha3date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate4 = Date.from(fecha4date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate5 = Date.from(fecha5date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate6 = Date.from(fecha6date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate7 = Date.from(fecha7date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate8 = Date.from(fecha8date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate9 = Date.from(fecha9date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate10 = Date.from(fecha10date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate11 = Date.from(fecha11date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate1_ = Date.from(fecha1_date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate2_ = Date.from(fecha2_date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate3_ = Date.from(fecha3_date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate4_ = Date.from(fecha4_date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate5_ = Date.from(fecha5_date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate6_ = Date.from(fecha6_date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate7_ = Date.from(fecha7_date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate8_ = Date.from(fecha8_date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate9_ = Date.from(fecha9_date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate10_ = Date.from(fecha10_date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate11_ = Date.from(fecha11_date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        fechas.add(fechaDate1);
        fechas.add(fechaDate1_);
        fechas.add(fechaDate2);
        fechas.add(fechaDate2_);
        fechas.add(fechaDate3);
        fechas.add(fechaDate3_);
        fechas.add(fechaDate4);
        fechas.add(fechaDate4_);
        fechas.add(fechaDate5);
        fechas.add(fechaDate5_);
        fechas.add(fechaDate6);
        fechas.add(fechaDate6_);
        fechas.add(fechaDate7);
        fechas.add(fechaDate7_);
        fechas.add(fechaDate8);
        fechas.add(fechaDate8_);
        fechas.add(fechaDate9);
        fechas.add(fechaDate9_);
        fechas.add(fechaDate10);
        fechas.add(fechaDate10_);
        fechas.add(fechaDate11);
        fechas.add(fechaDate11_);
        turnosM.add(fechaDate1);
        turnosT.add(fechaDate1_);
        turnosM.add(fechaDate2);
        turnosT.add(fechaDate2_);
        turnosM.add(fechaDate3);
        turnosT.add(fechaDate3_);
        turnosM.add(fechaDate4);
        turnosT.add(fechaDate4_);
        turnosM.add(fechaDate5);
        turnosT.add(fechaDate5_);
        turnosM.add(fechaDate6);
        turnosT.add(fechaDate6_);
        turnosM.add(fechaDate7);
        turnosT.add(fechaDate7_);
        turnosM.add(fechaDate8);
        turnosT.add(fechaDate8_);
        turnosM.add(fechaDate9);
        turnosT.add(fechaDate9_);
        turnosM.add(fechaDate10);
        turnosT.add(fechaDate10_);
        turnosM.add(fechaDate11);
        turnosT.add(fechaDate11_);

        Integer bonificacion = pagosService.obtenerBonificacion(fechas, turnosM, turnosT);
        assertEquals(20, bonificacion);
    }

    @Test
    void obtenerBonificacionTurnoSoloMTest(){
        List<Date> fechas = new ArrayList<>();
        List<Date> turnosM = new ArrayList<>();
        List<Date> turnosT = new ArrayList<>();
        String fecha1 = "2023/03/15";
        String fecha2 = "2023/03/16";
        String fecha2_ = "2023/03/16";
        String fecha3 = "2023/03/17";
        String fecha3_ = "2023/03/17";
        String fecha4 = "2023/03/18";
        String fecha5 = "2023/03/19";
        String fecha5_ = "2023/03/19";
        String fecha6 = "2023/03/20";
        String fecha7 = "2023/03/21";
        String fecha7_ = "2023/03/21";
        String fecha8 = "2023/03/22";
        String fecha8_ = "2023/03/22";
        String fecha9 = "2023/03/23";
        String fecha10 = "2023/03/24";
        String fecha10_ = "2023/03/24";
        String fecha11 = "2023/03/25";
        String fecha11_ = "2023/03/25";
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate fecha1date = LocalDate.parse(fecha1, formato);
        LocalDate fecha2date = LocalDate.parse(fecha2, formato);
        LocalDate fecha3date = LocalDate.parse(fecha3, formato);
        LocalDate fecha4date = LocalDate.parse(fecha4, formato);
        LocalDate fecha5date = LocalDate.parse(fecha5, formato);
        LocalDate fecha6date = LocalDate.parse(fecha6, formato);
        LocalDate fecha7date = LocalDate.parse(fecha7, formato);
        LocalDate fecha8date = LocalDate.parse(fecha8, formato);
        LocalDate fecha9date = LocalDate.parse(fecha9, formato);
        LocalDate fecha10date = LocalDate.parse(fecha10, formato);
        LocalDate fecha11date = LocalDate.parse(fecha11, formato);
        LocalDate fecha2_date = LocalDate.parse(fecha2_, formato);
        LocalDate fecha3_date = LocalDate.parse(fecha3_, formato);
        LocalDate fecha5_date = LocalDate.parse(fecha5_, formato);
        LocalDate fecha7_date = LocalDate.parse(fecha7_, formato);
        LocalDate fecha8_date = LocalDate.parse(fecha8_, formato);
        LocalDate fecha10_date = LocalDate.parse(fecha10_, formato);
        LocalDate fecha11_date = LocalDate.parse(fecha11_, formato);
        Date fechaDate1 = Date.from(fecha1date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate2 = Date.from(fecha2date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate3 = Date.from(fecha3date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate4 = Date.from(fecha4date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate5 = Date.from(fecha5date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate6 = Date.from(fecha6date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate7 = Date.from(fecha7date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate8 = Date.from(fecha8date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate9 = Date.from(fecha9date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate10 = Date.from(fecha10date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate11 = Date.from(fecha11date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate2_ = Date.from(fecha2_date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate3_ = Date.from(fecha3_date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate5_ = Date.from(fecha5_date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate7_ = Date.from(fecha7_date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate8_ = Date.from(fecha8_date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate10_ = Date.from(fecha10_date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate11_ = Date.from(fecha11_date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        fechas.add(fechaDate1);
        fechas.add(fechaDate2);
        fechas.add(fechaDate2_);
        fechas.add(fechaDate3);
        fechas.add(fechaDate3_);
        fechas.add(fechaDate4);
        fechas.add(fechaDate5);
        fechas.add(fechaDate5_);
        fechas.add(fechaDate6);
        fechas.add(fechaDate7);
        fechas.add(fechaDate7_);
        fechas.add(fechaDate8);
        fechas.add(fechaDate8_);
        fechas.add(fechaDate9);
        fechas.add(fechaDate10);
        fechas.add(fechaDate10_);
        fechas.add(fechaDate11);
        fechas.add(fechaDate11_);
        turnosM.add(fechaDate1);
        turnosM.add(fechaDate2);
        turnosT.add(fechaDate2_);
        turnosM.add(fechaDate3);
        turnosT.add(fechaDate3_);
        turnosM.add(fechaDate4);
        turnosM.add(fechaDate5);
        turnosT.add(fechaDate5_);
        turnosM.add(fechaDate6);
        turnosM.add(fechaDate7);
        turnosT.add(fechaDate7_);
        turnosM.add(fechaDate8);
        turnosT.add(fechaDate8_);
        turnosM.add(fechaDate9);
        turnosM.add(fechaDate10);
        turnosT.add(fechaDate10_);
        turnosM.add(fechaDate11);
        turnosT.add(fechaDate11_);

        Integer bonificacion = pagosService.obtenerBonificacion(fechas, turnosM, turnosT);
        assertEquals(12, bonificacion);
    }

    @Test
    void obtenerBonificacionTurnoSoloTTest(){
        List<Date> fechas = new ArrayList<>();
        List<Date> turnosM = new ArrayList<>();
        List<Date> turnosT = new ArrayList<>();
        String fecha1 = "2023/03/15";
        String fecha1_ = "2023/03/15";
        String fecha2_ = "2023/03/16";
        String fecha3 = "2023/03/17";
        String fecha3_ = "2023/03/17";
        String fecha4 = "2023/03/18";
        String fecha4_ = "2023/03/18";
        String fecha5_ = "2023/03/19";
        String fecha6 = "2023/03/20";
        String fecha6_ = "2023/03/20";
        String fecha7_ = "2023/03/21";
        String fecha8_ = "2023/03/22";
        String fecha9 = "2023/03/23";
        String fecha9_ = "2023/03/23";
        String fecha10 = "2023/03/24";
        String fecha10_ = "2023/03/24";
        String fecha11 = "2023/03/25";
        String fecha11_ = "2023/03/25";
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate fecha1date = LocalDate.parse(fecha1, formato);
        LocalDate fecha3date = LocalDate.parse(fecha3, formato);
        LocalDate fecha4date = LocalDate.parse(fecha4, formato);
        LocalDate fecha6date = LocalDate.parse(fecha6, formato);
        LocalDate fecha9date = LocalDate.parse(fecha9, formato);
        LocalDate fecha10date = LocalDate.parse(fecha10, formato);
        LocalDate fecha11date = LocalDate.parse(fecha11, formato);
        LocalDate fecha1_date = LocalDate.parse(fecha1_, formato);
        LocalDate fecha2_date = LocalDate.parse(fecha2_, formato);
        LocalDate fecha3_date = LocalDate.parse(fecha3_, formato);
        LocalDate fecha4_date = LocalDate.parse(fecha4_, formato);
        LocalDate fecha5_date = LocalDate.parse(fecha5_, formato);
        LocalDate fecha6_date = LocalDate.parse(fecha6_, formato);
        LocalDate fecha7_date = LocalDate.parse(fecha7_, formato);
        LocalDate fecha8_date = LocalDate.parse(fecha8_, formato);
        LocalDate fecha9_date = LocalDate.parse(fecha9_, formato);
        LocalDate fecha10_date = LocalDate.parse(fecha10_, formato);
        LocalDate fecha11_date = LocalDate.parse(fecha11_, formato);
        Date fechaDate1 = Date.from(fecha1date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate3 = Date.from(fecha3date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate4 = Date.from(fecha4date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate6 = Date.from(fecha6date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate9 = Date.from(fecha9date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate10 = Date.from(fecha10date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate11 = Date.from(fecha11date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate1_ = Date.from(fecha1_date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate2_ = Date.from(fecha2_date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate3_ = Date.from(fecha3_date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate4_ = Date.from(fecha4_date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate5_ = Date.from(fecha5_date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate6_ = Date.from(fecha6_date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate7_ = Date.from(fecha7_date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate8_ = Date.from(fecha8_date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate9_ = Date.from(fecha9_date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate10_ = Date.from(fecha10_date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate11_ = Date.from(fecha11_date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        fechas.add(fechaDate1);
        fechas.add(fechaDate1_);
        fechas.add(fechaDate2_);
        fechas.add(fechaDate3);
        fechas.add(fechaDate3_);
        fechas.add(fechaDate4);
        fechas.add(fechaDate4_);
        fechas.add(fechaDate5_);
        fechas.add(fechaDate6);
        fechas.add(fechaDate6_);
        fechas.add(fechaDate7_);
        fechas.add(fechaDate8_);
        fechas.add(fechaDate9);
        fechas.add(fechaDate9_);
        fechas.add(fechaDate10);
        fechas.add(fechaDate10_);
        fechas.add(fechaDate11);
        fechas.add(fechaDate11_);
        turnosM.add(fechaDate1);
        turnosT.add(fechaDate1_);
        turnosT.add(fechaDate2_);
        turnosM.add(fechaDate3);
        turnosT.add(fechaDate3_);
        turnosM.add(fechaDate4);
        turnosT.add(fechaDate4_);
        turnosT.add(fechaDate5_);
        turnosM.add(fechaDate6);
        turnosT.add(fechaDate6_);
        turnosT.add(fechaDate7_);
        turnosT.add(fechaDate8_);
        turnosM.add(fechaDate9);
        turnosT.add(fechaDate9_);
        turnosM.add(fechaDate10);
        turnosT.add(fechaDate10_);
        turnosM.add(fechaDate11);
        turnosT.add(fechaDate11_);

        Integer bonificacion = pagosService.obtenerBonificacion(fechas, turnosM, turnosT);
        assertEquals(8, bonificacion);
    }

    @Test
    void obtenerBonificacionSinBoniTest(){
        List<Date> fechas = new ArrayList<>();
        List<Date> turnosM = new ArrayList<>();
        List<Date> turnosT = new ArrayList<>();
        String fecha1 = "2023/03/15";
        String fecha1_ = "2023/03/15";
        String fecha2_ = "2023/03/16";
        String fecha3 = "2023/03/17";
        String fecha4 = "2023/03/18";
        String fecha4_ = "2023/03/18";
        String fecha5_ = "2023/03/19";
        String fecha6 = "2023/03/20";
        String fecha6_ = "2023/03/20";
        String fecha7 = "2023/03/21";
        String fecha8 = "2023/03/22";
        String fecha8_ = "2023/03/22";
        String fecha9_ = "2023/03/23";
        String fecha10 = "2023/03/24";
        String fecha11 = "2023/03/25";
        String fecha11_ = "2023/03/25";
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate fecha1date = LocalDate.parse(fecha1, formato);
        LocalDate fecha3date = LocalDate.parse(fecha3, formato);
        LocalDate fecha4date = LocalDate.parse(fecha4, formato);
        LocalDate fecha6date = LocalDate.parse(fecha6, formato);
        LocalDate fecha7date = LocalDate.parse(fecha7, formato);
        LocalDate fecha8date = LocalDate.parse(fecha8, formato);
        LocalDate fecha10date = LocalDate.parse(fecha10, formato);
        LocalDate fecha11date = LocalDate.parse(fecha11, formato);
        LocalDate fecha1_date = LocalDate.parse(fecha1_, formato);
        LocalDate fecha2_date = LocalDate.parse(fecha2_, formato);
        LocalDate fecha4_date = LocalDate.parse(fecha4_, formato);
        LocalDate fecha5_date = LocalDate.parse(fecha5_, formato);
        LocalDate fecha6_date = LocalDate.parse(fecha6_, formato);
        LocalDate fecha8_date = LocalDate.parse(fecha8_, formato);
        LocalDate fecha9_date = LocalDate.parse(fecha9_, formato);
        LocalDate fecha11_date = LocalDate.parse(fecha11_, formato);
        Date fechaDate1 = Date.from(fecha1date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate3 = Date.from(fecha3date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate4 = Date.from(fecha4date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate6 = Date.from(fecha6date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate7 = Date.from(fecha7date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate8 = Date.from(fecha8date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate10 = Date.from(fecha10date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate11 = Date.from(fecha11date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate1_ = Date.from(fecha1_date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate2_ = Date.from(fecha2_date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate4_ = Date.from(fecha4_date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate5_ = Date.from(fecha5_date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate6_ = Date.from(fecha6_date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate8_ = Date.from(fecha8_date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate9_ = Date.from(fecha9_date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate11_ = Date.from(fecha11_date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        fechas.add(fechaDate1);
        fechas.add(fechaDate1_);
        fechas.add(fechaDate2_);
        fechas.add(fechaDate3);
        fechas.add(fechaDate4);
        fechas.add(fechaDate4_);
        fechas.add(fechaDate5_);
        fechas.add(fechaDate6);
        fechas.add(fechaDate6_);
        fechas.add(fechaDate7);
        fechas.add(fechaDate8);
        fechas.add(fechaDate8_);
        fechas.add(fechaDate9_);
        fechas.add(fechaDate10);
        fechas.add(fechaDate11);
        fechas.add(fechaDate11_);
        turnosM.add(fechaDate1);
        turnosT.add(fechaDate1_);
        turnosT.add(fechaDate2_);
        turnosM.add(fechaDate3);
        turnosM.add(fechaDate4);
        turnosT.add(fechaDate4_);
        turnosT.add(fechaDate5_);
        turnosM.add(fechaDate6);
        turnosT.add(fechaDate6_);
        turnosM.add(fechaDate7);
        turnosM.add(fechaDate8);
        turnosT.add(fechaDate8_);
        turnosT.add(fechaDate9_);
        turnosM.add(fechaDate10);
        turnosM.add(fechaDate11);
        turnosT.add(fechaDate11_);

        Integer bonificacion = pagosService.obtenerBonificacion(fechas, turnosM, turnosT);
        assertEquals(0, bonificacion);
    }

    @Test
    void obtenerDescuentoLeche0a8Test(){
        Integer variacion_leche = -6;

        Integer descuento = pagosService.obtenerDescuentoLeche(variacion_leche);
        assertEquals(0, descuento);
    }

    @Test
    void obtenerDescuentoLeche9a25Test(){
        Integer variacion_leche = -16;

        Integer descuento = pagosService.obtenerDescuentoLeche(variacion_leche);
        assertEquals(7, descuento);
    }

    @Test
    void obtenerDescuentoLeche26a45Test(){
        Integer variacion_leche = -36;

        Integer descuento = pagosService.obtenerDescuentoLeche(variacion_leche);
        assertEquals(15, descuento);
    }

    @Test
    void obtenerDescuentoLeche46aMasTest(){
        Integer variacion_leche = -56;

        Integer descuento = pagosService.obtenerDescuentoLeche(variacion_leche);
        assertEquals(30, descuento);
    }

    @Test
    void obtenerDescuentoLecheVarPositivaTest(){
        Integer variacion_leche = 12;

        Integer descuento = pagosService.obtenerDescuentoLeche(variacion_leche);
        assertEquals(0, descuento);
    }

    @Test
    void obtenerDescuentoGrasa0a15Test(){
        Integer variacion_grasa = -5;

        Integer descuento = pagosService.obtenerDescuentoGrasa(variacion_grasa);
        assertEquals(0, descuento);
    }

    @Test
    void obtenerDescuentoGrasa16a25Test(){
        Integer variacion_grasa = -18;

        Integer descuento = pagosService.obtenerDescuentoGrasa(variacion_grasa);
        assertEquals(12, descuento);
    }

    @Test
    void obtenerDescuentoGrasa26a40Test(){
        Integer variacion_grasa = -35;

        Integer descuento = pagosService.obtenerDescuentoGrasa(variacion_grasa);
        assertEquals(20, descuento);
    }

    @Test
    void obtenerDescuentoGrasa41aMasTest(){
        Integer variacion_grasa = -45;

        Integer descuento = pagosService.obtenerDescuentoGrasa(variacion_grasa);
        assertEquals(30, descuento);
    }

    @Test
    void obtenerDescuentoGrasaVarPositivaTest(){
        Integer variacion_grasa = 15;

        Integer descuento = pagosService.obtenerDescuentoGrasa(variacion_grasa);
        assertEquals(0, descuento);
    }

    @Test
    void obtenerDescuentoSolido0a6Test(){
        Integer variacion_solidos = -4;

        Integer descuento = pagosService.obtenerDescuentoSolido(variacion_solidos);
        assertEquals(0, descuento);
    }

    @Test
    void obtenerDescuentoSolido7a12Test(){
        Integer variacion_solidos = -9;

        Integer descuento = pagosService.obtenerDescuentoSolido(variacion_solidos);
        assertEquals(18, descuento);
    }

    @Test
    void obtenerDescuentoSolido13a35Test(){
        Integer variacion_solidos = -24;

        Integer descuento = pagosService.obtenerDescuentoSolido(variacion_solidos);
        assertEquals(27, descuento);
    }

    @Test
    void obtenerDescuentoSolido36aMasTest(){
        Integer variacion_solidos = -44;

        Integer descuento = pagosService.obtenerDescuentoSolido(variacion_solidos);
        assertEquals(45, descuento);
    }

    @Test
    void obtenerDescuentoSolidoVarPositivaTest(){
        Integer variacion_solidos = 4;

        Integer descuento = pagosService.obtenerDescuentoSolido(variacion_solidos);
        assertEquals(0, descuento);
    }

    @Test
    void obtenerPagoTotalTest(){
        Pagos newPago = new Pagos();
        newPago.setPago_leche(450000);
        newPago.setPago_grasa(230000);
        newPago.setPago_solido(120000);
        newPago.setBonificacion(20);
        newPago.setDescuento_varLeche(0);
        newPago.setDescuento_varGrasa(12);
        newPago.setDescuento_varSolidos(18);

        Integer pago_total = pagosService.obtenerPagoTotal(newPago);
        assertEquals(672000, pago_total);
    }

    @Test
    void obtenerPagoTotalSinDsctoTest(){
        Pagos newPago = new Pagos();
        newPago.setPago_leche(450000);
        newPago.setPago_grasa(230000);
        newPago.setPago_solido(120000);
        newPago.setBonificacion(20);
        newPago.setDescuento_varLeche(0);
        newPago.setDescuento_varGrasa(0);
        newPago.setDescuento_varSolidos(0);

        Integer pago_total = pagosService.obtenerPagoTotal(newPago);
        assertEquals(960000, pago_total);
    }

    @Test
    void obtenerPagoTotalSinBonificacionTest(){
        Pagos newPago = new Pagos();
        newPago.setPago_leche(450000);
        newPago.setPago_grasa(230000);
        newPago.setPago_solido(120000);
        newPago.setBonificacion(0);
        newPago.setDescuento_varLeche(15);
        newPago.setDescuento_varGrasa(0);
        newPago.setDescuento_varSolidos(18);

        Integer pago_total = pagosService.obtenerPagoTotal(newPago);
        assertEquals(536000, pago_total);
    }

    @Test
    void obtenerPagoTotalSinBoniYSinDsctoTest(){
        Pagos newPago = new Pagos();
        newPago.setPago_leche(450000);
        newPago.setPago_grasa(230000);
        newPago.setPago_solido(-120000);
        newPago.setBonificacion(0);
        newPago.setDescuento_varLeche(0);
        newPago.setDescuento_varGrasa(0);
        newPago.setDescuento_varSolidos(0);

        Integer pago_total = pagosService.obtenerPagoTotal(newPago);
        assertEquals(560000, pago_total);
    }

    @Test
    void calcularDescuentosTest(){
        Pagos newPago = new Pagos();
        newPago.setDescuento_varLeche(7);
        newPago.setDescuento_varGrasa(20);
        newPago.setDescuento_varSolidos(0);
        Integer pago_acopio = 800000;

        Integer descuento = pagosService.calcularDescuentos(newPago, pago_acopio);
        assertEquals(216000, descuento);
    }

    @Test
    void calcularDescuentosEn0Test(){
        Pagos newPago = new Pagos();
        newPago.setDescuento_varLeche(0);
        newPago.setDescuento_varGrasa(0);
        newPago.setDescuento_varSolidos(0);
        Integer pago_acopio = 800000;

        Integer descuento = pagosService.calcularDescuentos(newPago, pago_acopio);
        assertEquals(0, descuento);
    }

    @Test
    void obtenerMontoRetencionTest(){
        Proveedor proveedor_prueba = new Proveedor();
        proveedor_prueba.setRetencion("Si");
        Integer pago_total = 1020000;

        Integer monto_retencion = pagosService.obtenerMontoRetencion(pago_total, proveedor_prueba);
        assertEquals(132600, monto_retencion);
    }

    @Test
    void obtenerMontoRetencionConNoTest(){
        Proveedor proveedor_prueba = new Proveedor();
        proveedor_prueba.setRetencion("No");
        Integer pago_total = 1020000;

        Integer monto_retencion = pagosService.obtenerMontoRetencion(pago_total, proveedor_prueba);
        assertEquals(0, monto_retencion);
    }

    @Test
    void obtenerMontoRetencionMenosDe950Test(){
        Proveedor proveedor_prueba = new Proveedor();
        proveedor_prueba.setRetencion("Si");
        Integer pago_total = 435000;

        Integer monto_retencion = pagosService.obtenerMontoRetencion(pago_total, proveedor_prueba);
        assertEquals(0, monto_retencion);
    }

    @Test
    void obtenerMontoFinalTest(){
        Integer pago_total = 1020000;
        Integer monto_retencion = 132600;

        Integer monto_final = pagosService.obtenerMontoFinal(pago_total, monto_retencion);
        assertEquals(887400, monto_final);
    }

    @Test
    void obtenerMontoFinalSinRetencionTest(){
        Integer pago_total = 1020000;
        Integer monto_retencion = 0;

        Integer monto_final = pagosService.obtenerMontoFinal(pago_total, monto_retencion);
        assertEquals(1020000, monto_final);
    }
}
