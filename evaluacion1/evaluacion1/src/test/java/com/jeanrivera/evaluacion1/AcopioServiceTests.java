package com.jeanrivera.evaluacion1;

import com.jeanrivera.evaluacion1.entity.Acopio;
import com.jeanrivera.evaluacion1.repositories.AcopioRepository;
import com.jeanrivera.evaluacion1.services.AcopioServices;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
public class AcopioServiceTests {

    @Mock
    private AcopioRepository acopioRepository;

    @InjectMocks
    private AcopioServices acopioServices;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void obtenerDataTest() {
        // Creamos una lista de acopios simulada
        List<Acopio> acopiosSimulados = new ArrayList<>();
        String fecha1 = "2023/03/15";
        String fecha2 = "2023/03/18";
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate fecha1date = LocalDate.parse(fecha1, formato);
        LocalDate fecha2date = LocalDate.parse(fecha2, formato);
        Date fechaDate1 = Date.from(fecha1date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate2 = Date.from(fecha2date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        acopiosSimulados.add(new Acopio(1, fechaDate1, "M", "00001", "67"));
        acopiosSimulados.add(new Acopio(2, fechaDate2, "T", "00002", "89"));

        // Simulamos el comportamiento del acopioRepository.findAll() para retornar la lista simulada
        when(acopioRepository.findAll()).thenReturn(acopiosSimulados);

        // Ejecutamos el método a probar
        List<Acopio> acopiosObtenidos = acopioServices.obtenerData();

        // Verificamos que el resultado no es nulo y tiene el tamaño correcto
        Assert.assertNotNull(acopiosObtenidos);
        Assert.assertEquals(2, acopiosObtenidos.size());

        // Verificamos que los elementos de la lista son iguales a los simulados
        Assert.assertEquals(acopiosSimulados.get(0), acopiosObtenidos.get(0));
        Assert.assertEquals(acopiosSimulados.get(1), acopiosObtenidos.get(1));

        // Verificamos que el método acopioRepository.findAll() fue llamado exactamente una vez
        verify(acopioRepository, times(1)).findAll();
    }

    @Test
    public void guardarDataTest() {
        // Crear un objeto Acopio simulado
        Acopio acopioSimulado = new Acopio();
        acopioSimulado.setId(1);
        acopioSimulado.setFecha(new Date());
        acopioSimulado.setTurno("M");
        acopioSimulado.setProveedor("00001");
        acopioSimulado.setKilos("67");

        // Simular el comportamiento del acopioRepository.save() para retornar el objeto simulado
        when(acopioRepository.save(acopioSimulado)).thenReturn(acopioSimulado);

        // Ejecutar el método a probar
        acopioServices.guardarData(acopioSimulado);

        // Verificar que el método acopioRepository.save() fue llamado exactamente una vez con el objeto simulado
        verify(acopioRepository, times(1)).save(acopioSimulado);
    }

    @Test
    public void guardarDataDBTest() {
        // Creamos un objeto Acopio simulado
        String fecha1 = "2023/03/15";
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate fecha1date = LocalDate.parse(fecha1, formato);
        Date fechaDate1 = Date.from(fecha1date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Acopio acopioSimulado = new Acopio();
        acopioSimulado.setFecha(fechaDate1);
        acopioSimulado.setTurno("M");
        acopioSimulado.setProveedor("00001");
        acopioSimulado.setKilos("50");

        // Simulamos el comportamiento del método guardarData
        doAnswer(invocation -> {
            Acopio acopioArgument = invocation.getArgument(0);
            Assert.assertNotNull(acopioArgument);
            Assert.assertEquals(fechaDate1, acopioArgument.getFecha());
            Assert.assertEquals("M", acopioArgument.getTurno());
            Assert.assertEquals("00001", acopioArgument.getProveedor());
            Assert.assertEquals("50", acopioArgument.getKilos());
            return null;
        }).when(acopioRepository).save(any(Acopio.class));

        // Ejecutamos el método a probar
        acopioServices.guardarDataDB(fechaDate1, "M", "00001", "50");

        // Verificamos que el método guardarData fue llamado exactamente una vez
        verify(acopioRepository, times(1)).save(any(Acopio.class));
    }

    @Test
    public void eliminarDataTest() {
        // Creamos una lista de acopios simulada
        List<Acopio> acopiosSimulados = new ArrayList<>();
        String fecha1 = "2023/03/15";
        String fecha2 = "2023/03/18";
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate fecha1date = LocalDate.parse(fecha1, formato);
        LocalDate fecha2date = LocalDate.parse(fecha2, formato);
        Date fechaDate1 = Date.from(fecha1date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate2 = Date.from(fecha2date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        acopiosSimulados.add(new Acopio(1, fechaDate1, "M", "00001", "67"));
        acopiosSimulados.add(new Acopio(2, fechaDate2, "T", "00002", "89"));

        // Simulamos el comportamiento del acopioRepository.deleteAll() para no hacer nada
        doNothing().when(acopioRepository).deleteAll(anyList());

        // Ejecutamos el método a probar
        acopioServices.eliminarData(acopiosSimulados);

        // Verificamos que el método acopioRepository.deleteAll() fue llamado exactamente una vez
        verify(acopioRepository, times(1)).deleteAll(eq(acopiosSimulados));
    }

    @Test
    public void findByProveedorTest() {
        // Creamos un código de proveedor simulado
        String codigoProveedor = "1";

        // Creamos una lista de acopios simulada para ese proveedor
        List<Acopio> acopiosSimulados = new ArrayList<>();
        String fecha1 = "2023/03/15";
        String fecha2 = "2023/03/18";
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate fecha1date = LocalDate.parse(fecha1, formato);
        LocalDate fecha2date = LocalDate.parse(fecha2, formato);
        Date fechaDate1 = Date.from(fecha1date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaDate2 = Date.from(fecha2date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        acopiosSimulados.add(new Acopio(1, fechaDate1, "M", codigoProveedor, "67"));
        acopiosSimulados.add(new Acopio(2, fechaDate2, "T", codigoProveedor, "89"));

        // Simulamos el comportamiento del acopioRepository.findByProveedor() para retornar la lista simulada
        when(acopioRepository.findByProveedor(codigoProveedor)).thenReturn(acopiosSimulados);

        // Ejecutamos el método a probar
        List<Acopio> acopiosObtenidos = acopioServices.findByProveedor(codigoProveedor);

        // Verificamos que el resultado no es nulo y tiene el tamaño correcto
        Assert.assertNotNull(acopiosObtenidos);
        Assert.assertEquals(2, acopiosObtenidos.size());

        // Verificamos que los elementos de la lista son iguales a los simulados
        Assert.assertEquals(acopiosSimulados.get(0), acopiosObtenidos.get(0));
        Assert.assertEquals(acopiosSimulados.get(1), acopiosObtenidos.get(1));

        // Verificamos que el método acopioRepository.findByProveedor() fue llamado exactamente una vez con el código de proveedor simulado
        verify(acopioRepository, times(1)).findByProveedor(codigoProveedor);
    }

    @Test
    public void findAllDistinctDatesTest() {
        // Crea una lista de fechas simuladas
        List<Date> fechasSimuladas = new ArrayList<>();
        fechasSimuladas.add(new Date());
        fechasSimuladas.add(new Date());

        // Simula el comportamiento de acopioRepository.findAllDistinctDates() para retornar la lista simulada
        when(acopioRepository.findAllDistinctDates(anyString())).thenReturn(fechasSimuladas);

        // Ejecuta el método a probar
        List<Date> fechasObtenidas = acopioServices.findAllDistinctDates("proveedor");

        // Verifica que el resultado no es nulo y tiene el tamaño correcto
        Assert.assertNotNull(fechasObtenidas);
        Assert.assertEquals(2, fechasObtenidas.size());

        // Verifica que los elementos de la lista son iguales a los simulados
        Assert.assertEquals(fechasSimuladas.get(0), fechasObtenidas.get(0));
        Assert.assertEquals(fechasSimuladas.get(1), fechasObtenidas.get(1));

        // Verifica que el método acopioRepository.findAllDistinctDates() fue llamado exactamente una vez con el argumento "proveedor"
        verify(acopioRepository, times(1)).findAllDistinctDates("proveedor");
    }

    @Test
    public void findAllByFechaTest() {
        // Creamos los datos de entrada de prueba
        String codigo = "00001";

        // Creamos los datos de salida de prueba
        Date fecha1 = new Date();
        Date fecha2 = new Date();
        List<Date> fechas = Arrays.asList(fecha1, fecha2);

        // Configuramos el comportamiento del mock del repositorio
        when(acopioRepository.findAllByFecha(codigo)).thenReturn(fechas);

        // Invocamos al método que queremos probar
        List<Date> resultado = acopioServices.findAllByFecha(codigo);

        // Verificamos el resultado
        Assert.assertEquals(fechas, resultado);
    }

    @Test
    public void contarTurnosTest() {
        // Creamos los datos de entrada de prueba
        String codigo = "00001";
        String turno = "M";

        // Creamos los datos de salida de prueba
        Date fecha1 = new Date();
        Date fecha2 = new Date();
        List<Date> fechas = Arrays.asList(fecha1, fecha2);

        // Configuramos el comportamiento del mock del repositorio
        when(acopioRepository.contarTurnos(codigo, turno)).thenReturn(fechas);

        // Invocamos al método que queremos probar
        List<Date> resultado = acopioServices.contarTurnos(codigo, turno);

        // Verificamos el resultado
        Assert.assertEquals(fechas, resultado);
    }
}
